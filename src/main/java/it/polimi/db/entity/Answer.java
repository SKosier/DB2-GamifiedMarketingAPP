package it.polimi.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "answer_id", nullable=false)
	private Integer id;
	
	@Column(name = "u_id", nullable=false)
	private int user;
	
	@Column(name = "quest_id", nullable=false)
	private int questionnaire;
	
	@Column(name = "qstn_id", nullable=false)
	private int question;

	@Column(name="text")
	private String text;

	// **********************************************
	
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(int questionnaire) {
		this.questionnaire = questionnaire;
	}

	public int getQuestion() {
		return question;
	}

	public void setQuestion(int question) {
		this.question = question;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
