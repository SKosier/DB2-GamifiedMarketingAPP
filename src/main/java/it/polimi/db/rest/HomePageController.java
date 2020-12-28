package it.polimi.db.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import it.polimi.db.entity.Answer;
import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.QuestionService;
import it.polimi.db.service.QuestionnaireService;

@Controller
@RequestMapping("/home")
public class HomePageController {

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private QuestionService questionService;

	@ModelAttribute("questToday")
	public Questionnaire getToday() {
		Optional<Questionnaire> todays = questionnaireService.findByDate(new Date(System.currentTimeMillis()));
		if (todays.isPresent())
			return todays.get();
		return null;
	}

	@ModelAttribute("submittedAnswer")
	public List<Answer> getAnswer(HttpServletRequest req) {
		Integer user = (Integer) req.getSession().getAttribute("currentUserId");
		if (user == null)
			return null;
		List<Answer> submitted = answerService.findByUserAndQuestionnaire(user, getToday().getId());
		if (submitted != null && submitted.size() != 0)
			return submitted;
		return null;
	}

	@GetMapping("")
	public String showPage(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
//		if (session.isNew() || session.getAttribute("currentUser") == null) {
//			return "redirect:/login";
//		}

		Map<Integer, List<String>> reviews = new HashMap<>();
		Map<Integer, String> questions = new HashMap<>();
		Questionnaire q = getToday();
		
		if (q != null) {
			for(Question qstn : q.getQuestions()) {
				questions.put(qstn.getId(), qstn.getQuestion());
			}
		
			List<Answer> answers = answerService.findByQuestionnaire(q.getId());
			if (answers != null && answers.size() != 0) {
				for (Answer a : answers) {
					if (reviews.containsKey(a.getQuestionId())) {
						reviews.get(a.getQuestionId()).add(a.getText());
					} else {
						List<String> list = new ArrayList<>();
						list.add(a.getText());
						reviews.put(a.getQuestionId(), list);
					}
				}
			}
			
			model.addAttribute("questions", questions);
			model.addAttribute("reviews", reviews);
		}
		
		return "home";
	}
}
