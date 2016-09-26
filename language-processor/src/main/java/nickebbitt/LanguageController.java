package nickebbitt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "languageProcessor")
public class LanguageController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${language-service.uri}")
    private String languageServiceUri;

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(path = "describe", method = RequestMethod.GET)
    public String describe() {

        log.info("languageServiceUri: {}", languageServiceUri);

        final ResponseEntity<Language> language = restTemplate.getForEntity(languageServiceUri + "/random", Language.class);

        if (language.getBody().getYearCreated() < 2000) {
            return "This is old school...";
        } else {
            return "Down with the kids!";
        }

    }

}
