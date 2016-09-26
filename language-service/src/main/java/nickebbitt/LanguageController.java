package nickebbitt;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(path = "languageService")
public class LanguageController {

    private final List<Language> languages;

    public LanguageController() {
        languages = new ArrayList<>();
        languages.add(new Language("Bash", "Scripting", 1989));
        languages.add(new Language("C", "Imperative", 1972));
        languages.add(new Language("Closure", "Functional", 2007));
        languages.add(new Language("COBOL", "Imperative", 1959));
        languages.add(new Language("Erlang", "Functional", 1986));
        languages.add(new Language("Groovy", "Object Oriented", 2003));
        languages.add(new Language("Haskell", "Functional", 1990));
        languages.add(new Language("Java", "Object Oriented", 1995));
        languages.add(new Language("Kotlin", "Object Oriented", 2011));
        languages.add(new Language("Scala", "Object Oriented / Functional", 2004));
        languages.add(new Language("Ruby", "Object Oriented", 1995));
    }

    @RequestMapping(path = "random", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public Language getRandomLanguage() {
        return languages.get(ThreadLocalRandom.current().nextInt(0, languages.size()));
    }

}
