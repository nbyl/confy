package de.codecentric.nbyl.confy;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class IntegrationTestBase {

    protected final Traverson traverson;
    protected final RestTemplate restTemplate;

    public IntegrationTestBase() throws URISyntaxException {
        this.restTemplate = new RestTemplate();
        this.traverson = new Traverson(getBaseUrl(), MediaTypes.HAL_JSON);
    }

    public RestTemplate restTemplate() {
        return restTemplate;
    }

    public Traverson traverson() {
        return this.traverson;
    }

    private static URI getBaseUrl() throws URISyntaxException {
        return new URI(System.getProperty("integrationTestBaseUrl", "http://localhost:8080/api/"));
    }
}
