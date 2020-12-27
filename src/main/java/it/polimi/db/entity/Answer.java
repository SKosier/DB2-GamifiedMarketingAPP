package it.polimi.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class Answer {
	@OneToOne
	@JoinColumn(name="user", referencedColumnName="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="from_questionnaire", referencedColumnName="questionnaire_id")
	private Questionnaire questionnaire;
	
	@OneToOne
	@JoinColumn(name="question_id", referencedColumnName="question_id")
	private Question question;

	@Column(name="text")
	private String text;
	
	// **********************************************
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public Question getQuestion() {
		return question;
	}
	
}
