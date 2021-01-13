package it.polimi.db.rest;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import it.polimi.db.entity.Statistic;
import it.polimi.db.entity.User;
import it.polimi.db.form.AnswersForm;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.QuestionService;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.StatisticService;
import it.polimi.db.service.UserService;

@Controller
@RequestMapping("/inspection")
public class InspectionController {
	
	int questionnaireId=-1;

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

	@ModelAttribute("questionnaireChoice")
	public Questionnaire getChosen() {
		if(questionnaireId==-1)
			return null;
		return questionnaireService.fetch(questionnaireId);
	}
	
	@ModelAttribute("participantsSubmitted")
	public Set<User> getSubmitted() {
		if(questionnaireId==-1)
			return null;
		return getChosen()
				.getParticipants()
				.stream()
				.filter(participant->
				statisticService.findByUserAndQuestionnaire(participant.getId(), questionnaireId).isPresent()
				&& !statisticService.findByUserAndQuestionnaire(participant.getId(), questionnaireId).get().isCanceled()).collect(Collectors.toSet());
	}

	@ModelAttribute("participantsCancelled")
	public Set<User> getCancelled() {
		if(questionnaireId==-1)
			return null;
		
		return userService
				.listAll()
				.stream()
				.filter(participant->
				statisticService.findByUserAndQuestionnaire(participant.getId(), questionnaireId).isPresent()
				&& statisticService.findByUserAndQuestionnaire(participant.getId(), questionnaireId).get().isCanceled()).collect(Collectors.toSet());
	}
	
	@ModelAttribute("responses")
	public Map<User, List<Answer>> getResponses() {
		if(questionnaireId==-1)
			return null;
		Map<User,List<Answer>> responses = new HashMap<User,List<Answer>>();
		getChosen().getParticipants().forEach(user->responses.put(user, answerService.findByUserAndQuestionnaire(user.getId(), getChosen().getId())));
		return responses;
	}
	
	@RequestMapping("/submit")
	public String chooseQuestionnaire(@RequestParam("method") String method, final Answer postAnswer,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {
		if(method.equals("Choose")) {
			String questionnaireDate = req.getParameter("questionnaireDate");
			
			if(questionnaireDate != null) {
				Optional<Questionnaire> qTemp = questionnaireService.findByDate(Date.valueOf(questionnaireDate));
				if(qTemp.isPresent())
					this.questionnaireId = qTemp.get().getId();
			}
			
			return "redirect:/inspection";
		}
		return "redirect:/home";
	}
	
	@RequestMapping("")
	public String goHome(@RequestParam("method") String method, final Answer postAnswer,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {
		if(method.equals("Home")) {
			questionnaireId = -1;
			return "redirect:/home";
		}
		return "redirect:/home";
	} 
}
