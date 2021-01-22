package it.polimi.db.service;

import java.util.List;
import java.util.Optional;

import it.polimi.db.entity.Statistic;

public interface StatisticService {
	Statistic createStatistic(Statistic stat);
	Statistic updateStatistic(Statistic stat);
	Optional<Statistic> findByUserAndQuestionnaire(Integer user_id, Integer questionnaire_id);
	List<Statistic> listAll();
	Statistic removeStatistic(Statistic stat);
}
