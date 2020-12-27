package it.polimi.db.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "questionnaire")
public class Questionnaire {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "questionnaire_id")
	private Integer id;
	
	@Column(name="date")
	@Temporal(TemporalType.DATE)
	private Date date; //date for questionnaire

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "questionnaire_participant", joinColumns = @JoinColumn(name = "questionnaire_id"), inverseJoinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "user_id"))
	private Set<User> participants = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "questionnaire_questions", joinColumns = @JoinColumn(name = "questionnaire_id"), inverseJoinColumns = @JoinColumn(name = "q_id", referencedColumnName = "question_id"))
	private Set<Question> questions = new HashSet<>();

	@Column(name="name")
	private String productName;
	
	@Column(name="photo")
	private String photo;

	// **********************************************
	public Set<User> getParticipants() {
		return participants;
	}

	public void addParticipant(User p) {
		this.participants.add(p);
	}

	public void setParticipants(Set<User> participants) {
		this.participants = participants;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void addQuestion(Question question) {
		this.questions.add(question);
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Integer getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
