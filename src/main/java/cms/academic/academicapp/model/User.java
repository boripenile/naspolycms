/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.academic.academicapp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Email;


/**
 *
 * @author MURTADHA
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.id = :userId"),
    @NamedQuery(name = "User.findByFirstname", query = "SELECT u FROM User u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "User.findByLastname", query = "SELECT u FROM User u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "User.findByEmailaddress", query = "SELECT u FROM User u WHERE u.emailaddress = :emailaddress"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByGender", query = "SELECT u FROM User u WHERE u.gender = :gender"),
    @NamedQuery(name = "User.findByInserteddate", query = "SELECT u FROM User u WHERE u.inserteddate = :inserteddate"),
    @NamedQuery(name = "User.findByUpdateddate", query = "SELECT u FROM User u WHERE u.updateddate = :updateddate")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @NotNull(message="First name is required")
    @Column(name = "firstname")
    private String firstname;
    @NotNull(message="Last name is required")
    @Column(name = "lastname")
    private String lastname;
    @Email(message="Enter a valid email address")
    @NotNull(message="Email Address is required")
    @Column(name = "emailaddress", unique=true)
    private String emailaddress;
    @NotNull(message="Password is required")
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "gender")
    private String gender;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Basic(optional = false)
    @Column(name = "inserteddate")
    @Temporal(TemporalType.DATE)
    private Date inserteddate;
    @Column(name = "updateddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateddate = new Date();
	@Column(name = "enabled", columnDefinition="tinyint(1) DEFAULT '1'")
	private Boolean enabled;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<RegisteredCourse> registeredCourseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Discussion> ownerDiscussions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Discussion> memberDiscussions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<EnrolledCourse> enrolledCourseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<CourseMedia> courseMediaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    private List<AnsweredCourseAssignment> answeredCourseAssignmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<CourseAssignment> courseAssignmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contributor")
    private List<DiscussionFeed> discussionFeedList;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(// @formatter:off
    	name = "user_role",	
        joinColumns =        { @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") }, 
        inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") }
    ) // @formatter:on
    private Set<Role> userrole;

    public User() {
    }

    public User(Long userId) {
        this.id = userId;
    }

    public User(Long userId, String firstname, String lastname, String emailaddress, String password, String gender, Date inserteddate, Date updateddate) {
        this.id = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailaddress = emailaddress;
        this.password = password;
        this.gender = gender;
        this.inserteddate = inserteddate;
        this.updateddate = updateddate;
    }
   
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getHashpassword() {
//		return hashpassword;
//	}

//	public void setHashpassword(String hashpassword) {
//		this.hashpassword = PasswordHashing.encodePassword(hashpassword);
//	}

	public Long getId() {
		return id;
	}

	public void setUserrole(Set<Role> userrole) {
		this.userrole = userrole;
	}

	public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
    public List<RegisteredCourse> getRegisteredCourseList() {
        return registeredCourseList;
    }

    public void setRegisteredCourseList(List<RegisteredCourse> registeredCourseList) {
        this.registeredCourseList = registeredCourseList;
    }

    @XmlTransient
    public List<Discussion> getOwnerDiscussions() {
        return ownerDiscussions;
    }

    public void setOwnerDiscussions(List<Discussion> discussionList) {
        this.ownerDiscussions = discussionList;
    }

    @XmlTransient
    public List<Discussion> getMemberDiscussions() {
        return memberDiscussions;
    }

    public void setMemberDiscussions(List<Discussion> discussionList1) {
        this.memberDiscussions = discussionList1;
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
    public List<AnsweredCourseAssignment> getAnsweredCourseAssignmentList() {
        return answeredCourseAssignmentList;
    }

    public void setAnsweredCourseAssignmentList(List<AnsweredCourseAssignment> answeredCourseAssignmentList) {
        this.answeredCourseAssignmentList = answeredCourseAssignmentList;
    }

    @XmlTransient
    public List<CourseAssignment> getCourseAssignmentList() {
        return courseAssignmentList;
    }

    public void setCourseAssignmentList(List<CourseAssignment> courseAssignmentList) {
        this.courseAssignmentList = courseAssignmentList;
    }

    @XmlTransient
    public List<DiscussionFeed> getDiscussionFeedList() {
        return discussionFeedList;
    }

    public void setDiscussionFeedList(List<DiscussionFeed> discussionFeedList) {
        this.discussionFeedList = discussionFeedList;
    }

    public Set<Role> getUserrole() {
        return userrole;
    }

    public void setUserroleId(Set<Role> userroleId) {
        this.userrole = userroleId;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailaddress == null) ? 0 : emailaddress.hashCode());
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
		User other = (User) obj;
		if (emailaddress == null) {
			if (other.emailaddress != null)
				return false;
		} else if (!emailaddress.equals(other.emailaddress))
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
        return "cms.academic.academicapp.bean.User[ userId=" + id + " ]";
    }
    
}
