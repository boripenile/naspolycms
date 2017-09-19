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
import javax.persistence.Lob;
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
@Table(name = "course_media")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseMedia.findAll", query = "SELECT c FROM CourseMedia c"),
    @NamedQuery(name = "CourseMedia.findByCourseMediaId", query = "SELECT c FROM CourseMedia c WHERE c.id = :courseMediaId"),
    @NamedQuery(name = "CourseMedia.findByInserteddate", query = "SELECT c FROM CourseMedia c WHERE c.inserteddate = :inserteddate"),
    @NamedQuery(name = "CourseMedia.findByUpdateddate", query = "SELECT c FROM CourseMedia c WHERE c.updateddate = :updateddate")})
public class CourseMedia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_media_id")
    private Long id;
    @Column(name = "filename")
    private String filename;
    @Column(name = "note")
    private String notes;
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Lob
    @Column(name = "media")
    private byte[] media;    
    @Basic(optional = false)
    @Column(name = "inserteddate")
    @Temporal(TemporalType.DATE)
    private Date inserteddate;
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
    @JoinColumn(name = "member_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User member;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Course course;

    public CourseMedia() {
    }

    public CourseMedia(Long courseMediaId) {
        this.id = courseMediaId;
    }

    public CourseMedia(Long courseMediaId, byte[] media, Date inserteddate) {
        this.id = courseMediaId;
        this.media = media;
        this.inserteddate = inserteddate;
    }


    public byte[] getMedia() {
        return media;
    }

    public void setMedia(byte[] media) {
        this.media = media;
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
    
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseMedia)) {
            return false;
        }
        CourseMedia other = (CourseMedia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cms.academic.academicapp.bean.CourseMedia[ courseMediaId=" + id + " ]";
    }
    
}
