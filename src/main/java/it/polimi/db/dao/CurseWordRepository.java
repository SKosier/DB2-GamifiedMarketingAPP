package it.polimi.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polimi.db.entity.CurseWord;

public interface CurseWordRepository extends JpaRepository<CurseWord, Integer> {
	List<CurseWord> findAll();
	
	Optional<String> findByWord(String curseWord);
}
