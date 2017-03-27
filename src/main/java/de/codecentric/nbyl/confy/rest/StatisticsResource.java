package de.codecentric.nbyl.confy.rest;

import de.codecentric.nbyl.confy.query.statistics.YearlyTalkStats;
import de.codecentric.nbyl.confy.query.statistics.YearlyTalkStatsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/statistics")
@RestController
public class StatisticsResource {

    private final YearlyTalkStatsRepository repository;

    public StatisticsResource(YearlyTalkStatsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{year}")
    public List<YearlyTalkStats> getStatistics(@PathVariable("year") Integer year) {
        return this.repository.findByYearOrderByTalkCountDesc(year);
    }
}
