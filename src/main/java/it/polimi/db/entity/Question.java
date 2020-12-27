package it.polimi.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE }, mappedBy = "questions")
	private Set<Questionnaire> questionnaires = new HashSet<>(); // u kojima ih mozemo naci

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

	public Set<Questionnaire> getQuestionnaires() {
		return questionnaires;
	}

	public void addQuestionnaire(Questionnaire questionnaires) {
		this.questionnaires.add(questionnaires);
	}

	public void setQuestionnaires(Set<Questionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}
	
	public String toString() {
		return question;
	}
}
