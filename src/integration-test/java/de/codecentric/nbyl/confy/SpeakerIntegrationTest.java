package de.codecentric.nbyl.confy;

import org.junit.Test;

import java.net.URISyntaxException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SpeakerIntegrationTest extends IntegrationTestBase {

    public SpeakerIntegrationTest() throws URISyntaxException {
    }

    @Test
    public void createAndLoadSpeaker() throws URISyntaxException {
//        SpeakerDTO speaker = new SpeakerDTO();
//        speaker.setFirstName("Demo");
//        speaker.setSurname("User");
//
//        String speakersLink = traverson().follow(rel("speakers")).asLink().getHref();
//
//        URI speakerLocation = restTemplate().postForLocation(speakersLink, new HttpEntity<>(speaker));
//        assertThat(speakerLocation.toString(), startsWith(speakersLink));
//
//        SpeakerDTO savedSpeaker = restTemplate().getForObject(speakerLocation, SpeakerDTO.class);
//        assertThat(savedSpeaker.getFirstName(), is("Demo"));
//        assertThat(savedSpeaker.getSurname(), is("User"));
    }


}
