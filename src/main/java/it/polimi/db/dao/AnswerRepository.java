package it.polimi.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polimi.db.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	List<Answer> findByUserId(Integer user_id);
	
	List<Answer> findByQuestionnaireId(Integer questionnaireId);
	
	List<Answer> findByUserIdAndQuestionnaireId(Integer userId, Integer questionnaireId);
	
	List<Answer> findByQuestionIdAndQuestionnaireId(Integer questionId, Integer questionnaireId);
	
	Optional<Answer> findByUserIdAndQuestionnaireIdAndQuestionId(Integer user_id, Integer questionnaire_id, Integer question_id);
	
}
