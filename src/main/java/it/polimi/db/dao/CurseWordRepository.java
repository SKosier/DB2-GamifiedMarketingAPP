package it.polimi.db.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.polimi.db.entity.CurseWord;

public interface CurseWordRepository extends JpaRepository<CurseWord, Integer> {
	@Query(value = "SELECT curse_words.word FROM curse_words", nativeQuery = true)
	List<String> findAllWords();
	
	Optional<CurseWord> findByWord(String curseWord);
}
