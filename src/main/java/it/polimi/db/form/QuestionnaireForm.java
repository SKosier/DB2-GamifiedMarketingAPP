package it.polimi.db.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class QuestionnaireForm {

	private Map<String, List<String>> errors = new HashMap<>();
	private List<String> questions = new ArrayList<>();

	public void popuniIzHttpRequesta(HttpServletRequest req) {
		int i = 1;
		while (i <= 50) {
			if (req.getParameter("input-" + i) != null && !req.getParameter("input-" + i).trim().isEmpty()) {
				questions.add(req.getParameter("input-" + i).trim());
				System.out.println(req.getParameter("input-" + i).trim());
		
			} else if (req.getParameter("input-" + i) != null && req.getParameter("input-" + i).trim().isEmpty()) {
			}
			i++;
		}
	}

	// dodati provjere blacklist rijeci kasnije
	public void validate() {
		errors.clear();

		if(questions.size() == 0) {
			setError("questions", "At least one question is needed!");
		}
	}
	
	public boolean containsError(String name) {
		return errors.containsKey(name);
	}
	
	public List<String> getError(String name) {
		return errors.get(name);
	}

	public void setError(String key, String content) {
		if (errors.containsKey(key)) {
			errors.get(key).add(content);
		} else {
			List<String> list = new LinkedList<>();
			list.add(content);
			errors.put(key, list);
		}
	}

	public boolean isValid() {
		return errors.isEmpty();
	}

	public List<String> getQuestions() {
		return questions;
	}
}
