/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.academic.academicapp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MURTADHA
 */
@Entity
@Table(name = "enrolled_course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EnrolledCourse.findAll", query = "SELECT e FROM EnrolledCourse e"),
    @NamedQuery(name = "EnrolledCourse.findByEnrolledCourseId", query = "SELECT e FROM EnrolledCourse e WHERE e.id = :enrolledCourseId"),
    @NamedQuery(name = "EnrolledCourse.findByInserteddate", query = "SELECT e FROM EnrolledCourse e WHERE e.inserteddate = :inserteddate"),
    @NamedQuery(name = "EnrolledCourse.findByUpdateddate", query = "SELECT e FROM EnrolledCourse e WHERE e.updateddate = :updateddate")})
public class EnrolledCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "enrolled_course_id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "inserteddate")
    @Temporal(TemporalType.DATE)
    private Date inserteddate;
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User student;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Course course;

    public EnrolledCourse() {
    }

    public EnrolledCourse(Long id) {
		super();
		this.id = id;
	}

	public EnrolledCourse(Long id, Date inserteddate, User student,
			Course course) {
		super();
		this.id = id;
		this.inserteddate = inserteddate;
		this.student = student;
		this.course = course;
	}


	public Date getInserteddate() {
        return inserteddate;
    }

    public void setInserteddate(Date inserteddate) {
        this.inserteddate = inserteddate;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		EnrolledCourse other = (EnrolledCourse) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "cms.academic.academicapp.bean.EnrolledCourse[ enrolledCourseId=" + id + " ]";
    }
    
}
