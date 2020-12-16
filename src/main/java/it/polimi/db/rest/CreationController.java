package it.polimi.db.rest;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.form.QuestionnaireForm;
import it.polimi.db.service.QuestionnaireService;

@Controller
@RequestMapping("/creation")
public class CreationController {
	
	@Autowired
	private QuestionnaireService questionnaireService;
	
	@ModelAttribute("questForm")
	public QuestionnaireForm populateFeatures() {
		return new QuestionnaireForm();
	}
	
	@GetMapping("")
	public String create() {
		return "creation";
	}
	
	//ADD REDIRECTING TO: "YOU SUCCESSFULLY ADDED A QUESTIONNAIRE"
	@RequestMapping()
	public String saveQuestionnaire(@RequestParam("method") String method, final Questionnaire postQues, BindingResult bindingResult,
			Model model, HttpServletRequest req) {

		if (method.equals("Cancel")) {
			return "redirect:/home";
		}
		
		QuestionnaireForm qf = new QuestionnaireForm();
		qf.popuniIzHttpRequesta(req);
		qf.validate();

		if (!qf.isValid()) {
			model.addAttribute("questForm", qf);
			return "creation";
		}
		
		Questionnaire newQuestionnaire = fillFromQF(qf);
		questionnaireService.createQuestionnaire(newQuestionnaire);
		
		model.addAttribute("questform", new QuestionnaireForm());
		return "redirect:/home";
	}

	private Questionnaire fillFromQF(QuestionnaireForm qf) {
		Questionnaire newQuest = new Questionnaire();
		Set<Question> questions = new HashSet<>();
		for(String s : qf.getQuestions()) {
			Question q = new Question();
			q.setQuestion(s);
			
			questions.add(q);
		}
		newQuest.setQuestions(questions);
		return newQuest;
	}
}
