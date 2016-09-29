package nickebbitt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path = "processor")
public class LanguageController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${language-service.uri}")
    private String languageServiceUri;

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(path = "describe/{languageId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String describe(@PathVariable("languageId") String languageId) {

        log.info("languageServiceUri: {}", languageServiceUri);
        log.info("languageId: {}", languageId);
        log.info("restTemplate: {}", restTemplate);


        final ResponseEntity<Language> language = restTemplate.getForEntity(languageServiceUri + languageId, Language.class);

        if (language.getStatusCode().is4xxClientError()) {
            throw new ClientRequestException();
        }

        if (language.getStatusCode().is5xxServerError()) {
            return "Unable to fulfil request,  there was a problem with the language service";
        }

        if (language.getBody().getName().equals("TrumpScript")) {
            throw new DangerousClientRequestException();
        }

        return String.format("%s is a %s language that was created in %s",
                language.getBody().getName(),
                language.getBody().getType(),
                language.getBody().getYearCreated());

    }

}
