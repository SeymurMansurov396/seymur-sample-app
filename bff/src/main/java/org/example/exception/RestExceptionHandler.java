package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.example.dto.response.ApiError;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex, uri);
        return buildResponseEntity(apiError, ex);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex, uri);
        return buildResponseEntity(apiError, ex);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, uri);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError,ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String error = "Malformed JSON request";
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error, ex, uri);
        return buildResponseEntity(apiError, ex);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String error = "Error writing JSON output";
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, error, ex, uri);
        return buildResponseEntity(apiError, ex);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, uri);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError, ex);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, uri);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError, ex);
    }


    @ExceptionHandler(JokeNotFoundException.class)
    protected ResponseEntity<Object> handleJokeNotFoundException(JokeNotFoundException ex, WebRequest request) {

        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, uri);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError, ex);
    }


    @ExceptionHandler(GeneralNoContentException.class)
    protected ResponseEntity<Object> handleGeneralNoContentException(GeneralNoContentException ex, WebRequest request) {

        String uri = request.getDescription(false);
        ApiError apiError = new ApiError(HttpStatus.NO_CONTENT, uri);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError, ex);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleRemainException(Exception ex, HttpServletRequest request) {
        String stacktrace = ExceptionUtils.getStackTrace(ex);
        request.setAttribute("exStackTrace", stacktrace);
        String uri = request.getRequestURI();
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, uri);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError, ex);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, Throwable t) {
        logger.error(apiError.toString(), t);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
