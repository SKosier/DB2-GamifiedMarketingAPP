package it.polimi.db.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

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
	public static String uploadDirectory = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploads";

	@Autowired
	private QuestionnaireService questionnaireService;

	@ModelAttribute("questForm")
	public QuestionnaireForm populateFeatures() {
		return new QuestionnaireForm();
	}

	@GetMapping("")
	public String create(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		if (session.isNew() || session.getAttribute("currentUser") == null) {
			return "redirect:/login";
		}
		
		if((boolean) session.getAttribute("isAdministrator") == false) {
			model.addAttribute("error", "You are not administrator!");
			req.getSession().setAttribute("error", "You are not admin");
			return "error";
		}
		
		return "creation";
	}

	@RequestMapping()
	public String saveQuestionnaire(@RequestParam("method") String method,
			final Questionnaire postQues,
			BindingResult bindingResult, Model model, HttpServletRequest req) throws IOException {

		//ACT-checks hehe
		if((boolean) req.getSession().getAttribute("isAdministrator") == false) {
			model.addAttribute("error", "You are not administrator!");
			req.getSession().setAttribute("error", "You are not admin");
			return "error";
		}
		
		if (method.equals("Cancel")) {
			return "redirect:/home";
		}

		QuestionnaireForm qf = new QuestionnaireForm();
		qf.popuniIzHttpRequesta(req);
		qf.validate();

		Optional<Questionnaire> existing = questionnaireService.findByDate(qf.getDate());
		if (existing.isPresent()) {
			qf.setError("date", "Questionnaire for that date already exists");
		}

		Part filePart = null;
		try {
			filePart = req.getPart("photo");
			InputStream fileInputStream = filePart.getInputStream();
		    File fileToSave = new File(uploadDirectory + "//" + filePart.getSubmittedFileName());
		  
		    try {
				Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
		    } catch (IOException e) {
				qf.setError("photo", "Error occured while uploading a photo!");
				e.printStackTrace();
			}
		} catch (IOException | ServletException e1) {
			qf.setError("photo", "Error occured while uploading a photo!");
			e1.printStackTrace();
		}
	  		
		if (!qf.isValid()) {
			model.addAttribute("questForm", qf);
			model.addAttribute("questions", qf.getQuestions());
			return "creation";
		}

		Questionnaire newQuestionnaire = fillFromQF(qf);
		newQuestionnaire.setPhoto(filePart.getSubmittedFileName());
		questionnaireService.createQuestionnaire(newQuestionnaire);

		model.addAttribute("questform", new QuestionnaireForm());
		model.addAttribute("msg", "Successfully uploaded new questionnaire ");// with product photo:
																				// +fileNames.toString());
		return "uploadstatus";
	}

	private Questionnaire fillFromQF(QuestionnaireForm qf) {
		Questionnaire newQuest = new Questionnaire();
		for (String text : qf.getQuestions()) {
			Question question = new Question();
			question.setQuestion(text);
			newQuest.addQuestion(question);
		}
		
		newQuest.setDate(qf.getDate());
		newQuest.setProductName(qf.getName());
		return newQuest;
	}
}
