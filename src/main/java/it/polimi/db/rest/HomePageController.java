package it.polimi.db.rest;

import java.sql.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import it.polimi.db.entity.Questionnaire;
import it.polimi.db.service.QuestionnaireService;

@Controller
@RequestMapping("/home")
public class HomePageController {

	@Autowired
	private QuestionnaireService questionnaireService;

	@ModelAttribute("questToday")
	public Questionnaire getToday() {
		Optional<Questionnaire> todays = questionnaireService.findByDate(new Date(System.currentTimeMillis())); //find if there exist q for today
		if(todays.isPresent()) return todays.get();
		return null;
	}
	
	@GetMapping("")
	public String showPage(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
//		if (session.isNew() || session.getAttribute("currentUser") == null) {
//			return "redirect:/login";
//		}

		return "home";
	}	
}
