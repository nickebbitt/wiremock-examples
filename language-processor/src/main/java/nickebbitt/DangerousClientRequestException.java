package nickebbitt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Avoid Trump at all costs, you teapot!")
class DangerousClientRequestException extends RuntimeException {


}
