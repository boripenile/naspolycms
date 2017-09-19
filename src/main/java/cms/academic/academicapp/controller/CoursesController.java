package cms.academic.academicapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.Role;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.CourseService;
import cms.academic.academicapp.service.MemberCourseService;
import cms.academic.academicapp.service.StudentCourseService;
import cms.academic.academicapp.service.UserService;

@Controller
@RequestMapping("/courses")
public class CoursesController {
	
	private MemberCourseService memberCourseService;
	private StudentCourseService studentCourseService;
	private UserService userService;
	private CourseService courseService;
	
	private static final String FACULTY = "FACULTY";
	private static final String STUDENT = "STUDENT";
	
	@Autowired
	public CoursesController(MemberCourseService memberCourse, UserService userService,
			CourseService courseService, StudentCourseService studentCourse) {
		this.memberCourseService = memberCourse;
		this.studentCourseService = studentCourse;
		this.userService = userService;
		this.courseService = courseService;
	}

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String coursesListByUser(Model model,
			HttpServletRequest request){
		loadRegisteredEnrolledCourseList(model, request);
		return "user/courses";
	}
    
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addNewCourse(@ModelAttribute("course")@Valid Course course, HttpServletRequest request, 
			@RequestParam(value="userId", required=false)Long id, BindingResult result, Model model){
		try {
			if(result.hasErrors()){
				model.addAttribute("error", "Complete the require fields");
			}else{
				if(id != null){
					User member = userService.findById(id);
					if(member != null){
						course.setInsereddate(Calendar.getInstance().getTime());
						boolean added = courseService.registerCourse(course);
						if(added){
							Course c = courseService.findByCourseCode(course.getCourseCode());
							RegisteredCourse mycourse = new RegisteredCourse();
							mycourse.setCourse(c);
							mycourse.setMember(member);
							mycourse.setInserteddate(Calendar.getInstance().getTime());
							List<RegisteredCourse> courses = new ArrayList<RegisteredCourse>();
							courses.add(mycourse);
							Integer insert = memberCourseService.registerCourse(courses);
						    model.addAttribute("message", insert + " record added successfully");
						}else{
							model.addAttribute("error", "Complete the required fields.<br/>Duplicates courses are not allowed.");
						}					
					}
				}
			}		
		} catch (Exception e) {
			model.addAttribute("error", "Error: " + e.getMessage() + "\n Cause: " + e.getCause());
		}
		
		reloadRegisteredCourses(model, request);
		return "user/courses";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updateCourse(@RequestParam(value= "id", required=false)Long id, Model model,
			  @ModelAttribute("course")@Valid RegisteredCourse course, BindingResult result, HttpServletRequest request){
		try {
			if(result.hasErrors()){
				model.addAttribute("error", "Complete all the fields");
			}else{
				if(id != null){
					RegisteredCourse mycourse = memberCourseService.viewRegisteredCourse(id);
					Course cours = courseService.findById(mycourse.getCourse().getId());
					cours.setCourseCode(course.getCourse().getCourseCode());
					cours.setCourseName(course.getCourse().getCourseName());
	                courseService.update(cours);
	                model.addAttribute("message", "Course updated successfully.");	
				}
			}
		} catch (Exception e) {
			model.addAttribute("error", "Error: " + e.getMessage() + "\n Cause: " + e.getCause());
		}
		
		loadRegisteredEnrolledCourseList(model, request);	
		return "user/courses";
	}
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.GET)
	public String editCourse(@PathVariable("id")Long id, Model model, HttpServletRequest request){
		if(id != null){
			model.addAttribute("coursenew", null);
			model.addAttribute("coursestudents", null);
			model.addAttribute("message", null);	
			RegisteredCourse course = memberCourseService.viewRegisteredCourse(id);
			model.addAttribute("course", course);
			model.addAttribute("coursedit", "coursedit");			
		}
		loadRegisteredEnrolledCourseList(model, request);
		return "user/courses";
	}
	
	@RequestMapping(value="/{id}/students", method=RequestMethod.GET)
	public String viewStudentsPerCourse(@PathVariable("id")Long id, Model model, HttpServletRequest request){
		if(id != null){
			model.addAttribute("coursenew", null);
			model.addAttribute("message", null);	
			RegisteredCourse course = memberCourseService.viewRegisteredCourse(id);
			List<User> students = studentCourseService.viewEnrolledStudentsPerCourse(course.getCourse()); 
			if(students != null){
				model.addAttribute("coursestudents", students);
				model.addAttribute("course", course);
			}						
		}
		loadRegisteredEnrolledCourseList(model, request);
		return "user/courses";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerCourse(Model model, HttpServletRequest request){
		reloadRegisteredCourses(model, request);
		return "user/courses";
	}
	
	@RequestMapping(value="/enroll/{id}", method=RequestMethod.GET)
	public String enrollCourse(@PathVariable("id")Long id, Model model, HttpServletRequest request){
		Long[] ids = new Long[1];
		ids[0] = id;
		int result = enrollCourses(ids, request);
		model.addAttribute("message", result + " course(s) enrolled successfully");	
		loadRegisteredEnrolledCourseList(model, request);
		return "user/courses";
	}

	@RequestMapping(value="/enroll/{id}/delete", method=RequestMethod.GET)
	public String deleteEnrollCourse(@PathVariable("id")Long id, Model model, HttpServletRequest request){
		int result = 0;
		if(id != null){
			EnrolledCourse enrolled = studentCourseService.viewEnrolledCourse(id);
			if(enrolled.getStudent().getCourseAssignmentList().size() == 0){
				result = studentCourseService.removeEnrolledCourse(enrolled);
				model.addAttribute("message", result + " course deleted successfully");	
			}		
		}	
		loadRegisteredEnrolledCourseList(model, request);
		return "user/courses";
	}
	
	@RequestMapping(value="/enrolls", method=RequestMethod.POST)
	public String enrollCourses(@RequestParam(value="courseId", required=false)Long[] ids, 
			@RequestParam(value="action", required=false)String action, Model model, HttpServletRequest request){
		if(action.equals("enroll")){
			int result = enrollCourses(ids, request);
			model.addAttribute("message", result + " course(s) enrolled successfully");	
		}
		loadRegisteredEnrolledCourseList(model, request);
		return "user/courses";
	}
	
	private int enrollCourses(Long[] ids, HttpServletRequest request) {
		if(ids != null){
			List<EnrolledCourse> coursesEnrol = new ArrayList<EnrolledCourse>();		
			for(Long id : ids){
				Course course = courseService.findById(id);
				EnrolledCourse enrollCourse = new EnrolledCourse();
				User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
				enrollCourse.setCourse(course);
				enrollCourse.setStudent(user);
				enrollCourse.setInserteddate(Calendar.getInstance().getTime());
				coursesEnrol.add(enrollCourse);
			}	
			Integer result = studentCourseService.enrolForCourse(coursesEnrol);
			return result;	
		}
		return 0;
	}

	private void reloadRegisteredCourses(Model model, HttpServletRequest request) {
		model.addAttribute("coursedit", null);
		model.addAttribute("message", null);	
		model.addAttribute("coursenew", "coursenew");
		model.addAttribute("course", new Course());
		model.addAttribute("user", userService.findByEmailAddress(request.getUserPrincipal().getName()));
		loadRegisteredEnrolledCourseList(model, request);
	}
	
	private void loadRegisteredEnrolledCourseList(Model model, HttpServletRequest request) {
		if(request.getUserPrincipal().getName() != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
			String[] roles = new String[2];
			int count = 0;
			for(Role r : user.getUserrole()){
				roles[count] = r.getRolename();
			}
			if(user != null && roles[0].equalsIgnoreCase(FACULTY)){
				model.addAttribute("mycourses", courseList(user));
				model.addAttribute("courses", courseService.courses());
			}else if(user != null && roles[0].equalsIgnoreCase(STUDENT)){
				model.addAttribute("studentcourses", enrollCourseList(user));
			}
		}
	}
	
	private List<RegisteredCourse> courseList(User member){		
		List<RegisteredCourse> mycourses = memberCourseService.registeredCourses(member);
		return mycourses;		
	}
	
	private List<EnrolledCourse> enrollCourseList(User student){		
		List<EnrolledCourse> mycourses = studentCourseService.enrolledCourses(student);
		return mycourses;		
	}
	
	@ModelAttribute("courses")
	public List<Course> loadAllCourses(){
		return courseService.courses();
	}
	
}
