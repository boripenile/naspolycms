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
@Table(name = "registered_course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RegisteredCourse.findAll", query = "SELECT r FROM RegisteredCourse r"),
    @NamedQuery(name = "RegisteredCourse.findByRegisteredCourseId", query = "SELECT r FROM RegisteredCourse r WHERE r.id = :registeredCourseId"),
    @NamedQuery(name = "RegisteredCourse.findByInserteddate", query = "SELECT r FROM RegisteredCourse r WHERE r.inserteddate = :inserteddate"),
    @NamedQuery(name = "RegisteredCourse.findByUpdateddate", query = "SELECT r FROM RegisteredCourse r WHERE r.updateddate = :updateddate")})
public class RegisteredCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "registered_course_id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "inserteddate")
    @Temporal(TemporalType.DATE)
    private Date inserteddate;
    @Basic(optional = false)
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
    @JoinColumn(name = "member_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User member;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Course course;

    public RegisteredCourse() {
    }

    public RegisteredCourse(Long registeredCourseId) {
        this.id = registeredCourseId;
    }

    public RegisteredCourse(Long registeredCourseId, Date inserteddate, Date updateddate) {
        this.id = registeredCourseId;
        this.inserteddate = inserteddate;
        this.updateddate = updateddate;
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

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
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
		result = prime * result + ((member == null) ? 0 : member.hashCode());
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
		RegisteredCourse other = (RegisteredCourse) obj;
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
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "cms.academic.academicapp.bean.RegisteredCourse[ registeredCourseId=" + id + " ]";
    }
    
}
