package it.polimi.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.polimi.db.entity.Answer;
import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.Statistic;
import it.polimi.db.entity.User;
import it.polimi.db.entity.UserType;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.CurseWordService;
import it.polimi.db.service.QuestionService;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.StatisticService;
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
	
	@Autowired
	AnswerService answerService;

	@Autowired
	StatisticService statService;
	
	@Autowired
	CurseWordService cwService;
	
	@Value("${db2.test.user.names}")
	private String testNames;

	@EventListener
	public void appReady(ApplicationReadyEvent event) {
		String[] names = testNames.split(",");
		List<User> users = new ArrayList<>();
		
		for (int i = 0; i < names.length; i++) {
			users.add(makeUser(names[i]));
		}
		users.stream().forEach(u -> userService.createUser(u));

		Questionnaire q1 = new Questionnaire();
		Questionnaire q2 = new Questionnaire();
		Questionnaire q3 = new Questionnaire();

		questionnaireService.createQuestionnaire(q1);
		questionnaireService.createQuestionnaire(q2);
		questionnaireService.createQuestionnaire(q3);
		
		String[] list = { "What do you think about this product?", "Would you buy this product?",
				"Would you recomend this product to your friends?",
				"Do you think this product is pricey?", 
				"What would you suggest us regarding the product?"};
		
		for (int i = 0; i < list.length; i++) {
			Question qu = new Question();
			qu.setQuestion(list[i]);
			qService.createQuestion(qu);

			if (i % 2 == 0) {
				q1.addQuestion(qu);		
			
			} else {
				q2.addQuestion(qu);
			}
		}


		Date today = new Date(System.currentTimeMillis());
		q1.setDate(today);
		q1.setProductName("Birra Moretti");
		q1.setPhoto("moretti.jpg");
		
		q2.setDate(new Date(System.currentTimeMillis() - 1000*60*60*24));
		q2.setProductName("Ichnusa");
		q2.setPhoto("Birra Ichnusa.jpg");
		
		q3.setDate(new Date(System.currentTimeMillis() + 3*1000*60*60*24));
		q3.setProductName("Birra Peroni");
		q3.setPhoto("peroni.png");
		
		createAnswers(q2, users);
		users.stream().forEach(u -> q2.addParticipant(u));
		
		questionnaireService.updateQuestionnaire(q1);
		questionnaireService.updateQuestionnaire(q2);
		questionnaireService.updateQuestionnaire(q3);
		
		List<String> bannedWords = null;
		try {
			bannedWords = Files.readAllLines(Paths.get("src/main/resources/cursewords.txt"));
			for(String banned : bannedWords) {
				if(banned.isBlank()) continue;
				cwService.createCurseWord(banned);
			}
			
		} catch (IOException e) {
			System.err.println("Couldn't locate file!");
		}
	}

	private void createAnswers(Questionnaire q2, List<User> users) {
		for(User u : users) {
			for(Question q : q2.getQuestions()) {
				Answer a = new Answer();
				a.setQuestionId(q.getId());
				a.setQuestionnaireId(q2.getId());
				a.setUserId(u.getId());
				a.setText("answer by: " + u.getUsername() + " for question " + q.getQuestion());
				answerService.createAnswer(a);
			}
			Statistic stat = new Statistic();
			stat.setAge("YOUNG ADULT");
			stat.setCanceled(false);
			stat.setExpertiseLevel("MEDIUM");
			stat.setQuestionnaireId(q2.getId());
			stat.setSex("O");
			stat.setUser_id(u.getId());
			statService.createStatistic(stat);
		}
	}

	private User makeUser(String name) {
		User user = new User();
		user.setUsername(name);
		user.setEmail(name + "@gmail.com");
		user.setPasswordHash(Util.calculateHash(name + "123"));
		user.setLastLogIn(new Date());
		user.setUserPrivilege(UserType.ADMINISTRATOR);
		
		if(name.equals("Federico")) user.setUserPrivilege(UserType.BANNED);
		
		return user;
	}
}