package nickebbitt;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;

/**
 * Created by NEbbitt on 11/09/2016.
 */
@RestController
public class LanguageController {

    static final URI LANGUAGE_SERVICE_URI = URI.create(System.getProperty("language-service.uri", "http://localhost:8080"));

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(path = "describe", method = RequestMethod.GET)
    public String describeLanguage() {
        final ResponseEntity<Language> language = restTemplate.getForEntity(LANGUAGE_SERVICE_URI + "/language", Language.class, Collections.emptyMap());

        if (language.getBody().getYearCreated() < 2000) {
            return "This is old school...";
        } else {
            return "Down with the kids!";
        }

    }

}
