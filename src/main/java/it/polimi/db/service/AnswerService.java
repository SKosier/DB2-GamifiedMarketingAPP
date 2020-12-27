package it.polimi.db.service;

import java.util.List;
import java.util.Optional;

import it.polimi.db.entity.Answer;
import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.User;

public interface AnswerService {
	List<Answer> findByUser(User user);
	
	List<Answer> findByQuestionnaire(Questionnaire questionnaire);
	
	Optional<Answer> findByUserAndQuestionnaireAndQuestion(User user, Questionnaire questionnaire, Question question);
	
	Answer createAnswer(Answer answer);
	
	Answer updateAnswer(Answer answer);
}
