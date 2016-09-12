package nickebbitt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by NEbbitt on 11/09/2016.
 */
@RestController
public class LanguageController {

    private final List<Language> languages;

    public LanguageController() {
        languages = new ArrayList<>();
        languages.add(new Language("Java", "ObjectOriented", 1996));
        languages.add(new Language("Scala", "ObjectOriented / Functional", 2010));
        languages.add(new Language("Closure", "Functional", 2005));
        languages.add(new Language("Oracle", "Database", 1980));
    }

    @RequestMapping(path = "language", method = RequestMethod.GET)
    @ResponseBody
    public Language getLanguage() {
        return languages.get(ThreadLocalRandom.current().nextInt(0, languages.size()));
    }

}
