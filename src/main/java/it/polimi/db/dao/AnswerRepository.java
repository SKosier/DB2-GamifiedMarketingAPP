package it.polimi.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polimi.db.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	List<Answer> findByUser(Integer user_id);
	
	List<Answer> findByQuestionnaire(Integer questionnaire_id);
	
	Optional<Answer> findByUserAndQuestionnaireAndQuestion(Integer user_id, Integer questionnaire_id, Integer question_id);
	
}
