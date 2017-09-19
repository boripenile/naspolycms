/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.academic.academicapp.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MURTADHA
 */
@Entity
@Table(name = "discussion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Discussion.findAll", query = "SELECT d FROM Discussion d"),
    @NamedQuery(name = "Discussion.findByDiscussionId", query = "SELECT d FROM Discussion d WHERE d.id = :discussionId"),
    @NamedQuery(name = "Discussion.findByTopic", query = "SELECT d FROM Discussion d WHERE d.topic = :topic"),
    @NamedQuery(name = "Discussion.findByDescription", query = "SELECT d FROM Discussion d WHERE d.description = :description"),
    @NamedQuery(name = "Discussion.findByDiscontinue", query = "SELECT d FROM Discussion d WHERE d.discontinue = :discontinue"),
    @NamedQuery(name = "Discussion.findByInserteddate", query = "SELECT d FROM Discussion d WHERE d.inserteddate = :inserteddate"),
    @NamedQuery(name = "Discussion.findByUpdateddate", query = "SELECT d FROM Discussion d WHERE d.updateddate = :updateddate")})
public class Discussion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discussion_id")
    private Long id;
    @NotNull(message = "Topic is required")
    @Column(name = "topic", unique=true)
    private String topic;
    @Column(name = "description")
    private String description;
    @Column(name = "discontinue")
    private boolean discontinue;
    @Basic(optional = false)
    @Column(name = "inserteddate")
    @Temporal(TemporalType.DATE)
    private Calendar inserteddate;
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User owner;
    @JoinColumn(name = "member_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User member;
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    @ManyToOne(optional = false)
    private Course course;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discussion")
    private List<DiscussionFeed> discussionFeedList;

    @Transient
    private String addDate;
    
    public Discussion() {
    }

    public Discussion(Long discussionId) {
        this.id = discussionId;
    }

    public Discussion(Long discussionId, String topic, boolean discontinue, Calendar inserteddate) {
        this.id = discussionId;
        this.topic = topic;
        this.discontinue = discontinue;
        this.inserteddate = inserteddate;      
    }

    public Long getId() {
        return id;
    }

    public void setId(Long discussionId) {
        this.id = discussionId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDiscontinue() {
        return discontinue;
    }

    public void setDiscontinue(boolean discontinue) {
        this.discontinue = discontinue;
    }

    public Calendar getInserteddate() {
        return inserteddate;
    }

    public void setInserteddate(Calendar inserteddate) {
        this.inserteddate = inserteddate;
    }

    public Date getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(Date updateddate) {
        this.updateddate = updateddate;
    }

    public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
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

	@XmlTransient
    public List<DiscussionFeed> getDiscussionFeedList() {
        return discussionFeedList;
    }

    public void setDiscussionFeedList(List<DiscussionFeed> discussionFeedList) {
        this.discussionFeedList = discussionFeedList;
    }

    public String getAddDate() {
    	if(getInserteddate() != null){
    		java.util.Calendar gc = getInserteddate();
            java.util.Date d2 = gc.getTime();
            SimpleDateFormat ddmmyyyyFormat = new SimpleDateFormat("dd-MMM-yyyy");
            this.addDate = ddmmyyyyFormat.format(d2);
            return this.addDate;
    	}
        return addDate;
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
        if (!(object instanceof Discussion)) {
            return false;
        }
        Discussion other = (Discussion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cms.academic.academicapp.bean.Discussion[ discussionId=" + id + " ]";
    }
    
}
