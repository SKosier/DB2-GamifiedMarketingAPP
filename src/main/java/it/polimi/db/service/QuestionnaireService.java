package it.polimi.db.service;

import java.util.List;

import it.polimi.db.entity.Questionnaire;

public interface QuestionnaireService {
	List<Questionnaire> listAll();
	
	Questionnaire fetch(int questionnaireId);
	
	Questionnaire createQuestionnaire(Questionnaire questionnaire);

	Questionnaire updateQuestionnaire(Questionnaire questionnaire);

}
