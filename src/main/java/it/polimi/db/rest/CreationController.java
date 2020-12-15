package it.polimi.db.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.polimi.db.entity.Questionnaire;
import it.polimi.db.form.QuestionnaireForm;

@Controller
@RequestMapping("/creation")
public class CreationController {
	
	//@Autowired
	//private QuestionnaireService qService;
	
	@ModelAttribute("questForm")
	public QuestionnaireForm populateFeatures() {
		return new QuestionnaireForm();
	}
	
	@GetMapping("")
	public String create() {
		return "create";
	}
	
	@RequestMapping()
	public String saveQuestionnaire(@RequestParam("method") String method, final BindingResult bindingResult,
			Model model, HttpServletRequest req) {

		if (method.equals("Cancel"))
			return "redirect:/home";

		QuestionnaireForm lf = new QuestionnaireForm();
		lf.popuniIzHttpRequesta(req);
		lf.validate();

		if (!lf.isValid()) {
			model.addAttribute("questform", lf);
			return "create";
		}
		
		Questionnaire quest = fillUpFromFrom(lf);
		
//		Optional<Questionnaire> opQuest = userService.findByUsernameAndPasswordHash(lf.getUsername(), Util.calculateHash(lf.getPassword()));
//		if(!opUser.isPresent()) {
//			lf = new LoginForm();
//			lf.setError("password", "Username or password is incorrect!");
//			model.addAttribute("logform", lf);
//			return "login";
//		}
		
		//qService.createQuestionnaire(newQuest);
		model.addAttribute("questform", new QuestionnaireForm());
		return "redirect:/home";
	}

	private Questionnaire fillUpFromFrom(QuestionnaireForm lf) {
		// TODO Auto-generated method stub
		return null;
	}
}
