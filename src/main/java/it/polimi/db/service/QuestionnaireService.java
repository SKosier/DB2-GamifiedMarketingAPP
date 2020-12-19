package it.polimi.db.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import it.polimi.db.entity.Questionnaire;

public interface QuestionnaireService {
	List<Questionnaire> listAll();
	
	Questionnaire fetch(int questionnaireId);
	
	Optional<Questionnaire> findByDate(Date date);
	
	Questionnaire createQuestionnaire(Questionnaire questionnaire);

	Questionnaire updateQuestionnaire(Questionnaire questionnaire);

}
