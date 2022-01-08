package learn.capstone.clubrunner.controllers;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

/*
controller "try"
controller "catch"
try {
} catch (DataAccessException ex) {
  // TODO
} catch (Exception ex) {
  // TODO
}
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    // DataAccessException is the super class of many Spring database exceptions
    // including BadSqlGrammarException.
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(DataAccessException ex) {

        // TODO Log the exception?

        return new ResponseEntity<>(
                new ErrorResponse("We can't show you the details, but something went wrong in our database. Sorry :(", LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // IllegalArgumentException is the super class for many Java exceptions
    // including all formatting (number, date) exceptions.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {

        // TODO Log the exception?

        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage(), LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This is our absolute last resort. Yuck.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) throws Exception {

//        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null)
//            throw ex;

        // TODO Log the exception?

        if (ex instanceof HttpMessageNotReadableException || ex instanceof HttpMediaTypeNotSupportedException) {
            throw ex; // return
        }

        return new ResponseEntity<>(
                new ErrorResponse("Something went wrong on our end. Your request failed. :(", LocalDateTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException exception) {
//        return new ResponseEntity<>(
//                new ErrorResponse("Bad request body."),
//                HttpStatus.BAD_REQUEST);
//    }
}
