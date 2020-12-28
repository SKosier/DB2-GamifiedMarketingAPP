package it.polimi.db.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.polimi.db.entity.Statistic;

public interface StatisticRepository extends JpaRepository<Statistic, Integer>{
	Optional<Statistic> findByUserIdAndQuestionnaireId(Integer user_id, Integer questionnaire_id);
}
