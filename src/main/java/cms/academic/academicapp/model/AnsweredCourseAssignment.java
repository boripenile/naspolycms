/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.academic.academicapp.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MURTADHA
 */
@Entity
@Table(name = "answered_course_assignment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnsweredCourseAssignment.findAll", query = "SELECT a FROM AnsweredCourseAssignment a"),
    @NamedQuery(name = "AnsweredCourseAssignment.findByAnsweredCourseAssignmentId", query = "SELECT a FROM AnsweredCourseAssignment a WHERE a.id = :answeredCourseAssignmentId"),
    @NamedQuery(name = "AnsweredCourseAssignment.findByInserteddate", query = "SELECT a FROM AnsweredCourseAssignment a WHERE a.inserteddate = :inserteddate"),
    @NamedQuery(name = "AnsweredCourseAssignment.findByUpdateddate", query = "SELECT a FROM AnsweredCourseAssignment a WHERE a.updateddate = :updateddate")})
public class AnsweredCourseAssignment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "answered_course_assignment_id")
    private Long id;    
    @Lob
    @Column(name = "answer")
    private String answer;
    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "obtained")
    private double obtained;
    
    @Column(name = "inserteddate")
    @Temporal(TemporalType.DATE)
    private Date inserteddate;
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User student;
    @JoinColumn(name = "assignment_id", referencedColumnName = "course_assignment_id")
    @ManyToOne
    private CourseAssignment assignment;

    public AnsweredCourseAssignment() {
    }

    public AnsweredCourseAssignment(Long id) {
        this.id = id;
    }

    public AnsweredCourseAssignment(Long id, String answer, Date inserteddate) {
        this.id = id;
        this.answer = answer;
        this.inserteddate = inserteddate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    
    public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public CourseAssignment getAssignment() {
		return assignment;
	}

	public double getObtained() {
		return obtained;
	}

	public void setObtained(double obtained) {
		if(obtained < 0 || obtained > 100){
			this.obtained = 0;
		}
		this.obtained = obtained;
	}

	public void setAssignment(CourseAssignment assignment) {
		this.assignment = assignment;
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

    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result
				+ ((assignment == null) ? 0 : assignment.hashCode());
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
		AnsweredCourseAssignment other = (AnsweredCourseAssignment) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (assignment == null) {
			if (other.assignment != null)
				return false;
		} else if (!assignment.equals(other.assignment))
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
        return "cms.academic.academicapp.bean.AnsweredCourseAssignment[ Id=" + id + " ]";
    }
    
}
