package it.polimi.db.service;

import java.util.Optional;

import it.polimi.db.entity.Question;

public interface QuestionService {
	Question createQuestion(String q);
	Question createQuestion(Question q);
	
	Optional<Question> findById(int questionId);
}
