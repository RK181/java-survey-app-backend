/*package rk181.java_survey_app_backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<RestError> handleAuthenticationException(Exception ex) {
        RestError re = new RestError(HttpStatus.UNAUTHORIZED.value(), "Authentication failed at controller advice");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }

    @ExceptionHandler({ResponseStatusException.class})
    @ResponseBody
    public ResponseEntity<ProblemDetail> handleResponseStatusException(ResponseStatusException ex) {
        //RestError re = new RestError(HttpStatus.UNAUTHORIZED.value(), "Authentication");
       
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getBody());
    }
}*/