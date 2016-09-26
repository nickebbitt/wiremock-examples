package it;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import nickebbitt.LanguageProcessorApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = LanguageProcessorApplication.class,
        properties="language-service.uri=http://localhost:9000/languageService"
)
public class LanguageProcessorIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9000);

    @Test
    public void shouldReturnHttpStatus200() {

        final ResponseEntity<String> response
                = restTemplate.getForEntity("/languageProcessor/describe", String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);


    }

    @Test
    public void shouldReturnDownWithTheKidsWhenYearCreatedGreaterThanOrEqualTo2000() {

        stubFor(
                get(urlEqualTo("/languageService/random"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Context-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("{\n" +
                                        "  \"name\": \"Kotlin\",\n" +
                                        "  \"type\": \"Object Oriented\",\n" +
                                        "  \"yearCreated\": 2011\n" +
                                        "}")
                )
        );

        final ResponseEntity<String> response
                = restTemplate.getForEntity("/languageProcessor/describe", String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Down with the kids!");


    }

}
