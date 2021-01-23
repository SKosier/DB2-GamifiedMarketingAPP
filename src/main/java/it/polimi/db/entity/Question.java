package it.polimi.db.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {
	@Id
	@GeneratedValue
	@Column(name = "question_id")
	private Integer id;

	@Column(nullable = false)
	private String question;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "questionnaire")
	private Questionnaire questionnaire; // u kojem se nalazi

	// **********************************************

	public Integer getId() {
		return id;
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public String toString() {
		return question;
	}
}
