package it.polimi.db.form;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import it.polimi.db.entity.CWSingleton;
import it.polimi.db.entity.Question;
import it.polimi.db.entity.Questionnaire;

public class AnswersForm {
	private Questionnaire qstnr;
	private Map<String, List<String>> errors = new HashMap<>();
	private Map<Integer, String> answers = new HashMap<>();

	public AnswersForm(Questionnaire qstnr) {
		this.qstnr = qstnr;
	}

	public void popuniIzHttpRequesta(HttpServletRequest req) {
		Set<Question> questions = qstnr.getQuestions();
		for (Question q : questions) {
			if (req.getParameter("answer-" + q.getId()) != null
					&& !req.getParameter("answer-" + q.getId()).trim().isEmpty()) {
				String answer = req.getParameter("answer-" + q.getId());
				if(answer == null) answer = "";
				answers.put(q.getId(), answer);
			}
		}
	}
	
	public String getAnswer(Integer id) {
		if(id == null) return "";
		if(answers.containsKey(id)) return answers.get(id);
		return "";
	}

	// dodati provjere blacklist rijeci kasnije
	public void validate() {
		errors.clear();
		
		boolean shouldBan = false;
		for(Integer key : answers.keySet()) {
			String answer = answers.get(key);
			if(answer.isEmpty()) {
				setError("answer", "Answer should not be null");
			}
			
			for(String word : answer.split(" ")) {
				System.out.println(word);
				if(CWSingleton.bannedWords.contains(word)) {
					setError("ban", "You wrote innapropriate answer! You are banned!");
					shouldBan = true;
					break;
				}
			}
			
			if(shouldBan) break;
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

	public Map<String, List<String>> getErrors() {
		return errors;
	}

	public Map<Integer, String> getAnswers() {
		return answers;
	}

}
