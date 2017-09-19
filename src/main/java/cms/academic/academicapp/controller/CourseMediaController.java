package cms.academic.academicapp.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cms.academic.academicapp.model.Course;
import cms.academic.academicapp.model.CourseMedia;
import cms.academic.academicapp.model.EnrolledCourse;
import cms.academic.academicapp.model.RegisteredCourse;
import cms.academic.academicapp.model.Role;
import cms.academic.academicapp.model.User;
import cms.academic.academicapp.service.CourseMediaService;
import cms.academic.academicapp.service.CourseService;
import cms.academic.academicapp.service.MemberCourseService;
import cms.academic.academicapp.service.StudentCourseService;
import cms.academic.academicapp.service.UserService;

@Controller
@RequestMapping("/media")
public class CourseMediaController {

	private UserService userService;
	private CourseService courseService;
	private CourseMediaService courseMediaService;
	private MemberCourseService memberCourseService;
	private StudentCourseService studentCourseService;

	private static final String FACULTY = "FACULTY";
	private static final String STUDENT = "STUDENT";
	
	@Autowired
	public CourseMediaController(UserService userService,
			CourseService courseService, CourseMediaService courseMediaService,
			MemberCourseService memberCourseService,
			StudentCourseService studentCourseService) {		
		this.userService = userService;
		this.courseService = courseService;
		this.courseMediaService = courseMediaService;
		this.memberCourseService = memberCourseService;
		this.studentCourseService = studentCourseService;
	}


	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String coursesListByUser(Model model,
			HttpServletRequest request){
		loadRegisteredEnrolledCourseList(model, request);
		return "user/media";
	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam(value="courseId", required=false)Long courseId,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        if(courseId != null){
        	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    		MultipartFile multipartFile = multipartRequest.getFile("media");
    		User user = userService.findByEmailAddress(request.getUserPrincipal().getName());    		
    		Course course = courseService.findById(courseId);
    		if(!multipartFile.isEmpty()){
    			CourseMedia file = new CourseMedia();
        		file.setFilename(multipartFile.getOriginalFilename());
        		file.setNotes(ServletRequestUtils.getStringParameter(request, "notes"));
        		file.setType(multipartFile.getContentType());
        		file.setMedia(multipartFile.getBytes());
                file.setMember(user);
                file.setCourse(course);
                file.setInserteddate(Calendar.getInstance().getTime());
                
        		courseMediaService.addCourseMedia(file);
    		}else{
    			model.addAttribute("message", "Select a file to upload.");
    		}  		
    		
    		loadCourseMedia(courseId, model);
        }
        
		loadRegisteredEnrolledCourseList(model, request);
		return "user/media";
	}

	/**
	 * download
	 */
	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public String download(@PathVariable("id")Long id,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CourseMedia file = courseMediaService.findMediaById(id);

		response.setContentType(file.getType());
		response.setContentLength(file.getMedia().length);
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file.getFilename() + "\"");

		FileCopyUtils.copy(file.getMedia(), response.getOutputStream());

		return null;
	}

	/**
	 * delete
	 */
	@RequestMapping(value = "/delete/{id}/{courseId}", method = RequestMethod.GET)
	public String delete(@PathVariable("id")Long id,@PathVariable("courseId")Long courseId,
			HttpServletRequest request,	HttpServletResponse response, Model model) throws Exception {
		
		CourseMedia file = courseMediaService.findMediaById(id);
		
		courseMediaService.removeCourseMedia(file);
		
		loadCourseMedia(courseId, model);
		loadRegisteredEnrolledCourseList(model, request);
		return "user/media";
	}
	
	@RequestMapping(value = "/view/{courseId}", method = RequestMethod.GET)
	public String viewCourseMedia(@PathVariable("courseId")Long courseId ,HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {		
		loadCourseMedia(courseId, model);	
		loadRegisteredEnrolledCourseList(model, request);
		return "user/media";
	}

	private void loadCourseMedia(Long courseId, Model model) {
		if(courseId != null){
			model.addAttribute("allmedia", null);
			Course course = courseService.findById(courseId);
			List<CourseMedia> media = courseMediaService.courseMediaList(course);
			model.addAttribute("course", course);
			if(media != null && media.size() > 0){
				model.addAttribute("allmedia", media);
			}
			
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
