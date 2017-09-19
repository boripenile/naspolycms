package cms.academic.academicapp.controller;

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

import cms.academic.academicapp.model.AnsweredCourseAssignment;
import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseAssignment;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.Role;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.AnsweredCourseAssignmentService;
import cms.academic.academicapp.service.CourseService;
import cms.academic.academicapp.service.MemberCourseAssignmentService;
import cms.academic.academicapp.service.MemberCourseService;
import cms.academic.academicapp.service.StudentCourseService;
import cms.academic.academicapp.service.UserService;
import cms.academic.academicapp.utils.DataHelper;

@Controller
@RequestMapping("/assignments")
public class AssignmentController {
	
	private MemberCourseService memberCourseService;
	private StudentCourseService studentCourseService;
	private UserService userService;
	private CourseService courseService;
	private MemberCourseAssignmentService assignmentService;
	private AnsweredCourseAssignmentService answeredAssignmentService;
	
	private static final String FACULTY = "FACULTY";
	private static final String STUDENT = "STUDENT";
	private static final double TOTAL_MARK_PER_COURSE = 100;
	
	@Autowired
	public AssignmentController(MemberCourseService memberCourseService, StudentCourseService studentCourseService,
			UserService userService, CourseService courseService, MemberCourseAssignmentService assignmentService,
			AnsweredCourseAssignmentService answeredAssignmentService) {
		super();
		this.memberCourseService = memberCourseService;
		this.studentCourseService = studentCourseService;
		this.userService = userService;
		this.courseService = courseService;
		this.assignmentService = assignmentService;
		this.answeredAssignmentService = answeredAssignmentService;
	}
	
	// Start Assignment management by Lecturers
	@RequestMapping(value="/courseslist", method=RequestMethod.GET)
	public String coursesListByUser(Model model,
			HttpServletRequest request){
		
		loadRegisteredEnrolledCourseList(model, request);
		model.addAttribute("myassignments", null);
		return "user/assignments";
	}
	
	@RequestMapping(value="/fcourses/assignment/{regid}/new", method=RequestMethod.GET)
	public String coursesNewAssignment(@PathVariable("regid")Long regcourseId, Model model,
			HttpServletRequest request){
		if(regcourseId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null){
            	RegisteredCourse mycourse = memberCourseService.viewRegisteredCourse(regcourseId);
            	Course course = courseService.findById(mycourse.getCourse().getId());
            	CourseAssignment assignment = new CourseAssignment();
            	assignment.setCourse(course);
            	assignment.setMember(user);
                model.addAttribute("assignment", assignment);  
                model.addAttribute("myassignments", null);
                model.addAttribute("assignmentnew", "assignmentnew");
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		return "user/assignments";
	}
	
	@RequestMapping(value="/fcourses/assignment/add", method=RequestMethod.POST)
	public String addNewAssignment(@RequestParam(value="courseId", required=true)Long courseId,
			@RequestParam(value="memberId", required=true)Long memberId, @ModelAttribute("assignment")@Valid CourseAssignment courseAssignment, HttpServletRequest request, 
			BindingResult result, Model model){		
			if(courseId != null && memberId != null){
				if(courseAssignment.getQuestion() == ""){
					model.addAttribute("error", "Question is required.");
				}
				if(result.hasErrors()){
					model.addAttribute("error", "Complete required fields.");
					loadRegisteredEnrolledCourseList(model, request);
					return "user/assignments";
				}else{
					Course course = courseService.findById(courseId);
					User user = userService.findById(memberId);
					courseAssignment.setInserteddate(Calendar.getInstance().getTime());
					courseAssignment.setCourse(course);
					courseAssignment.setMember(user);
					boolean isExceeded = checkTotalMark(courseAssignment);
					if(isExceeded){
						model.addAttribute("message", "Total marks for assessments per course can not exceed 100.");
					}else{
						int out = assignmentService.addCourseAssignmennt(courseAssignment);
						model.addAttribute("message", out + " record added successfully");
					}
					
	            	Course mycourse = courseService.findById(courseId);
					List<CourseAssignment> courseAssignments = loadCourseAssignmentList(mycourse);
	                model.addAttribute("courseassignments", courseAssignments);
	                model.addAttribute("course", mycourse);
					model.addAttribute("myassignments", "myassignments");
	                model.addAttribute("assignmentnew", null);
				}	
			}		
		loadRegisteredEnrolledCourseList(model, request);
		return "user/assignments";
	}
	
	@RequestMapping(value="/fcourses/assignment/{id}/view", method=RequestMethod.GET)
	public String viewAssignment(@PathVariable("id")Long assignId, Model model,
			HttpServletRequest request){
		if(assignId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null){
            	CourseAssignment assignment = assignmentService.viewCourseAssignment(assignId);
                model.addAttribute("assignment", assignment); 
                model.addAttribute("myassignments", null);
                model.addAttribute("assignmentedit", "assignmentedit");
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		
		return "user/assignments";
	}
	
	@RequestMapping(value="/fcourses/assignment/update", method=RequestMethod.POST)
	public String updateAssignment(@RequestParam(value="courseId", required=true)Long courseId,
			@RequestParam(value="memberId", required=true)Long memberId,@ModelAttribute("assignment")@Valid CourseAssignment courseAssignment, HttpServletRequest request, 
			@RequestParam(value="id", required=false)Long assignmentId, BindingResult result, Model model){		
			if(result.hasErrors()){
				model.addAttribute("error", "Complete required fields.");
				loadRegisteredEnrolledCourseList(model, request);
				return "user/assignments";
			}			
			if(courseId != null && memberId != null){				
				if(assignmentId != null){
					CourseAssignment assignment = assignmentService.viewCourseAssignment(assignmentId);
					double markBUFF = assignment.getObtainableMark();
	                if(assignment != null){	                	
	                	//assignment.setQuestion(courseAssignment.getQuestion());
	                	assignment.setObtainableMark(courseAssignment.getObtainableMark());
	                	boolean isExceeded = checkTotalMark(assignment);
						if(isExceeded){
							assignment.setObtainableMark(markBUFF);
							model.addAttribute("message", "Total marks for assessments per course can not exceed 100.");							
						}else{	
							assignment.setQuestion(courseAssignment.getQuestion());
		                	assignment.setObtainableMark(courseAssignment.getObtainableMark());
							int out = assignmentService.editCourseAssignment(assignment);
							resetStudentsMarkToZero(assignment,request);
							model.addAttribute("message", out + " record updated successfully");
						}	                	
	                	Course mycourse = courseService.findById(courseId);
	                	List<CourseAssignment> courseAssignments = loadCourseAssignmentList(mycourse);
		                model.addAttribute("courseassignments", courseAssignments);
		                model.addAttribute("course", mycourse);
	                	
	                	model.addAttribute("myassignments", "myassignments");
	                    model.addAttribute("assignmentedit", null);
	                }
				}				
			}		
		loadRegisteredEnrolledCourseList(model, request);
		return "user/assignments";
	}
	
	@RequestMapping(value="/fcourses/{regid}", method=RequestMethod.GET)
	public String courseAssignments(@PathVariable("regid")Long regcourseId, Model model,
			HttpServletRequest request){
		if(regcourseId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null){
            	Course mycourse = courseService.findById(regcourseId);
            	List<CourseAssignment> courseAssignments = loadCourseAssignmentList(mycourse);
                model.addAttribute("courseassignments", courseAssignments);
                model.addAttribute("course", mycourse);
                model.addAttribute("myassignments", "myassignments");
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		return "user/assignments";
	}

	@RequestMapping(value="/fcourses/assignment/{id}/studentsattempt", method=RequestMethod.GET)
	public String viewStudentsAttempt(@PathVariable("id")Long courseId, Model model,
			HttpServletRequest request){
		if(courseId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null){
            	//Course mycourse = courseService.findById(courseId);
            	CourseAssignment assignment = assignmentService.viewCourseAssignment(courseId);
            	List<AnsweredCourseAssignment> answeredCourseAssignments = loadStudentsAttemptQuestionsList(assignment, user);
                if(answeredCourseAssignments != null && answeredCourseAssignments.size() > 0){
                	model.addAttribute("answeredcourseassignments", answeredCourseAssignments);
                    model.addAttribute("course", assignment.getCourse());
                    model.addAttribute("myassignments", null);
                    model.addAttribute("assignment", assignment);
                    model.addAttribute("answeredassignments", "answeredassignments");
                }else{
                	List<CourseAssignment> courseAssignments = loadCourseAssignmentList(assignment.getCourse());
                    model.addAttribute("courseassignments", courseAssignments);
                    model.addAttribute("course", assignment.getCourse());
                    model.addAttribute("message", "No student has attempted the selected assessment.");
                    model.addAttribute("myassignments", "myassignments");
                }            	
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		return "user/assignments";
	}
	
	@RequestMapping(value="/fcourses/assignment/{id}/attempted", method=RequestMethod.GET)
	public String viewStudentsAttemptPerStudent(@PathVariable("id")Long assignId, Model model,
			HttpServletRequest request){
		if(assignId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null && assignId != null){
            	AnsweredCourseAssignment answered = answeredAssignmentService.viewAnsweredCourseAssignmentByStudent(assignId);
                model.addAttribute("answered", answered); 
                model.addAttribute("course", answered.getAssignment().getCourse());
                model.addAttribute("gradeanswered", "gradeanswered");
                model.addAttribute("myassignments", null);                
                model.addAttribute("answeredassignments", null);
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		
		return "user/assignments";
	}
	
	@RequestMapping(value="/fcourses/assignment/attempt", method=RequestMethod.POST)
	public String viewStudentsAttemptPerStudentUpdate(@RequestParam(value="id", required=true)Long assignId,
			Model model, @ModelAttribute("answered") AnsweredCourseAssignment answered,
			HttpServletRequest request){
		if(assignId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null && assignId != null){
            	AnsweredCourseAssignment selected = answeredAssignmentService.viewAnsweredCourseAssignmentByStudent(assignId);
            	double markBUFF = selected.getObtained();
            	selected.setObtained(answered.getObtained());
                if(selected.getObtained() > selected.getAssignment().getObtainableMark()){
                	selected.setObtained(markBUFF);
                	model.addAttribute("message", "Student's obtained mark cannot be greater than the assessment's obtainable mark.");
                }else{
                	int out = answeredAssignmentService.editAnsweredCourseAssignmentByStudent(selected);
                	if(out > 0){
                		model.addAttribute("message", out + " assessment success updated.");
                	}
                }
                
            	CourseAssignment assignment = assignmentService.viewCourseAssignment(selected.getAssignment().getId());
            	List<AnsweredCourseAssignment> answeredCourseAssignments = loadStudentsAttemptQuestionsList(assignment, user);
            	model.addAttribute("answeredcourseassignments", answeredCourseAssignments);
                model.addAttribute("course", selected.getAssignment().getCourse());
                model.addAttribute("assignment", assignment);
                model.addAttribute("gradeanswered", null);
                model.addAttribute("myassignments", null);                
                model.addAttribute("answeredassignments", "answeredassignments");
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		
		return "user/assignments";
	}
	
	private boolean checkTotalMark(CourseAssignment courseAssignment){
		List<CourseAssignment> all = assignmentService.viewAssignmentsByCourse(courseAssignment.getCourse());
		
		double total = courseAssignment.getObtainableMark(); 
		for(CourseAssignment c : all){
			if(!c.getId().equals(courseAssignment.getId())){
				total += c.getObtainableMark();
			}			
		}		
		
		//total += 
		if(total > TOTAL_MARK_PER_COURSE){
			return true;
		}
		return false;
	}
	
	// End Assignment Management
	
	// Start Assignment Attempt by Students
	@RequestMapping(value="/scourses/{enrolid}", method=RequestMethod.GET)
	public String coursesAssignmentsForStudent(@PathVariable("enrolid")Long enrolId, Model model,
			HttpServletRequest request){
		if(enrolId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null){
            	Course mycourse = courseService.findById(enrolId);
            	List<CourseAssignment> courseAssignments = loadCourseAssignmentListForStudent(mycourse);
                model.addAttribute("scourseassignments", courseAssignments);
                model.addAttribute("course", mycourse);
                model.addAttribute("myassignments", "myassignments");
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		return "user/assignments";
	}
	
	@RequestMapping(value="/scourses/assignment/{id}/view", method=RequestMethod.GET)
	public String viewAssignmentByStudent(@PathVariable("id")Long assignId, Model model,
			HttpServletRequest request){
		if(assignId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null){
            	CourseAssignment assignment = assignmentService.viewCourseAssignment(assignId);
            	AnsweredCourseAssignment attemptassignment = new AnsweredCourseAssignment();
            	attemptassignment.setAssignment(assignment);
            	attemptassignment.setStudent(user);
            	boolean attemped = isAttempted(attemptassignment,request);
            	if(attemped){
            		model.addAttribute("message", "You have attempted the selected assessment.");
            		model.addAttribute("myassignments", "myassignments");
                    model.addAttribute("assignmentattempt", null);
                   
                	List<CourseAssignment> courseAssignments = loadCourseAssignmentList(assignment.getCourse());
	                model.addAttribute("scourseassignments", courseAssignments);
            	}else{
            		 model.addAttribute("attemptassignment", attemptassignment); 
                     
            		 model.addAttribute("myassignments", null);
                     model.addAttribute("assignmentattempt", "assignmentattempt");
            	}
               
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		
		return "user/assignments";
	}
	
	private boolean isAttempted(AnsweredCourseAssignment assignment,HttpServletRequest request ){
		User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
		List<AnsweredCourseAssignment> all = answeredAssignmentService.viewAnsweredCourseAssignmentByStudent(assignment.getAssignment().getCourse(), user);
		
		for(AnsweredCourseAssignment c : all){
			if(c.getAssignment().getId().equals(assignment.getAssignment().getId()) &&
					c.getStudent().equals(user)){
				return true;
			}
		}
		
		return false;
	}
	
	
	private void resetStudentsMarkToZero(CourseAssignment assignment,HttpServletRequest request){
		User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
		List<AnsweredCourseAssignment> all = answeredAssignmentService.viewAnsweredCourseAssignmentByMember(assignment, user);
		
		for(AnsweredCourseAssignment c : all){
			if(c.getAssignment().getId().equals(assignment.getId()) &&
					c.getObtained() > assignment.getObtainableMark()){
				AnsweredCourseAssignment as = answeredAssignmentService.viewAnsweredCourseAssignmentByStudent(c.getId());
				as.setObtained(new Double("0.0"));
				answeredAssignmentService.editAnsweredCourseAssignmentByStudent(as);
			}
		}
	}
	@RequestMapping(value="/scourses/assignment/{id}/review", method=RequestMethod.GET)
	public String reviewAssignmentByStudent(@PathVariable("id")Long assignId, Model model,
			HttpServletRequest request){
		if(assignId != null){
			User user = userService.findByEmailAddress(request.getUserPrincipal().getName());
            if(user != null){
            	CourseAssignment assignment = assignmentService.viewCourseAssignment(assignId);
            	AnsweredCourseAssignment attemptassignment = new AnsweredCourseAssignment();
            	attemptassignment.setAssignment(assignment);
            	attemptassignment.setStudent(user);
            	
                model.addAttribute("attemptassignment", attemptassignment); 
                model.addAttribute("myassignments", null);
                model.addAttribute("assignmentattempt", "assignmentattempt");
            }			
		}
		loadRegisteredEnrolledCourseList(model, request);
		
		return "user/assignments";
	}
	
	@RequestMapping(value="/scourses/attemptassignment", method=RequestMethod.POST)
	public String attemptAssignmentByStudent(@RequestParam(value="studentId", required=true)Long studentId,
			@RequestParam(value="courseId", required=true)Long courseId,
			@RequestParam(value="answer", required=true)String answer, HttpServletRequest request, 
			@RequestParam(value="assignmentId", required=false)Long assignmentId, Model model){
		try {
			if(studentId != null && courseId != null){				
				if(assignmentId != null){
					CourseAssignment assignment = assignmentService.viewCourseAssignment(assignmentId);
	                User user = userService.findById(studentId);
					if(assignment != null && user != null){
						if(answer == ""){
							model.addAttribute("message", "Answer field cannot be empty.");
						}else{
							AnsweredCourseAssignment answeredAssignment = new AnsweredCourseAssignment();
							answeredAssignment.setAssignment(assignment);   
		                	answeredAssignment.setStudent(user);
		                	answeredAssignment.setId(new Long(3));
		                	answeredAssignment.setAnswer(answer);
		                	answeredAssignment.setObtained(new Double("0.0"));
		                	answeredAssignment.setInserteddate(Calendar.getInstance().getTime());
		                	if(!isAssessmentSubmitted(answeredAssignment)){
		                		int out = DataHelper.addAnsweredAssessment(answeredAssignment);		                	
			                	if(out > 0){
			                		model.addAttribute("message", out + " record submitted successfully");
			                	}else{
			                		model.addAttribute("message", "You have attempted the question.");
			                	}
		                	}		                	
						}	                	
	                	
	                	model.addAttribute("myassignments", "myassignments");
	                	Course mycourse = courseService.findById(courseId);
	                	List<CourseAssignment> courseAssignments = loadCourseAssignmentList(mycourse);
		                model.addAttribute("scourseassignments", courseAssignments);
		                model.addAttribute("course", mycourse);
	                    model.addAttribute("assignmentattempt", null);
	                }
				}				
			}			
		} catch (Exception e) {
			throw new IllegalArgumentException("Error occured: " + e.getMessage());
		}
		loadRegisteredEnrolledCourseList(model, request);
		return "user/assignments";
	}
	
	public boolean isAssessmentSubmitted(
			AnsweredCourseAssignment assignment) {
		if(assignment != null){
			if(assignment.getAnswer() == ""){
				return true;
			}
			else{
				try {
					List<AnsweredCourseAssignment> all = answeredAssignmentService.getAllAnswers();
					if(all != null){
						for (AnsweredCourseAssignment a : all){
							if(a.getStudent().equals(assignment.getStudent()) && 
									a.getAssignment().getQuestion().equals(assignment.getAssignment().getQuestion())){
								return true;
							}
						}
					}					
					
				} catch (Exception e) {
					
				}
			}
		}
		return false;
		
	}
	// End Assignment Attempt
	
	private List<AnsweredCourseAssignment> loadStudentsAttemptQuestionsList(CourseAssignment assignment, User user){		
		List<AnsweredCourseAssignment> answeredAssignments 
				= answeredAssignmentService.viewAnsweredCourseAssignmentByMember(assignment, user);
	    if(answeredAssignments != null){
	    	return answeredAssignments;
	    }else{
	    	return null;
	    }
	}
	
	private List<CourseAssignment> loadCourseAssignmentList(Course regCourse){
		List<CourseAssignment> courseAssignments = assignmentService.viewCourseAssignments(regCourse);
	    if(courseAssignments != null){
	    	return courseAssignments;
	    }else{
	    	return null;
	    }
	}
	
	private List<CourseAssignment> loadCourseAssignmentListForStudent(Course enrolCourse){
		List<CourseAssignment> courseAssignments = assignmentService.viewAssignmentsByCourse(enrolCourse);
	    if(courseAssignments != null){
	    	return courseAssignments;
	    }else{
	    	return null;
	    }
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
}
