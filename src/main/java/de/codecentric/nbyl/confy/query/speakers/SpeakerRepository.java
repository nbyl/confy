package de.codecentric.nbyl.confy.query.speakers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeakerRepository extends CrudRepository<SpeakerQueryObject, String > {
}
