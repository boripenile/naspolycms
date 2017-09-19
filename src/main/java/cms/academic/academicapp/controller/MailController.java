package cms.academic.academicapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.Mail;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.CourseService;
import cms.academic.academicapp.service.MemberCourseService;
import cms.academic.academicapp.service.StudentCourseService;
import cms.academic.academicapp.service.UserService;

@Controller
@RequestMapping(value="/mailing")
public class MailController {
    private SpringMailSender springMailSender;
	private StudentCourseService studentCourseService;
	private MemberCourseService memberCourseService;
	private UserService userService;
	private CourseService courseService;
	
	@Autowired
	public MailController(SpringMailSender springMailSender, MemberCourseService memberCourseService,
			StudentCourseService studentCourseService, UserService userService, CourseService courseService) {
		this.springMailSender = springMailSender;
		this.studentCourseService = studentCourseService;
		this.memberCourseService = memberCourseService;
		this.userService = userService;
		this.courseService = courseService;
	}

	@RequestMapping(value="/courses", method=RequestMethod.GET)
	public String coursesListByUser(Model model,
			HttpServletRequest request){
		loadRegisteredEnrolledCourseList(model, request);
		return "user/email";
	}
	
	@RequestMapping(value="/courses/{courseId}/mail", method=RequestMethod.GET)
	public String mailing(Model model, @PathVariable("courseId")Long id,
			HttpServletRequest request){
		if(id != null){
			loadMailingPage(model, request, id);
            return "user/email";
		}
		
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/mail")
	public String email(){
		return "user/email";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/send")
	public String sendEmail(HttpServletRequest request, Model model,
			final @RequestParam CommonsMultipartFile attachFile) {
		// reads form input
		
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		
        String userId = request.getParameter("userId");
        String courseId = request.getParameter("courseId");
        
        Long myId = null;
        Long cId = null;
        
        if(courseId != null && userId != null){
        	myId = Long.parseLong(userId);
        	cId = Long.parseLong(courseId);
        }else{
        	model.addAttribute("error", "The mail cannot be sent.");
        	return "home";
        }
        if(subject.length() == 0 || message.length() == 0){
			model.addAttribute("error", "Both subject and message are required.");
			loadMailingPage(model, request, cId);
            return "user/email";
        }else{
        	User faculty = userService.findById(myId);
            Course course = courseService.findById(cId);
            List<User> students = studentCourseService.viewEnrolledStudentsPerCourse(course);
            
            String[] toEmails = extractEmails(students);
            
    		Mail mail = new Mail(faculty.getEmailaddress());
    		mail.setSubject(subject);
    		mail.setBody(message);
    		mail.setReceiver(toEmails);
    		Mail mailcopy = new Mail(faculty.getEmailaddress());
    		mailcopy.setSubject("Copy of ".concat(subject));
    		mailcopy.setBody(message);
    		mailcopy.setReceiver(new String[]{faculty.getEmailaddress()});
    		
    		springMailSender.sendMail("", faculty.getFirstname() + " " + faculty.getLastname()+ "<br/> Computer Science Department, Nasarawa State Polytechnic.", mail, attachFile);
    		springMailSender.sendMail("", faculty.getFirstname() + " " + faculty.getLastname()+ "<br/> Computer Science Department, Nasarawa State Polytechnic.", mailcopy, attachFile);
    		
    		model.addAttribute("message", "Message sent successfully.");
    		loadMailingPage(model, request, cId);
    		return "user/email";
        }
        
	}
	
	private void loadMailingPage(Model model, HttpServletRequest request, Long courseId) {
		if(request.getUserPrincipal().getName() != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
			Course course = courseService.findById(courseId);
	        if(course != null){
				List<User> students = studentCourseService.viewEnrolledStudentsPerCourse(course);
				if(user != null && students != null && students.size() > 0){
					model.addAttribute("mycourses", courseList(user));
					model.addAttribute("newmail", "newmail");
					model.addAttribute("course", course);
					model.addAttribute("user", user);

				}else{
					model.addAttribute("newmail", null);
					model.addAttribute("mycourses", courseList(user));
					model.addAttribute("message", "No Student enrolled for " + course.getCourseName() + " yet.");
				}
	        }			
		}
	}
	
	private void loadRegisteredEnrolledCourseList(Model model, HttpServletRequest request) {
		if(request.getUserPrincipal().getName() != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
			
			if(user != null){
				model.addAttribute("mycourses", courseList(user));
			}
		}
	}
	
	private List<RegisteredCourse> courseList(User member){		
		List<RegisteredCourse> mycourses = memberCourseService.registeredCourses(member);
		return mycourses;		
	}
	
	private String[] extractEmails(List<User> allStudents){
		String[] emails;
		if(allStudents != null){
			emails = new String[allStudents.size()];
			int pos = 0;
			for(User s : allStudents){
				emails[pos] = s.getEmailaddress();
				++pos;
			}
			return emails;
		}
		return null;
	}

}
