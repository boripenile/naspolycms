package cms.academic.academicapp.controller;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cms.academic.academicapp.model.Mail;
import cms.academic.academicapp.model.Role;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.RoleService;
import cms.academic.academicapp.service.UserService;

@Controller
public class UserController {

	private UserService userService;
	private RoleService roleService;
	private SpringMailSender springMailSender;

	@Autowired
	public UserController(UserService userService, RoleService roleService, SpringMailSender mailService) {
		this.userService = userService;
		this.roleService = roleService;
		this.springMailSender = mailService;
	}

	@RequestMapping(value = "/users/new", method = RequestMethod.GET)
	public String setUpUser(Model model) {
		User user = new User();
		user.setUserrole(new HashSet<Role>());
		model.addAttribute("user", user);
		return "user/registeruser";
	}

	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User newUser, BindingResult result,
			@RequestParam(value = "userrol", required = false) Long[] roles, Model model) {
		if (result.hasErrors()) {
			return "user/registeruser";
		} else {
			newUser.setEnabled(Boolean.FALSE);
			newUser.setInserteddate(Calendar.getInstance().getTime());
			Set<Role> myroles = new HashSet<Role>();

			if (roles != null && roles.length > 0) {
				for (Long auth : roles) {

					Role role = roleService.findById(auth);
					if (role != null) {
						myroles.add(role);
					}
				}
				newUser.setUserrole(myroles);
				newUser.setEnabled(Boolean.TRUE);
				if (userService.registerUser(newUser)) {
					model.addAttribute("message",
							"Registration success! Kindly check your inbox or spam of your registered email address to activate your account.");
					sendActivationLink(newUser);
					return "home";
				} else {
					model.addAttribute("error", "The email address used has been registered.");
				}
			}
		}
		return "user/registeruser";
	}

	private void sendActivationLink(User receiver) {
		String sender = "alitestaps@gmail.com";
		String subject = "Activate your account";
		String closure = "Administrator";
		Long recepient = receiver.getId();
		// String body = "http://youngprime.com/users/activate/" + recepient;
		String body = "http://youngprime.com:8081/naspolylafia/users/activate/" + recepient;
		Mail mail = new Mail(sender);
		mail.setReceiver(new String[] { receiver.getEmailaddress() });
		mail.setBody(body);
		mail.setSubject(subject);
		String salutation = receiver.getFirstname() + ", click on the link below to acticate your account.";
		springMailSender.sendMail(salutation, closure, mail, null);
	}

	@RequestMapping(value = "/users/view", method = RequestMethod.GET)
	public String viewProfile(Model model, HttpServletRequest request) {
		User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
		model.addAttribute("user", user);
		return "user/profile";
	}

	@RequestMapping(value = "/users/activate/{id}", method = RequestMethod.GET)
	public String activateUser(@PathVariable("id") Long id, Model model) {
		if (id != null) {
			System.out.println("Email exits: " + id);
			User user = userService.findById(id);
			if (user != null && user.getEnabled() == Boolean.FALSE) {
				System.out.println("User exits");
				user.setEnabled(Boolean.TRUE);
				userService.updateUser(user);
				model.addAttribute("message", "Your account has been activated success. Kindly login to proceed.");
			}
		}
		return "home";
	}

	@RequestMapping(value = "/users/{id}/edit", method = RequestMethod.GET)
	public String editUser(@PathVariable("id") Long userId, Model model) {
		User user = userService.findById(userId);
		model.addAttribute("user", user);
		return "user/editprofile";
	}

	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public String updateUser(@RequestParam(value = "id", required = true) Long id, @ModelAttribute("user") User edit,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/editprofile";
		} else {
			if (id != null) {
				User selected = userService.findById(id);
				selected.setFirstname(edit.getFirstname());
				selected.setLastname(edit.getLastname());
				selected.setMobileNumber(edit.getMobileNumber());
				selected.setGender(edit.getGender());
				userService.updateUser(selected);

				model.addAttribute("user", selected);
			}
		}
		return "user/profile";
	}

	@ModelAttribute(value = "users")
	public List<User> getAllUsers() {
		return userService.users();
	}

	@ModelAttribute(value = "roles")
	public List<Role> getAllRoles() {
		return roleService.findAllRoles();
	}

}
