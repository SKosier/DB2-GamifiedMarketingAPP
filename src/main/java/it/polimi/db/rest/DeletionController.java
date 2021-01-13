package it.polimi.db.rest;

import java.io.IOException;
import java.sql.Date;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.User;
import it.polimi.db.form.AnswersForm;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.UserService;

@Controller
@RequestMapping("/deletion")
public class DeletionController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@Autowired
	private AnswerService answerService;
	
	@GetMapping("")
	public String showPage(Model model, HttpServletRequest req) {
		return "deletion";
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
				 model.addAttribute("msg", "Thank you for deleting the questionnaire!");
			}
			else {
				model.addAttribute("msg", "Deletion of the questionnaire unsuccessful!");
			}
		}
		return "uploadstatus";
	}
	
}
