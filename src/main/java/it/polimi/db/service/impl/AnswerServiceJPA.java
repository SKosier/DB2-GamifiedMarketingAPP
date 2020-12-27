package it.polimi.db.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polimi.db.dao.AnswerRepository;
import it.polimi.db.entity.Answer;
import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.User;
import it.polimi.db.service.AnswerService;

@Service
public class AnswerServiceJPA implements AnswerService {

	@Autowired
	private AnswerRepository answerRepo;

	@Override
	public List<Answer> findByUser(User user) {
		return answerRepo.findByUser(user);
	}

	@Override
	public List<Answer> findByQuestionnaire(Questionnaire questionnaire) {
		return answerRepo.findByQuestionnaire(questionnaire);
	}

	@Override
	public Optional<Answer> findByUserAndQuestionnaireAndQuestion(User user, Questionnaire questionnaire,
			Question question) {
		return answerRepo.findByUserAndQuestionnaireAndQuestion(user, questionnaire, question);
	}

	@Override
	public Answer createAnswer(Answer answer) {
		return answerRepo.save(answer);
	}

	@Override
	public Answer updateAnswer(Answer answer) {
		return answerRepo.saveAndFlush(answer);
	}
}
