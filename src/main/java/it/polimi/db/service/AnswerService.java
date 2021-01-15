package it.polimi.db.service;

import java.util.List;
import java.util.Optional;

import it.polimi.db.entity.Answer;

public interface AnswerService {
	List<Answer> findByUser(Integer user_id);
	
	List<Answer> findByQuestionnaire(Integer questionnaire_id);

	List<Answer> findByUserAndQuestionnaire(Integer userId, Integer questionnaireId);
	
	List<Answer> findByQuestionAndQuestionnaire(Integer questionId, Integer questionnaireId);
	
	Optional<Answer> findByUserAndQuestionnaireAndQuestion(Integer user_id, Integer questionnaire_id, Integer question_id);

	Answer createAnswer(Answer answer);
	
	Answer updateAnswer(Answer answer);
	
	Answer removeAnswer(Answer answer);
}