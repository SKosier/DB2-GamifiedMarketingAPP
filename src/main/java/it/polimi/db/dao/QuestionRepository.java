package it.polimi.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polimi.db.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
}
