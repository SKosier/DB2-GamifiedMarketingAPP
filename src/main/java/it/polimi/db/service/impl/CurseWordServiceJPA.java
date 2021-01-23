package it.polimi.db.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polimi.db.dao.CurseWordRepository;
import it.polimi.db.entity.CurseWord;
import it.polimi.db.service.CurseWordService;

@Service
public class CurseWordServiceJPA implements CurseWordService {

	@Autowired
	private CurseWordRepository cwRepo;
	
	@Override
	public List<String> findAllWords() {
		return cwRepo.findAllWords();
	}

	@Override
	public Optional<CurseWord> findByWord(String curseWord) {
		return cwRepo.findByWord(curseWord);
	}

	@Override
	public CurseWord createCurseWord(String curseWord) {
		return cwRepo.save(new CurseWord(curseWord));
	}

}
