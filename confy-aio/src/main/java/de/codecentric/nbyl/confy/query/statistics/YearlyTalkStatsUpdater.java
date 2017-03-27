package de.codecentric.nbyl.confy.query.statistics;

import de.codecentric.nbyl.confy.api.events.talks.TalkCreatedEvent;
import de.codecentric.nbyl.confy.api.events.talks.TalkDeletedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class YearlyTalkStatsUpdater {

    private final YearlyTalkStatsRepository repository;

    public YearlyTalkStatsUpdater(YearlyTalkStatsRepository repository) {
        this.repository = repository;
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

    @EventHandler
    public void on(TalkDeletedEvent event) {
//        this.repository.findOne(new YearlyTalkStatsId(event.get))
    }
}
