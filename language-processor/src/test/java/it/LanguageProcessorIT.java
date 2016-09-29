package it;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import nickebbitt.LanguageProcessorApplication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
        properties="language-service.uri=http://localhost:8083/languages/"
)
public class LanguageProcessorIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8083);

    @Test
    public void shouldReturnMessageDescribingKotlin() {

        stubFor(
                get(urlEqualTo("/languages/1"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody("{\n" +
                                        "  \"id\": \"9\",\n" +
                                        "  \"name\": \"Kotlin\",\n" +
                                        "  \"type\": \"Object Oriented\",\n" +
                                        "  \"yearCreated\": 2011\n" +
                                        "}")
                )
        );

        final ResponseEntity<String> response
                = restTemplate.getForEntity("/processor/describe/1", String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo("Kotlin is a Object Oriented language that was created in 2011");

    }

    @Test
    public void shouldReturnIAmATeapotWhenTrumpScriptRequested() {

        stubFor(
                get(urlEqualTo("/languages/12"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody("{\n" +
                                                "  \"id\": \"12\",\n" +
                                                "  \"name\": \"TrumpScript\",\n" +
                                                "  \"type\": \"Some crazy scripting language!\",\n" +
                                                "  \"yearCreated\": 2016\n" +
                                                "}")
                        )
        );

        final ResponseEntity<String> response
                = restTemplate.getForEntity("/processor/describe/12", String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.I_AM_A_TEAPOT.value());

    }

}
