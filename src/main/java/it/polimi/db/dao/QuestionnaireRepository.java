package it.polimi.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polimi.db.entity.Questionnaire;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer> {

}
