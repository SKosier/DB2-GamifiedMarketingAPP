package it.polimi.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.User;
import it.polimi.db.service.QuestionService;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.UserService;
import it.polimi.db.util.Util;

@Component
public class Initializer {
	@Autowired
	UserService userService;

	@Autowired
	QuestionService qService;

	@Autowired
	QuestionnaireService questionnaireService;

	@Value("${db2.test.user.names}")
	private String testNames;

	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		String[] names = testNames.split(",");
		User user = null;
		for (int i = 0; i < names.length; i++) {
			user = makeUser(names[i]);
			userService.createUser(user);
		}

		Questionnaire q1 = new Questionnaire();
		questionnaireService.createQuestionnaire(q1);

		Questionnaire q2 = new Questionnaire();
		questionnaireService.createQuestionnaire(q2);

		String[] list = { "What do you think about this product?", "Would you buy this product?",
				"Would you recomend this product to your friends?",
				"Do you think this product is pricey?" }; 
		for (int i = 0; i < list.length; i++) {
			Question qu = new Question();
			qu.setQuestion(list[i]);
			qService.createQuestion(qu);

			if (i < 2) {
				q1.addQuestion(qu);
				q2.addQuestion(qu);
			}
			
			if (i == 3) {
				q2.addQuestion(qu);
			}
		}

		questionnaireService.updateQuestionnaire(q1);
		questionnaireService.updateQuestionnaire(q2);
	}

	private User makeUser(String name) {
		User user = new User();
		user.setUsername(name);
		user.setEmail(name + "@gmail.com");
		user.setPasswordHash(Util.calculateHash(name + "123"));
		return user;
	}
}