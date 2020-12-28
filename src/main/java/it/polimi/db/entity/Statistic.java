package it.polimi.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "statistic")
public class Statistic {
	@Id
	@GeneratedValue
	@Column(name = "statId")
	private Integer statId;
	
	@Column(name = "userId")
	private Integer userId;

	@Column(name = "questionnaireId")
	private Integer questionnaireId;

	@Column(name = "age")
	private String age;

	@Column(name = "sex")
	private String sex;

	@Column(name = "expertiseLevel")
	private String expertiseLevel;

	@Column(name="isCanceled")
	private boolean isCanceled;
	
	// *******************************************************
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getExpertiseLevel() {
		return expertiseLevel;
	}

	public void setExpertiseLevel(String expertiseLevel) {
		this.expertiseLevel = expertiseLevel;
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaire_id) {
		this.questionnaireId = questionnaire_id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUser_id(Integer user_id) {
		this.userId = user_id;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}
}
