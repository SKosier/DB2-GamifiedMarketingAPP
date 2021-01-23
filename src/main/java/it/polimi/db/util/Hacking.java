package it.polimi.db.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.polimi.db.service.CurseWordService;

public class Hacking {
	@Autowired
	CurseWordService cwService;
	
	public List<String> allWords; 
	
	public Hacking() {
		allWords = cwService.findAllWords();
	}
	
	public List<String> getAll() {
		return allWords;
	}
}
