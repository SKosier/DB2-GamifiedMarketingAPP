package it.polimi.db.dao;

import java.sql.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polimi.db.entity.Questionnaire;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer> {
	Optional<Questionnaire> findByDate(Date date);
}
