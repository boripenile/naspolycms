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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MURTADHA
 */
@Entity
@Table(name = "discussion_feed")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiscussionFeed.findAll", query = "SELECT d FROM DiscussionFeed d"),
    @NamedQuery(name = "DiscussionFeed.findByDiscussionfeedFK", query = "SELECT d FROM DiscussionFeed d WHERE d.id = :discussionfeedFK"),
    @NamedQuery(name = "DiscussionFeed.findByInserteddate", query = "SELECT d FROM DiscussionFeed d WHERE d.inserteddate = :inserteddate"),
    @NamedQuery(name = "DiscussionFeed.findByUpdateddate", query = "SELECT d FROM DiscussionFeed d WHERE d.updateddate = :updateddate")})
public class DiscussionFeed implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discussion_feed_FK")
    private Long id;
    @NotNull(message = "Comment is required")
    @Lob
    @Column(name = "comment")
    private String comment;
    @Basic(optional = false)
    @Column(name = "inserteddate")
    @Temporal(TemporalType.DATE)
    private Calendar inserteddate;
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
    @JoinColumn(name = "discussion_id", referencedColumnName = "discussion_id")
    @ManyToOne(optional = false)
    private Discussion discussion;
    @JoinColumn(name = "contributor_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User contributor;

    @Transient
    private String addDate;
    
    public DiscussionFeed() {
    }

    public DiscussionFeed(Long discussionfeedFK) {
        this.id = discussionfeedFK;
    }

    public DiscussionFeed(Long discussionfeedFK, String comment, Calendar inserteddate) {
        this.id = discussionfeedFK;
        this.comment = comment;
        this.inserteddate = inserteddate;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long discussionfeedFK) {
        this.id = discussionfeedFK;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    
    public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

	public User getContributor() {
		return contributor;
	}

	public void setContributor(User contributor) {
		this.contributor = contributor;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
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
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiscussionFeed)) {
            return false;
        }
        DiscussionFeed other = (DiscussionFeed) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cms.academic.academicapp.bean.DiscussionFeed[ discussionfeedFK=" + id + " ]";
    }
    
}
