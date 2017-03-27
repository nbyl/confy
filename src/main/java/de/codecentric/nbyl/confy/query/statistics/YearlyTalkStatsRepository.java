package de.codecentric.nbyl.confy.query.statistics;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearlyTalkStatsRepository extends CrudRepository<YearlyTalkStats, YearlyTalkStatsId> {

    List<YearlyTalkStats> findByYear(Integer year);
}
