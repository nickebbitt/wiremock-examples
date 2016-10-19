package nickebbitt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Language not found.")
class LanguageNotFoundException extends RuntimeException {


}
