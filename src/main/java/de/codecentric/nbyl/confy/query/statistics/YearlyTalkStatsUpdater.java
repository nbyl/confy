package de.codecentric.nbyl.confy.query.statistics;

import de.codecentric.nbyl.confy.api.events.talks.TalkCreatedEvent;
import de.codecentric.nbyl.confy.domain.speakers.Speaker;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class YearlyTalkStatsUpdater {

    private final YearlyTalkStatsRepository repository;

    private final Repository<Speaker> speakerRepository;

    public YearlyTalkStatsUpdater(YearlyTalkStatsRepository repository, Repository<Speaker> speakerRepository) {
        this.repository = repository;
        this.speakerRepository = speakerRepository;
    }

    @EventHandler
    public void on(TalkCreatedEvent event) {
        YearlyTalkStats stats = this.repository.findOne(new YearlyTalkStatsId(event.getDateHeld().getYear(), event.getSpeakerId()));
        if (stats == null) {
            stats = new YearlyTalkStats(
                    event.getDateHeld().getYear(),
                    event.getSpeakerId(),
                    event.getSpeakerSurname(),
                    event.getSpeakerFirstName()
            );
        }

        stats.increaseTalkCount();
        this.repository.save(stats);
    }
}
