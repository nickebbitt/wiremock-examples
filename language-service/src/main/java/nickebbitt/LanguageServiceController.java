package nickebbitt;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "languages")
public class LanguageServiceController {

    private final Map<Integer, Language> languages;

    public LanguageServiceController() {
        languages = new HashMap<>();
        languages.put(1, new Language(1, "Bash", "Scripting", 1989));
        languages.put(2, new Language(2, "C", "Imperative", 1972));
        languages.put(3, new Language(3, "Closure", "Functional", 2007));
        languages.put(4, new Language(4, "COBOL", "Imperative", 1959));
        languages.put(5, new Language(5, "Erlang", "Functional", 1986));
        languages.put(6, new Language(6, "Groovy", "Object Oriented", 2003));
        languages.put(7, new Language(7, "Haskell", "Functional", 1990));
        languages.put(8, new Language(8, "Java", "Object Oriented", 1995));
        languages.put(9, new Language(9, "Kotlin", "Object Oriented", 2011));
        languages.put(10, new Language(10, "Scala", "Object Oriented / Functional", 2004));
        languages.put(11, new Language(11, "Ruby", "Object Oriented", 1995));
        languages.put(12, new Language(12, "TrumpScript", "Some crazy scripting language!", 2016));
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Set<Integer> getLanguageIds() {

        return languages.keySet();

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Language getLanguageDetails(@PathVariable("id") int id) {

        return Optional.ofNullable(languages.get(id))
                  .orElseThrow(() -> new LanguageNotFoundException(id));
    }

}
