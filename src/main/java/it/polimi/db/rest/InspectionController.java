package it.polimi.db.rest;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.polimi.db.entity.Answer;
import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.QuestionService;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.StatisticService;
import it.polimi.db.service.UserService;

@Controller
@RequestMapping("/inspection")
public class InspectionController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@Autowired
	private StatisticService statisticService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("")
	public String showPage(Model model, HttpServletRequest req) {
		return "inspection";
	}
	
	@ModelAttribute("questionnaireList")
	public List<Questionnaire> getQuestionnaireList() {
		return questionnaireService.listAll();
	}
	
	@RequestMapping("/submit")
	public String chooseQuestionnaire(@RequestParam("method") String method, final Answer postAnswer,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {
	
		if(method.equals("Choose")) {
			String questionnaireDate = req.getParameter("questionnaireDate");
			if(questionnaireDate != null) {
				Optional<Questionnaire> qTemp = questionnaireService.findByDate(Date.valueOf(questionnaireDate));
		
				if(qTemp.isPresent()) {
					Questionnaire q = qTemp.get();
					model.addAttribute("questionnaireChoice", q);
					model.addAttribute("participantsSubmitted",
							q.getParticipants()
							 .stream()
							 .filter(participant->
							 statisticService.findByUserAndQuestionnaire(participant.getId(), q.getId()).isPresent()
							 && !statisticService.findByUserAndQuestionnaire(participant.getId(), q.getId()).get().isCanceled()).collect(Collectors.toSet()));
				
					model.addAttribute("participantsCancelled",
							userService.listAll()
									   .stream()
									   .filter(participant->
									    statisticService.findByUserAndQuestionnaire(participant.getId(), q.getId()).isPresent()
										&& statisticService.findByUserAndQuestionnaire(participant.getId(), q.getId()).get().isCanceled()).collect(Collectors.toSet()));
					
					Map<Question,List<String>> qAndAs = new HashMap<Question,List<String>>();
					for(Question question : q.getQuestions()) {
						List<Answer> answers = answerService.findByQuestionAndQuestionnaire(question.getId(), q.getId());
						List<String> userAndA = new ArrayList<>();
						for(Answer ans : answers) userAndA.add("("+userService.fetch(ans.getUserId()).getUsername()+") " + ans.getText());
						qAndAs.put(question, userAndA);
					}
					model.addAttribute("qAndAs", qAndAs);
				}
			}
			return "/inspection";
		}
		
		return "redirect:/home";
	}
	
}
