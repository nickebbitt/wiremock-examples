package it;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import nickebbitt.LanguageProcessorApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = LanguageProcessorApplication.class,
        properties= "language-service.uri=" + LanguageProcessorIT.LANGUAGE_SERVICE_URL
)
public class LanguageProcessorIT {

    private static final int LANGUAGE_SERVICE_PORT = 8083;

    static final String LANGUAGE_SERVICE_URL
            = "http://localhost:" + LANGUAGE_SERVICE_PORT + "/languages/";
    private static final String PROCESSOR_DESCRIBE_URL_PATH = "/processor/describe/";


    @Autowired
    private TestRestTemplate restTemplate;

    @Rule
    public WireMockRule wireMockRule
            = new WireMockRule(LANGUAGE_SERVICE_PORT); // TODO console notifier

    @Test
    public void shouldReturnMessageDescribingKotlin() {

        stubFor(
            get(urlEqualTo("/languages/9"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\n" +
                            "  \"id\": \"9\",\n" +
                            "  \"name\": \"Kotlin\",\n" +
                            "  \"type\": \"Object Oriented\",\n" +
                            "  \"yearCreated\": 2011\n" +
                            "}")
                    .withFixedDelay(5000)
            )
        );

        final ResponseEntity<String> response
                = restTemplate.getForEntity(PROCESSOR_DESCRIBE_URL_PATH + "9", String.class);

        assertThat(response.getStatusCode().value())
                .isEqualTo(HttpStatus.OK.value());

        assertThat(response.getBody())
                .isEqualTo("Kotlin is a Object Oriented language that was created in 2011");

    }

    @Test
    public void shouldReturn404NotFoundForUnknownLanguage() {

        final String unknownLanguage = "/languages/99";

        stubFor(
            get(urlEqualTo(unknownLanguage))
            .willReturn(
                aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())
            )
        );

        final ResponseEntity<String> response
                = restTemplate.getForEntity(unknownLanguage, String.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void verifyThatRequestMadeToLanguageService() {

        final ResponseEntity<String> response
                = restTemplate.getForEntity(PROCESSOR_DESCRIBE_URL_PATH + "1", String.class);

        verify(
                getRequestedFor(urlEqualTo("/languages/1"))
        );

    }

    @Test
    public void confirmThatDonaldTrumpIsATeapot() {

        stubFor(
            get(urlEqualTo("/languages/12"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.OK.value())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody("{\n" +
                            "  \"id\": 12,\n" +
                            "  \"name\": \"TrumpScript\",\n" +
                            "  \"type\": \"Some crazy scripting language!\",\n" +
                            "  \"yearCreated\": 2016\n" +
                            "}"
                    )
            )
        );

        final ResponseEntity<String> response
                = restTemplate.getForEntity(PROCESSOR_DESCRIBE_URL_PATH + "12", String.class);

        assertThat(response.getStatusCodeValue())
                .isEqualTo(HttpStatus.I_AM_A_TEAPOT.value());

    }

}
