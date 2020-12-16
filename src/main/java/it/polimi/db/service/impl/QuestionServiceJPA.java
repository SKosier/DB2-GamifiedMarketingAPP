package it.polimi.db.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polimi.db.dao.QuestionRepository;
import it.polimi.db.entity.Question;
import it.polimi.db.service.QuestionService;

@Service
public class QuestionServiceJPA implements QuestionService {

	@Autowired
	private QuestionRepository qRepo;

	// DODATI PROVJERE!!!
	@Override
	public Question createQuestion(String q) {
		Question question = new Question();
		question.setQuestion(q);
		return qRepo.save(question);
	}

	@Override
	public Optional<Question> findById(int questionId) {
		// TODO Auto-generated method stub
		return null;
	}

	// DODATI PROVJERE!!!
	@Override
	public Question createQuestion(Question q) {
		return qRepo.save(q);
	}

}
