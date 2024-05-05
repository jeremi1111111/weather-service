package org.weather_service_endpoint;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ErrorInfo missingParameterHandler(HttpServletRequest request, MissingServletRequestParameterException exception) {
        String message = "Missing parameter '" + exception.getParameterName()
                + "' of type '" + exception.getParameterType() + "'";
        return new ErrorInfo(request.getRequestURI(), message);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public ErrorInfo typeMismatchHandler(HttpServletRequest request, TypeMismatchException exception) {
        String message = "Incorrect value '" + exception.getValue()
                + "' for parameter '" + exception.getPropertyName()
                + "' of type '" + exception.getRequiredType() + "'";
        return new ErrorInfo(request.getRequestURI(), message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ErrorInfo illegalArgumentHandler(HttpServletRequest request, IllegalArgumentException exception) {
        return new ErrorInfo(request.getRequestURI(), exception.getMessage());
    }
}
