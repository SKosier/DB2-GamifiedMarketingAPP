package it.polimi.db.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polimi.db.dao.QuestionnaireRepository;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.exception.EntityMissingException;

@Service
public class QuestionnaireServiceJPA implements QuestionnaireService {

	@Autowired
	private QuestionnaireRepository questionnaireRepo;
	
	@Override
	public List<Questionnaire> listAll() {
		return questionnaireRepo.findAll();
	}

	@Override
	public Questionnaire fetch(int questionnaireId) {
		return questionnaireRepo.getOne(questionnaireId);
	}

	//DODATI PROVJERE!!!
	@Override
	public Questionnaire createQuestionnaire(Questionnaire questionnaire) {
		return questionnaireRepo.save(questionnaire);
	}

	@Override
	public Questionnaire updateQuestionnaire(Questionnaire questionnaire) {
	    Integer questionnaireId = questionnaire.getId();
	    if(!questionnaireRepo.existsById(questionnaireId))
	    	throw new EntityMissingException("Questionnaire doesn't exist!");
	    return questionnaireRepo.saveAndFlush(questionnaire);
	}

	@Override
	public Optional<Questionnaire> findByDate(Date date) {
		return questionnaireRepo.findByDate(date);
	}
}
