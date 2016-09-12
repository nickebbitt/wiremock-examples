package nickebbitt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping(path = "languageProcessor")
public class LanguageController {

    private static final URI LANGUAGE_SERVICE_URI = URI.create(System.getProperty("language-service.uri", "http://localhost:8080/languageService"));

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(path = "describe", method = RequestMethod.GET)
    public String describeLanguage() {
        final ResponseEntity<Language> language = restTemplate.getForEntity(LANGUAGE_SERVICE_URI + "/random", Language.class, Collections.emptyMap());

        if (language.getBody().getYearCreated() < 2000) {
            return "This is old school...";
        } else {
            return "Down with the kids!";
        }

    }

}
