package it.polimi.db.rest;

import java.sql.Date;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.User;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.UserService;

@Controller
@RequestMapping("/leaderboard")
public class LeaderboardController {

	@Autowired
	private QuestionnaireService questionnaireService;
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("questToday")
	public Questionnaire getToday() {
		Optional<Questionnaire> todays = questionnaireService.findByDate(new Date(System.currentTimeMillis()));
		if (todays.isPresent())
			return todays.get();
		return null;
	}
	
	@GetMapping("")
	public String showPage(Model model, HttpServletRequest req) {
		
		Map<Integer, Integer> points = new HashMap<>();
		List<User> users = userService.listAll();
		
		List<User> sortedUsers = users.stream()
		            .sorted(Comparator.comparingInt(User::getPoints).reversed())
		            .collect(Collectors.toList());
		int i = 1;
		for(User user : sortedUsers) {
			points.put(i, (user.getPoints()!=null)?user.getPoints():0);
			i++;
		}
		
		model.addAttribute("points", points);
		model.addAttribute("users", sortedUsers);
		return "leaderboard";
	}
	

	@Scheduled(cron = "0 0 0 * * *")
	void resetPoints() {
		List<User> users = userService.listAll();
		for(User user : users) {
			user.setPoints(0);
			userService.updateUser(user);
		}
	}
}
