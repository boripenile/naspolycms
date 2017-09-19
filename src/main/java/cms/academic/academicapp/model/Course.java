/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.academic.academicapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MURTADHA
 */
@Entity
@Table(name = "course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c"),
    @NamedQuery(name = "Course.findByCourseId", query = "SELECT c FROM Course c WHERE c.id = :courseId"),
    @NamedQuery(name = "Course.findByCourseCode", query = "SELECT c FROM Course c WHERE c.courseCode = :courseCode"),
    @NamedQuery(name = "Course.findByCourseName", query = "SELECT c FROM Course c WHERE c.courseName = :courseName"),
    @NamedQuery(name = "Course.findByInsereddate", query = "SELECT c FROM Course c WHERE c.insereddate = :insereddate"),
    @NamedQuery(name = "Course.findByUpdateddate", query = "SELECT c FROM Course c WHERE c.updateddate = :updateddate")})
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    private Long id;
    @NotNull(message="Course code is required")
    @Column(name = "course_code", unique=true, nullable = false)
    private String courseCode;
    @NotNull(message="Course name is required")
    @Column(name = "course_name", unique=true, nullable = false)
    private String courseName;
    @Basic(optional = false)
    @Column(name = "insereddate")
    @Temporal(TemporalType.DATE)
    private Date insereddate;
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<RegisteredCourse> registeredCourseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<Discussion> discussionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<EnrolledCourse> enrolledCourseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<CourseMedia> courseMediaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<CourseAssignment> courseAssignmentList;

    public Course() {
    }

    public Course(Long courseId) {
        this.id = courseId;
    }

    public Course(Long courseId, String courseCode, String courseName, Date insereddate) {
        this.id = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.insereddate = insereddate;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getInsereddate() {
        return insereddate;
    }

    public void setInsereddate(Date insereddate) {
        this.insereddate = insereddate;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    @XmlTransient
    public List<RegisteredCourse> getRegisteredCourseList() {
        return registeredCourseList;
    }

    public void setRegisteredCourseList(List<RegisteredCourse> registeredCourseList) {
        this.registeredCourseList = registeredCourseList;
    }

    @XmlTransient
    public List<Discussion> getDiscussionList() {
        return discussionList;
    }

    public void setDiscussionList(List<Discussion> discussionList) {
        this.discussionList = discussionList;
    }

    @XmlTransient
    public List<EnrolledCourse> getEnrolledCourseList() {
        return enrolledCourseList;
    }

    public void setEnrolledCourseList(List<EnrolledCourse> enrolledCourseList) {
        this.enrolledCourseList = enrolledCourseList;
    }

    @XmlTransient
    public List<CourseMedia> getCourseMediaList() {
        return courseMediaList;
    }

    public void setCourseMediaList(List<CourseMedia> courseMediaList) {
        this.courseMediaList = courseMediaList;
    }

    @XmlTransient
    public List<CourseAssignment> getCourseAssignmentList() {
        return courseAssignmentList;
    }

    public void setCourseAssignmentList(List<CourseAssignment> courseAssignmentList) {
        this.courseAssignmentList = courseAssignmentList;
    }

    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((courseCode == null) ? 0 : courseCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (courseCode == null) {
			if (other.courseCode != null)
				return false;
		} else if (!courseCode.equals(other.courseCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "cms.academic.academicapp.bean.Course[ courseId=" + id + " ]";
    }
    
}
