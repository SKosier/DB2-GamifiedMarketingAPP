package it.polimi.db.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polimi.db.dao.StatisticRepository;
import it.polimi.db.entity.Statistic;
import it.polimi.db.service.StatisticService;

@Service
public class StatisticServiceJPA implements StatisticService {
		     
	@Autowired
	private StatisticRepository statRepo;
	
	@Override
	public Optional<Statistic> findByUserAndQuestionnaire(Integer user_id, Integer questionnaire_id) {
		return statRepo.findByUserIdAndQuestionnaireId(user_id, questionnaire_id);
	}

	@Override
	public Statistic createStatistic(Statistic stat) {
		return statRepo.save(stat);
	}

	@Override
	public Statistic updateStatistic(Statistic stat) {
		return statRepo.saveAndFlush(stat);
	}
}
