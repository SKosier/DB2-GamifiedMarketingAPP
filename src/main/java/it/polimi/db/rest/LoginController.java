package it.polimi.db.rest;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.polimi.db.entity.User;
import it.polimi.db.form.LoginForm;
import it.polimi.db.service.UserService;
import it.polimi.db.util.Util;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@ModelAttribute("logform")
	public LoginForm populateFeatures() {
		return new LoginForm();
	}

	@GetMapping("")
	public String login() {
		return "login";
	}

	@RequestMapping()
	public String saveUser(@RequestParam("method") String method, final User postUser, final BindingResult bindingResult,
			Model model, HttpServletRequest req) {

		if (method.equals("Cancel"))
			return "redirect:/login";

		LoginForm lf = new LoginForm();
		lf.popuniIzHttpRequesta(req);
		lf.validate();

		if (!lf.isValid()) {
			model.addAttribute("logform", lf);
			return "login";
		}
		
		Optional<User> opUser = userService.findByUsernameAndPasswordHash(lf.getUsername(), Util.calculateHash(lf.getPassword()));
		if(!opUser.isPresent()) {
			lf = new LoginForm();
			lf.setError("password", "Username or password is incorrect!");
			model.addAttribute("logform", lf);
			return "login";
		}
		
		User registeredUser = opUser.get();
		
		// check if user is banned
//		if(registeredUser.getUserPrivilege() == UserType.BANNED) {
//			lf = new LoginForm();
//			lf.setError("password", "You are not allowed to enter this site!");
//			model.addAttribute("logform", lf);
//			return "login";
//		}
		
		Util.setSessionAttributes(req, registeredUser);
		model.addAttribute("logform", new LoginForm());
		return "redirect:/home";
	}
}
