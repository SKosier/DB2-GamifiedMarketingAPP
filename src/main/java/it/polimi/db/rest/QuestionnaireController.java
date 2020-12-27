package it.polimi.db.rest;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

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
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.form.AnswersForm;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.UserService;

@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {

	@Autowired
	private UserService userService;

	@Autowired
	private QuestionnaireService questionnaireService;

	@Autowired
	private AnswerService answerService;

	private AnswersForm temp;
	
	@ModelAttribute("ansform") 
	public AnswersForm populateFeatures(HttpServletRequest req, Model model) {
		return new AnswersForm(getToday());
	}
	
	@ModelAttribute("questToday")
	public Questionnaire getToday() {
		Optional<Questionnaire> todays = questionnaireService.findByDate(new Date(System.currentTimeMillis()));
		if (todays.isPresent())
			return todays.get();
		return null;
	}

	@GetMapping("")
	public String create(HttpServletRequest req) {
		if (getToday() == null)
			return "redirect:/home";
		return "questionnaire";
	}
	
	@RequestMapping("")
	public String prev(@RequestParam("method") String method, final Answer postAnswer,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {
//		if (session.isNew() || session.getAttribute("currentUser") == null) {
//			return "redirect:/login";
//		}

		if (method.equals("Previous")) {
			model.addAttribute("ansform", temp);
			return "questionnaire";
		}

		AnswersForm af = new AnswersForm(getToday());
		af.popuniIzHttpRequesta(req);
		
		Map<Integer, String> map = af.getAnswers();
		for(Integer i : map.keySet()) System.out.println(map.get(i));
		
		model.addAttribute("ansform", af);
		temp = af;
		return "stats";
	}

	// ADD ERROR <NEKIBROJ> FOR DENIED ACCES
	@GetMapping("/statistic/submit")
	public String statsSubmit() {
		return "redirect:/home";
	}

	@RequestMapping("/submit")
	public String saveAnswer(@RequestParam("method") String method, final Answer postAnswer,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {
//		if (session.isNew() || session.getAttribute("currentUser") == null) {
//			return "redirect:/login";
//		}

		if (method.equals("Cancel")) {
			model.addAttribute("ansform", new AnswersForm(getToday()));
			temp = null;
			return "redirect:/home";
		}

		System.out.println(temp.getAnswers().size());
		
		model.addAttribute("msg", "Thank you for filling out questionnaire of the day!");
		return "uploadstatus";
	}
}
