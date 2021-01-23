package it.polimi.db.service;

import java.util.List;
import java.util.Optional;

import it.polimi.db.entity.CurseWord;

public interface CurseWordService {
	List<String> findAllWords();
	
	Optional<CurseWord> findByWord(String curseWord);
	
	CurseWord createCurseWord(String curseWord);
}
