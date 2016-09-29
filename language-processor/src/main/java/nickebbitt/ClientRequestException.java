package nickebbitt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="There was a problem with the client request")
class ClientRequestException extends RuntimeException {


}
