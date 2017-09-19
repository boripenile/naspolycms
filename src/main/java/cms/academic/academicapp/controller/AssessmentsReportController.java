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

import cms.academic.academicapp.model.AnsweredCourseAssignment;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseAssignment;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.AnsweredCourseAssignmentService;
import cms.academic.academicapp.service.CourseService;
import cms.academic.academicapp.service.MemberCourseAssignmentService;
import cms.academic.academicapp.service.MemberCourseService;
import cms.academic.academicapp.service.StudentCourseService;
import cms.academic.academicapp.service.UserService;

@Controller
@RequestMapping("/assessmentsreport")
public class AssessmentsReportController {
	
	private StudentCourseService studentCourseService;
	private MemberCourseService memberCourseService;
	private MemberCourseAssignmentService memberAssignmentCourseService;
	private UserService userService;
	private CourseService courseService;
	private AnsweredCourseAssignmentService answeredAssignmentService;	
	
	@Autowired
	public AssessmentsReportController(StudentCourseService studentCourseService, UserService userService,
			AnsweredCourseAssignmentService answeredAssignmentService, 
			MemberCourseService memberCourseService, CourseService courseService,
			MemberCourseAssignmentService memberCourseAssignmentService) {			
		this.studentCourseService = studentCourseService;
		this.userService = userService;		
		this.answeredAssignmentService = answeredAssignmentService;
		this.memberCourseService = memberCourseService;
		this.memberAssignmentCourseService = memberCourseAssignmentService;
		this.courseService = courseService;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String reportPage(Model model,
			HttpServletRequest request){
		loadFacultyCourses(model, request);
		return "user/assessmentreport";
	}

	private void loadFacultyCourses(Model model, HttpServletRequest request) {
		String emailAddress = request.getUserPrincipal().getName();
		if(emailAddress != null){
			User user = userService.findByEmailAddress(emailAddress);
			if(user != null){
				List<RegisteredCourse> registeredCourses = memberCourseService.registeredCourses(user);
				if(registeredCourses != null){
					model.addAttribute("registeredcourses", registeredCourses);
				}
			}
		}
	}
	
	@RequestMapping(value="/report", method=RequestMethod.POST)
	public String report(@RequestParam(value="email", required=true)String emailAddress,Model model,
			HttpServletRequest request){
		if(emailAddress != ""){
			User user = userService.findByEmailAddress(emailAddress);
			if(user != null){
				
				List<EnrolledCourse> enrolledCourses = enrolledCourseList(user);
				List<AnsweredCourseAssignment> answeredAssessments = answeredCoursesList(user);	
				model.addAttribute("user", user);	
				if(enrolledCourses != null){
					model.addAttribute("enrolledcourses", enrolledCourses);		
				}
				if(answeredAssessments != null){
					model.addAttribute("answeredassessments", answeredAssessments);	
				}
			}else{
				model.addAttribute("message", "No student was found.");		
			}		
		}else{
			model.addAttribute("message", "Email Address field cannot be empty.");			
		}
		loadFacultyCourses(model, request);
		return "user/assessmentreport";
	}
	
	@RequestMapping(value="/report/{courseId}", method=RequestMethod.GET)
	public String reportByCourse(@PathVariable(value="courseId")Long courseId, Model model,
			HttpServletRequest request){
		if(courseId != null){
			String emailAddress = request.getUserPrincipal().getName();
			User user = userService.findByEmailAddress(emailAddress);
			Course course = courseService.findById(courseId);			
			List<CourseAssignment> courseAssignment = memberAssignmentCourseService.viewAssignmentsByCourse(course);
			if(user != null && course != null && courseAssignment != null){				
				List<AnsweredCourseAssignment> answered = answeredAssignmentService.viewAnsweredCourseAssignmentByMember(course, user);
				model.addAttribute("courseassignments", courseAssignment);
				model.addAttribute("answers", answered);
				model.addAttribute("course", course);
				model.addAttribute("lecturer", user);
				model.addAttribute("students", studentCourseService.viewEnrolledStudentsPerCourse(course));
			}
		}		
		loadFacultyCourses(model, request);
		return "user/assessmentreport";
	}
	
	private List<EnrolledCourse> enrolledCourseList(User student){		
		List<EnrolledCourse> mycourses = studentCourseService.enrolledCourses(student);
		return mycourses;		
	}
	
	private List<AnsweredCourseAssignment> answeredCoursesList(User student){		
		List<AnsweredCourseAssignment> answered = answeredAssignmentService.viewAnsweredCourseAssignmentsByStudent(student);
		return answered;		
	}
}
