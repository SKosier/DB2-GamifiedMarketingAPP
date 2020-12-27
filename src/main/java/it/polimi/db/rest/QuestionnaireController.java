package it.polimi.db.rest;

import java.io.IOException;

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
import it.polimi.db.form.AnswersForm;
import it.polimi.db.form.QuestionnaireForm;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.QuestionnaireService;

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {
	
	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private AnswerService answerService;
	
	@ModelAttribute("answerForm")
	public AnswersForm populateFeatures() {
		return new AnswersForm();
	}

	@GetMapping("")
	public String create() {
		return "questionnaire";
	}

	@RequestMapping()
	public String saveAnswers(@RequestParam("method") String method,
			final Answer postAnswer,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {
//		if (session.isNew() || session.getAttribute("currentUser") == null) {
//			return "redirect:/login";
//		}
		
		model.addAttribute("msg", "Thank you for filling out questionnaire of the day!");
		return "uploadstatus";
	}
}
