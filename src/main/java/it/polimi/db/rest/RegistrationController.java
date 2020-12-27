package it.polimi.db.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.polimi.db.entity.User;
import it.polimi.db.form.RegistrationForm;
import it.polimi.db.service.UserService;
import it.polimi.db.util.Util;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	private UserService userService;

	@ModelAttribute("regform")
	public RegistrationForm populateFeatures() {
		return new RegistrationForm();
	}

	@GetMapping("")
	public String registration() {
		return "registration";
	}

	@RequestMapping()
	public String saveUser(@RequestParam("method") String method, final User user, final BindingResult bindingResult,
			Model model, HttpServletRequest req) {
		if (method.equals("Cancel"))
			return "redirect:/login";

		if (bindingResult.hasErrors()) {
			return "registration";
		}
		
		RegistrationForm rf = new RegistrationForm();
		rf.popuniIzHttpRequesta(req);
		rf.validate();

		checkifValidWithDB(rf);
		if (!rf.isValid()) {
			model.addAttribute("regform", rf);
			return "registration";
		}

		User newUser = new User();
		rf.popuniURecord(newUser);
		newUser.setLastLogIn(new Date());
		newUser.setPasswordHash(Util.calculateHash(newUser.getPasswordHash()));
//		newUser.setUserPrivilege(UserType.COMPETITOR);
		
		this.userService.createUser(newUser);
		Util.setSessionAttributes(req, newUser);
		model.addAttribute("regform", new RegistrationForm());
		return "redirect:/home";
	}

	public boolean checkifValidWithDB(RegistrationForm rf) {
		boolean flag = true;
		if(userService.countByUsername(rf.getUsername()) > 0) {
			rf.setError("username", "Username is already taken!");
			flag = false;
		}
		if(userService.countByEmail(rf.getEmail()) > 0) {
			rf.setError("email", "Email is already in use!");
			flag = false;
		}
		
		return flag;
	}
}
