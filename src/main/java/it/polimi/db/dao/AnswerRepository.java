package it.polimi.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polimi.db.entity.Answer;
import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.User;

public interface AnswerRepository extends JpaRepository<Answer, Integer>{
	List<Answer> findByUser(User user);
	List<Answer> findByQuestionnaire(Questionnaire questionnaire);
	Optional<Answer> findByUserAndQuestionnaireAndQuestion(User user, Questionnaire questionnaire, Question question);
}
