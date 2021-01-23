package it.polimi.db.service;

import java.util.List;
import java.util.Optional;

import it.polimi.db.entity.CurseWord;

public interface CurseWordService {
	List<CurseWord> findAll();
	
	Optional<String> findByWord(String curseWord);
	
	CurseWord createCurseWord(String curseWord);
}
