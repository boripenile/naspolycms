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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MURTADHA
 */
@Entity
@Table(name = "course_assignment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseAssignment.findAll", query = "SELECT c FROM CourseAssignment c"),
    @NamedQuery(name = "CourseAssignment.findByCourseAssignmentId", query = "SELECT c FROM CourseAssignment c WHERE c.id = :courseAssignmentId"),
    @NamedQuery(name = "CourseAssignment.findByDiscontinue", query = "SELECT c FROM CourseAssignment c WHERE c.discontinue = :discontinue"),
    @NamedQuery(name = "CourseAssignment.findByInserteddate", query = "SELECT c FROM CourseAssignment c WHERE c.inserteddate = :inserteddate"),
    @NamedQuery(name = "CourseAssignment.findByUpdateddate", query = "SELECT c FROM CourseAssignment c WHERE c.updateddate = :updateddate")})
public class CourseAssignment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_assignment_id")
    private Long id;
    @NotNull(message = "Question is required")
    @Lob
    @Column(name = "question")
    private String question;
    @Column(name = "discontinue")
    private boolean discontinue;
    @Min(value=1)
    @Max(value=100)
    @Column(name = "obtainableMark")
    private double obtainableMark;
    @Basic(optional = false)
    @Column(name = "inserteddate")
    @Temporal(TemporalType.DATE)
    private Date inserteddate;
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assignment")
    private List<AnsweredCourseAssignment> answeredCourseAssignmentList;
    @JoinColumn(name = "member_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User member;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Course course;
     
    public CourseAssignment() {
    }

    public CourseAssignment(Long courseAssignmentId) {
        this.id = courseAssignmentId;
    }

    public CourseAssignment(Long courseAssignmentId, String question, boolean discontinue, Date inserteddate) {
        this.id = courseAssignmentId;
        this.question = question;
        this.discontinue = discontinue;
        this.inserteddate = inserteddate;
    }

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean getDiscontinue() {
        return discontinue;
    }

    public void setDiscontinue(boolean discontinue) {
        this.discontinue = discontinue;
    }

    public double getObtainableMark() {
		return obtainableMark;
	}

	public void setObtainableMark(double obtainableMark) {
		if(obtainableMark < 0 || obtainableMark > 100 ){
			this.obtainableMark = 0;
		}
		this.obtainableMark = obtainableMark;
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

    @XmlTransient
    public List<AnsweredCourseAssignment> getAnsweredCourseAssignmentList() {
        return answeredCourseAssignmentList;
    }

    public void setAnsweredCourseAssignmentList(List<AnsweredCourseAssignment> answeredCourseAssignmentList) {
        this.answeredCourseAssignmentList = answeredCourseAssignmentList;
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
		CourseAssignment other = (CourseAssignment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "cms.academic.academicapp.bean.CourseAssignment[ courseAssignmentId=" + id + " ]";
    }
    
}
