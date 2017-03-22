package de.codecentric.nbyl.confy.query.talks;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TalkQueryObjectRepository extends CrudRepository<TalkQueryObject, String> {

    void deleteBySpeakerId(String speakerId);
}
