package de.codecentric.nbyl.confy;

import de.codecentric.nbyl.confy.domain.Speaker;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.hateoas.client.Hop.rel;

public class SpeakerIntegrationTest extends IntegrationTestBase {

    public SpeakerIntegrationTest() throws URISyntaxException {
    }

    @Test
    public void createAndLoadSpeaker() throws URISyntaxException {
        Speaker speaker = new Speaker();
        speaker.setFirstName("Demo");
        speaker.setSurname("User");

        String speakersLink = traverson().follow(rel("speakers")).asLink().getHref();

        URI speakerLocation = restTemplate().postForLocation(speakersLink, new HttpEntity<>(speaker));
        assertThat(speakerLocation.toString(), startsWith(speakersLink));

        Speaker savedSpeaker = restTemplate().getForObject(speakerLocation, Speaker.class);
        assertThat(savedSpeaker.getFirstName(), is("Demo"));
        assertThat(savedSpeaker.getSurname(), is("User"));
    }


}
