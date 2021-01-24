package it.polimi.db.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.polimi.db.entity.Questionnaire;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer> {
	Optional<Questionnaire> findByDate(Date date);
	
	@Query(value="SELECT questionnaire.* FROM questionnaire where date < curdate()", nativeQuery = true)
	List<Questionnaire> findPreviousQuestionnaires();
}
