package rk181.java_survey_app_backend.config.exceptions;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ResponseStatusException.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(ResponseStatusException ex, HttpServletRequest request) {
 
        ProblemDetail exBody = ex.getBody();

        Map<String, String> errorMessage = new HashMap<>();
        errorMessage.put("dateTime", LocalDate.now().toString());
        errorMessage.put("title", exBody.getTitle());
        errorMessage.put("status", Integer.toString(exBody.getStatus()));
        errorMessage.put("error", exBody.getDetail());
        errorMessage.put("instance", request.getRequestURI());

        
        return new ResponseEntity<Object>(errorMessage, ex.getStatusCode());
    }


    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    // This method is used to handle exceptions that are thrown by the application
    // We dont override the handleExceptionInternal method, its exactly the same as the one in the ResponseEntityExceptionHandler class
    // We use it for reference
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

		if (request instanceof ServletWebRequest servletWebRequest) {
			HttpServletResponse response = servletWebRequest.getResponse();
			if (response != null && response.isCommitted()) {
				if (logger.isWarnEnabled()) {
					logger.warn("Response already committed. Ignoring: " + ex);
				}
				return null;
			}
		}

        if (body == null && ex instanceof ErrorResponse errorResponse) {
			body = errorResponse.updateAndGetBody(super.getMessageSource(), LocaleContextHolder.getLocale());
            
		}

		if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR) && body == null) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}

		return createResponseEntity(null, headers, statusCode, request);
	}
}