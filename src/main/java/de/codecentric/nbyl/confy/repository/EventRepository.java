package de.codecentric.nbyl.confy.repository;

import de.codecentric.nbyl.confy.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Event entity.
 */
@SuppressWarnings("unused")
public interface EventRepository extends JpaRepository<Event,Long> {

}
