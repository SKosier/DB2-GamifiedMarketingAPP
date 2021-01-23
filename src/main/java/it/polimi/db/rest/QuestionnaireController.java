package it.polimi.db.rest;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
import it.polimi.db.entity.Questionnaire;
import it.polimi.db.entity.Statistic;
import it.polimi.db.entity.User;
import it.polimi.db.entity.UserType;
import it.polimi.db.form.AnswersForm;
import it.polimi.db.service.AnswerService;
import it.polimi.db.service.QuestionnaireService;
import it.polimi.db.service.StatisticService;
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

	@Autowired
	private StatisticService statisticService;
	
	private AnswersForm temp;
	
	@ModelAttribute("ansform") 
	public AnswersForm populateFeatures(HttpServletRequest req, Model model) {
		return new AnswersForm(getToday());
	}
	
	@ModelAttribute("questToday")
	public Questionnaire getToday() {
		Optional<Questionnaire> todays = questionnaireService.findByDate(new Date(System.currentTimeMillis()));
		if (todays.isPresent()) return todays.get();
		return null;
	}

	@GetMapping("")
	public String create(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.isNew() || session.getAttribute("currentUser") == null) {
			return "redirect:/login";
		}
		
		Questionnaire q = getToday();
		Integer user = (Integer) req.getSession().getAttribute("currentUserId");
		if (q == null || user == null)
			return "redirect:/home";
		
		List<Answer> answers = answerService.findByUserAndQuestionnaire(user, q.getId());
		if(answers != null && answers.size() != 0) {
			return "redirect:/home"; //return to page - you already submitted it
		}
		
		return "questionnaire";
	}
	
	@RequestMapping("")
	public String prev(@RequestParam("method") String method, final Answer postAnswer,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {
		HttpSession session = req.getSession();
		if (session.isNew() || session.getAttribute("currentUser") == null) {
			return "redirect:/login";
		}

		if (method.equals("Previous")) {
			model.addAttribute("ansform", temp);
			return "questionnaire";
		}

		AnswersForm af = new AnswersForm(getToday());
		af.popuniIzHttpRequesta(req);
		
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
		
		HttpSession session = req.getSession();
		if (session .isNew() || session.getAttribute("currentUser") == null) {
			return "redirect:/login";
		}

		Integer userId = (Integer) req.getSession().getAttribute("currentUserId");
		Questionnaire q = getToday();
		
		if (method.equals("Cancel")) {
			Statistic stat = new Statistic();
			stat.setQuestionnaireId(q.getId());
			stat.setUser_id(userId);
			stat.setCanceled(true);
			
			statisticService.createStatistic(stat);
			temp = null;
			model.addAttribute("ansform", new AnswersForm(q));
			return "redirect:/home";
		}

		temp.validate();
		
		Optional<User> currentUser = userService.findById(userId);
		if(!temp.isValid() && currentUser.isPresent()) {
			if(temp.containsError("ban")) {
				User user = currentUser.get();
				user.setUserPrivilege(UserType.BANNED);
				userService.updateUser(user);
				
				session.invalidate();
				model.addAttribute("error", temp.getError("ban").get(0));
				return "error";
			}
			
		} else {
			//Toma doda
			User user = currentUser.get();
			int newPoints = user.getPoints();
			
			for(Integer id : temp.getAnswers().keySet()) {
				Answer ans = new Answer();
				ans.setText(temp.getAnswer(id));
				ans.setQuestionId(id);
				ans.setQuestionnaireId(q.getId());
				ans.setUserId(userId);
				answerService.createAnswer(ans);
				
				//Toma doda
				newPoints++;
			}
			
			String age = req.getParameter("age");
			String sex = req.getParameter("sex");
			String expLevel = req.getParameter("exper");
			
			Optional<Statistic> stats = statisticService.findByUserAndQuestionnaire(userId, q.getId());
			Statistic stat;
			
			if(stats.isPresent()) {
				stat = stats.get();
			
			} else {
				stat = new Statistic();
			}

			stat.setCanceled(false);
			stat.setAge(age);
			stat.setExpertiseLevel(expLevel);
			stat.setSex(sex);
			stat.setQuestionnaireId(q.getId());
			stat.setUser_id(userId);
			statisticService.updateStatistic(stat);

			//Toma doda
			if(age!=null)		newPoints+=2;
			if(sex!=null)		newPoints+=2;
			if(expLevel!=null)	newPoints+=2;
		
			//Toma doda
			user.setPoints(newPoints);
			q.addParticipant(user);
			questionnaireService.updateQuestionnaire(q);
		}
		
		model.addAttribute("msg", "Thank you for filling out questionnaire of the day!");
		return "uploadstatus";
	}
}
