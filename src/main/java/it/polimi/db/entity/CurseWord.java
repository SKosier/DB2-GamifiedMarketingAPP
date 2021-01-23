package it.polimi.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="curse_words")
public class CurseWord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String word;

	// **********************************************
	
	public CurseWord(String word) {
		this.word = word;
	}
	
	public Integer getId() {
		return id;
	}

	public String getWord() {
		return word;
	}
	
	public String toString() {
		return word;
	}
}
