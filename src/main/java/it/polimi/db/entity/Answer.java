package it.polimi.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "answer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "answer_id", nullable = false)
	private Integer id;

	@Column(name = "user", nullable = false)
	private int userId;

	@Column(name = "questionnaire", nullable = false)
	private int questionnaireId;

	@Column(name = "question", nullable = false)
	private int questionId;

	@Column(name = "text")
	private String text;

	// **********************************************

	public int getUserId() {
		return userId;
	}

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public String getText() {
		return text;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public void setText(String text) {
		this.text = text;
	}
}
