package it.polimi.db.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomePageController {

	@GetMapping("")
	public String showPage(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.isNew() || session.getAttribute("currentUser") == null) {
			return "redirect:/login";
		}
		
		return "home";
	}
	
}
