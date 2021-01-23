package it.polimi.db.rest;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.polimi.db.entity.Answer;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.Statistic;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.StatisticService;

@Controller
@RequestMapping("/deletion")
public class DeletionController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private StatisticService statService;
	
	@GetMapping("")
	public String showPage(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.isNew() || session.getAttribute("currentUser") == null) {
			return "redirect:/login";
		}
		
		if((boolean) session.getAttribute("isAdministrator") == false) {
			model.addAttribute("error", "You are not administrator!");
			req.getSession().setAttribute("error", "You are not admin");
			return "error";
		}
		
		return "deletion";
	}
	
	@ModelAttribute("yesterdaysQuestionnaire")
	public Questionnaire getYesterdaysQuestionnaire() {
		Optional<Questionnaire> yesterdays = questionnaireService.findByDate(new Date(System.currentTimeMillis()-24*60*60*1000));
		if (yesterdays.isPresent()) return yesterdays.get();
		return null;
	}
	
	@RequestMapping("")
	public String prev(@RequestParam("method") String method, final Answer postAnswer,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {

		if (method.equals("Delete")) {
			Optional<Questionnaire> yesterdays = questionnaireService.findByDate(new Date(System.currentTimeMillis()-24*60*60*1000));
			if (yesterdays.isPresent()) {
				Questionnaire questionnaire = yesterdays.get();
				questionnaireService.removeQuestionnaire(questionnaire);
				List<Answer> answers = answerService.findByQuestionnaire(questionnaire.getId());
				 
				for(Answer a : answers) {
					answerService.removeAnswer(a);
				}
				 
				List<Statistic> stats = statService.listAll();
				for(Statistic s : stats) {
					if(s.getQuestionnaireId()==questionnaire.getId()) statService.removeStatistic(s);
				}
				 
				model.addAttribute("msg", "Successfully deleted questionnaire!");
			} else {
				model.addAttribute("msg", "Deletion of the questionnaire unsuccessful!");
			}
		}
		
		return "uploadstatus";
	}
	
}
