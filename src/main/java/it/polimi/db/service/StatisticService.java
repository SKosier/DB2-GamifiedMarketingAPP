package it.polimi.db.service;

import java.util.Optional;

import it.polimi.db.entity.Statistic;

public interface StatisticService {
	Statistic createStatistic(Statistic stat);
	Optional<Statistic> findByUserAndQuestionnaire(Integer user_id, Integer questionnaire_id);
}
