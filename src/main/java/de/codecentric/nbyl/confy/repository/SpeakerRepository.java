package de.codecentric.nbyl.confy.repository;

import de.codecentric.nbyl.confy.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

}
