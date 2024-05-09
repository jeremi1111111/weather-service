package org.weather_service_endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.*;

import java.net.UnknownHostException;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo missingParameterHandler(HttpServletRequest request, MissingServletRequestParameterException exception) {
        String message = "Missing parameter '" + exception.getParameterName()
                + "' of type '" + exception.getParameterType() + "'.";
        return new ErrorInfo(request.getRequestURI(), message);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo typeMismatchHandler(HttpServletRequest request, TypeMismatchException exception) {
        String message = "Incorrect value '" + exception.getValue()
                + "' for parameter '" + exception.getPropertyName()
                + "' of type '" + exception.getRequiredType() + "'.";
        return new ErrorInfo(request.getRequestURI(), message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo illegalArgumentHandler(HttpServletRequest request, IllegalArgumentException exception) {
        return new ErrorInfo(request.getRequestURI(), exception.getMessage());
    }

    @ExceptionHandler({JsonProcessingException.class, JsonMappingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo jsonProcessingException(HttpServletRequest request, JsonProcessingException exception) {
        return new ErrorInfo(request.getRequestURI(), "Error processing response from third party service.");
    }

    @ExceptionHandler(UnknownHostException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorInfo unknownHostHandler(HttpServletRequest request, UnknownHostException exception) {
        return new ErrorInfo(request.getRequestURI(), "Third party service is unreachable.");
    }

    @ExceptionHandler(HttpServerErrorException.GatewayTimeout.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorInfo gatewayTimeoutHandler(HttpServletRequest request, HttpServerErrorException exception) {
        return new ErrorInfo(request.getRequestURI(), "Third party service timed out.");
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public ErrorInfo notFoundHandler(HttpServletRequest request, HttpClientErrorException exception) {
        return new ErrorInfo(request.getRequestURI(), "Third party service responded with 'Not Found'. Contact site administrator.");
    }

    @ExceptionHandler(RestClientResponseException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorInfo restClientResponseHandler(HttpServletRequest request, RestClientResponseException exception) {
        return new ErrorInfo(request.getRequestURI(), "Third party service rejected the request. Contact site administrator.");
    }

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ResponseBody
    public ErrorInfo resourceAccessHandler(HttpServletRequest request, ResourceAccessException exception) {
        return new ErrorInfo(request.getRequestURI(), "Resource access error. Try again.");
    }
}
