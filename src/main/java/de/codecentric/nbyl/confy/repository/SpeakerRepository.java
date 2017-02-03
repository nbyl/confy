package de.codecentric.nbyl.confy.repository;

import de.codecentric.nbyl.confy.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Speaker entity.
 */
@SuppressWarnings("unused")
public interface SpeakerRepository extends JpaRepository<Speaker,Long> {

}
