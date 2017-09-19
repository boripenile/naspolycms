package cms.academic.academicapp.controller;

import java.util.Calendar;
import java.util.List;

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

import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.Discussion;
import cms.academic.academicapp.model.DiscussionFeed;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.Mail;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.Role;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.CourseService;
import cms.academic.academicapp.service.DiscussionFeedService;
import cms.academic.academicapp.service.MemberCourseService;
import cms.academic.academicapp.service.MemberDiscussionService;
import cms.academic.academicapp.service.StudentCourseService;
import cms.academic.academicapp.service.StudentDiscussionService;
import cms.academic.academicapp.service.UserService;

@Controller
@RequestMapping(value = "/discussions")
public class DiscussionController {

	private MemberCourseService memberCourseService;
	private StudentCourseService studentCourseService;
	private UserService userService;
	private CourseService courseService;
	private MemberDiscussionService memberDiscussionService;
	private StudentDiscussionService studentDiscussionService;
	private DiscussionFeedService discussionFeedService;
	private SpringMailSender springMailSender;
	
	private static final String FACULTY = "FACULTY";
	private static final String STUDENT = "STUDENT";

	@Autowired
	public DiscussionController(SpringMailSender springMailSender,MemberCourseService memberCourseService, StudentCourseService studentCourseService,
			UserService userService, CourseService courseService, MemberDiscussionService memberDiscussion,
			StudentDiscussionService studentDiscussion, DiscussionFeedService discussionFeedService) {
		super();
		this.memberCourseService = memberCourseService;
		this.studentCourseService = studentCourseService;
		this.springMailSender = springMailSender;
		this.userService = userService;
		this.courseService = courseService;
		this.memberDiscussionService = memberDiscussion;
		this.studentDiscussionService = studentDiscussion;
		this.discussionFeedService = discussionFeedService;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String coursesListByUser(Model model, HttpServletRequest request) {
		loadRegisteredEnrolledCourseList(model, request);
		return "user/discussions";
	}

	@RequestMapping(value = "/{courseId}/{discussionId}/join", method = RequestMethod.GET)
	public String joinDiscussions(@PathVariable("courseId") Long courseId,
			@PathVariable("discussionId") Long discussionId, Model model, HttpServletRequest request) {	
		if(discussionId != null){
			Discussion discussion = discussionFeedService.findDiscussion(discussionId);
			List<DiscussionFeed> discussionFeeds = discussionFeedService.viewDiscussionFeeds(discussion);
			
			model.addAttribute("feeds", discussionFeeds);
			
		}else{
			loadCourseDiscussionByUser(courseId, model, request);			
		}	
		model.addAttribute("discussion", discussionFeedService.findDiscussion(discussionId));
		model.addAttribute("feedslist", "feedslist");
		model.addAttribute("newdiscussion", null);
		model.addAttribute("editdiscussion", null);
		model.addAttribute("feed", new DiscussionFeed());
		loadRegisteredEnrolledCourseList(model, request);
		return "user/discussions";
	}
	
	@RequestMapping(value = "/{courseId}/topics", method = RequestMethod.GET)
	public String coursesDiscussions(@PathVariable("courseId") Long courseId, Model model, HttpServletRequest request) {
		loadCourseDiscussionByUser(courseId, model, request);
		loadRegisteredEnrolledCourseList(model, request);
		return "user/discussions";
	}

	@RequestMapping(value = "/feeds/add", method = RequestMethod.POST)
	public String addFeeddiscussions(@RequestParam(value="discussionId", required = false) Long discussionId,
			@RequestParam(value="courseId", required = false) Long courseId,
			   Model model, HttpServletRequest request) {
		if(discussionId != null){
			Discussion discussion = discussionFeedService.findDiscussion(discussionId);
			if(request.getParameter("comment").toString() == ""){
				model.addAttribute("error", "comment is required.");
				
				List<DiscussionFeed> discussionFeeds = discussionFeedService.viewDiscussionFeeds(discussion);
				
				model.addAttribute("feeds", discussionFeeds);
			}else{
				User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
				DiscussionFeed feed = new DiscussionFeed();
				feed.setComment(request.getParameter("comment").trim().toString());
				feed.setDiscussion(discussion);
				feed.setContributor(user);
				feed.setInserteddate(Calendar.getInstance());
				
				discussionFeedService.addDiscussionFeed(feed);
                List<DiscussionFeed> discussionFeeds = discussionFeedService.viewDiscussionFeeds(discussion);
				
				model.addAttribute("feeds", discussionFeeds);
			}
			
			
		}else{
			loadCourseDiscussionByUser(courseId, model, request);			
		}	
		model.addAttribute("discussion", discussionFeedService.findDiscussion(discussionId));
		model.addAttribute("feedslist", "feedslist");
		model.addAttribute("newdiscussion", null);
		model.addAttribute("editdiscussion", null);
		model.addAttribute("feed", new DiscussionFeed());
		loadRegisteredEnrolledCourseList(model, request);
		return "user/discussions";
	}
	
	private void loadCourseDiscussionByUser(Long courseId, Model model, HttpServletRequest request) {
		if (courseId != null) {
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
			String[] roles = new String[2];
			int count = 0;
			for (Role r : user.getUserrole()) {
				roles[count] = r.getRolename();
			}
			if (user != null && roles[0].equalsIgnoreCase(FACULTY)) {
				RegisteredCourse registerCourse = memberCourseService.viewRegisteredCourse(courseId);
				model.addAttribute("mdiscussions", discussionListByMember(registerCourse, user));
				model.addAttribute("course", registerCourse.getCourse());
				model.addAttribute("mycourseId", registerCourse.getId());
				model.addAttribute("logged", user);
			} else if (user != null && roles[0].equalsIgnoreCase(STUDENT)) {
				EnrolledCourse enrolledCourse = studentCourseService.viewEnrolledCourse(courseId);
				model.addAttribute("sdiscussions", discussionListByStudent(enrolledCourse));
				model.addAttribute("course", enrolledCourse.getCourse());
				model.addAttribute("mycourseId", enrolledCourse.getId());
				model.addAttribute("logged", user);
			}

		}
		model.addAttribute("newdiscussion", null);
	}

	@RequestMapping(value = "/{courseId}/new", method = RequestMethod.GET)
	public String coursesNewDiscussion(@PathVariable("courseId") Long courseId, Model model, HttpServletRequest request) {
		if (courseId != null) {
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
			String[] roles = new String[2];
			int count = 0;
			for (Role r : user.getUserrole()) {
				roles[count] = r.getRolename();
			}
			if (user != null && roles[0].equalsIgnoreCase(FACULTY)) {
				RegisteredCourse registerCourse = memberCourseService.viewRegisteredCourse(courseId);
				model.addAttribute("mycourse", registerCourse);
				model.addAttribute("course", registerCourse.getCourse());
				model.addAttribute("member", registerCourse.getMember());
				model.addAttribute("owner", user);
				model.addAttribute("discussion", new Discussion());
			} else if (user != null && roles[0].equalsIgnoreCase(STUDENT)) {
				EnrolledCourse enrolledCourse = studentCourseService.viewEnrolledCourse(courseId);
				model.addAttribute("mycourse", enrolledCourse);
				model.addAttribute("course", enrolledCourse.getCourse());
				model.addAttribute("member", getCourseMember(enrolledCourse.getCourse().getId()));
				model.addAttribute("owner", user);
				model.addAttribute("discussion", new Discussion());
			}
			model.addAttribute("newdiscussion", "newdiscussion");
			model.addAttribute("feedslist", null);
		}
		
		loadRegisteredEnrolledCourseList(model, request);
		return "user/discussions";
	}

	@RequestMapping(value = "/{courseId}/edit/{mycourseId}", method = RequestMethod.GET)
	public String viewDiscussion(@PathVariable("courseId") Long courseId,
			@PathVariable("mycourseId") Long mycourseId, Model model, HttpServletRequest request) {
		if (courseId != null) {
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
			String[] roles = new String[2];
			int count = 0;
			for (Role r : user.getUserrole()) {
				roles[count] = r.getRolename();
			}
			if (user != null && roles[0].equalsIgnoreCase(FACULTY)) {
				Discussion discussion = memberDiscussionService.viewDiscussion(courseId);
				model.addAttribute("owner", user);
				model.addAttribute("discussion", discussion);
				model.addAttribute("mycourseId", mycourseId);
			} else if (user != null && roles[0].equalsIgnoreCase(STUDENT)) {
				Discussion discussion = studentDiscussionService.viewDiscussion(courseId);
				model.addAttribute("owner", user);
				model.addAttribute("discussion", discussion);
				model.addAttribute("mycourseId", mycourseId);
			}
			model.addAttribute("newdiscussion", null);
			model.addAttribute("feedslist", null);
			model.addAttribute("editdiscussion", "editdiscussion");
		}
		
		loadRegisteredEnrolledCourseList(model, request);
		return "user/discussions";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addNewCourse(@ModelAttribute("discussion") Discussion discussion, HttpServletRequest request,
			@RequestParam(value = "ownerId", required = false) Long id,
			@RequestParam(value = "userCourseId", required = false) Long userCourseId,
			@RequestParam(value = "memberId", required = false) Long memberId,
			@RequestParam(value = "courseId", required = false) Long courseId, BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				model.addAttribute("error", "Complete the require fields");
			} else {
				if ((id != null && memberId != null && courseId != null && userCourseId != null)
						|| discussion.getTopic() != null) {
					User owner = userService.findById(id);
					User member = userService.findById(memberId);
					Course course = courseService.findById(courseId);
					discussion.setCourse(course);
					discussion.setMember(member);
					discussion.setOwner(owner);
					discussion.setInserteddate(Calendar.getInstance());
					User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
					String[] roles = new String[2];
					int count = 0;
					int out = 0;
					for (Role r : user.getUserrole()) {
						roles[count] = r.getRolename();
					}
					if (user != null && roles[0].equalsIgnoreCase(FACULTY)) {
                         out = memberDiscussionService.addNewDiscussion(discussion);
                         
					} else if (user != null && roles[0].equalsIgnoreCase(STUDENT)) {
                         out = studentDiscussionService.addNewDiscussion(discussion);
					}
					Mail mailcopy = new Mail(member.getEmailaddress());
					mailcopy.setSubject("New discussion's topic alert created by " + owner.getFirstname().concat(" ").concat(owner.getLastname()));
					String mainbody = "Topic: ".concat(discussion.getTopic()).concat("\nDescription: ").concat(discussion.getDescription());
					mailcopy.setBody(mainbody);
					mailcopy.setReceiver(new String[]{member.getEmailaddress()});
					
					springMailSender.sendMail("Hi ".concat(owner.getFirstname().concat(" ").concat(owner.getLastname())).concat(","), "Nassarawa University.", mailcopy, null);
					
					model.addAttribute("message", out + " discussion added successfully.");
					loadCourseDiscussionByUser(userCourseId, model, request);
				}else{
					model.addAttribute("error", "Discussion's topic is required.");
					return "redirect:/discussions/" + courseId + "/new";
				}
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error: " + e.getMessage() + "\n Cause: " + e.getCause());
		}

		loadRegisteredEnrolledCourseList(model, request);
		return "user/discussions";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateCourseDiscussion(@ModelAttribute("discussion") Discussion discussion, HttpServletRequest request,
			@RequestParam(value = "id", required = false) Long discussionId,
			@RequestParam(value = "ownerId", required = false) Long id,
			@RequestParam(value = "mycourseId", required = false) Long userCourseId,
			@RequestParam(value = "memberId", required = false) Long memberId,
			@RequestParam(value = "courseId", required = false) Long courseId, BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				model.addAttribute("error", "Complete the require fields");
			} else {
				if ((discussionId != null && id != null && memberId != null && courseId != null && userCourseId != null)
						|| discussion.getTopic() != null) {
					User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
					String[] roles = new String[2];
					int count = 0;
					int out = 0;
					for (Role r : user.getUserrole()) {
						roles[count] = r.getRolename();
					}
					if (user != null && roles[0].equalsIgnoreCase(FACULTY)) {
						Discussion selected = memberDiscussionService.viewDiscussion(discussionId);
						selected.setTopic(discussion.getTopic());
						selected.setDescription(discussion.getDescription());
                        out = memberDiscussionService.editDiscussion(selected);
					} else if (user != null && roles[0].equalsIgnoreCase(STUDENT)) {
						Discussion selected = studentDiscussionService.viewDiscussion(discussionId);
						selected.setTopic(discussion.getTopic());
						selected.setDescription(discussion.getDescription());
                        out = studentDiscussionService.editDiscussion(selected);
					}
					
					model.addAttribute("message", out + " discussion updated successfully.");
					loadCourseDiscussionByUser(userCourseId, model, request);
				}else{
					model.addAttribute("error", "Discussion's topic is required.");
					return "redirect:/discussions/" + courseId + "/new";
				}
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error: " + e.getMessage() + "\n Cause: " + e.getCause());
		}

		loadRegisteredEnrolledCourseList(model, request);
		return "user/discussions";
	}
	
	private List<Discussion> discussionListByMember(RegisteredCourse course, User member) {
		return memberDiscussionService.courseDiscussions(course, member);
	}

	private List<Discussion> discussionListByStudent(EnrolledCourse enrolled) {
		return studentDiscussionService.courseDiscussions(enrolled);
	}

	private void loadRegisteredEnrolledCourseList(Model model, HttpServletRequest request) {
		if (request.getUserPrincipal().getName() != null) {
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
			String[] roles = new String[2];
			int count = 0;
			for (Role r : user.getUserrole()) {
				roles[count] = r.getRolename();
			}
			if (user != null && roles[0].equalsIgnoreCase(FACULTY)) {
				model.addAttribute("mycourses", courseList(user));
			} else if (user != null && roles[0].equalsIgnoreCase(STUDENT)) {
				model.addAttribute("studentcourses", enrollCourseList(user));
			}
		}
	}

	public User getCourseMember(Long courseId) {
		List<RegisteredCourse> memberCourses = memberCourseService.allRegisteredCourses();
		for (RegisteredCourse c : memberCourses) {
			if (c.getCourse().getId().equals(courseId.longValue())) {
				return c.getMember();
			}
		}
		return null;
	}

	private List<RegisteredCourse> courseList(User member) {
		List<RegisteredCourse> mycourses = memberCourseService.registeredCourses(member);
		return mycourses;
	}

	private List<EnrolledCourse> enrollCourseList(User student) {
		List<EnrolledCourse> mycourses = studentCourseService.enrolledCourses(student);
		return mycourses;
	}

	@ModelAttribute("courses")
	public List<Course> loadAllCourses() {
		return courseService.courses();
	}

}
