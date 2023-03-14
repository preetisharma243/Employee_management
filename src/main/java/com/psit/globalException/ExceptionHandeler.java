package com.psit.globalException;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.psit.entity.ErrorEntity;
import com.psit.exception.DuplicateDataException;
import com.psit.exception.InvalidFileException;

/*A convenience annotation that is itself annotated with @ControllerAdviceand @ResponseBody. 

Types that carry this annotation are treated as controller advice where @ExceptionHandler methods assume @ResponseBody semantics by default. 

NOTE: @RestControllerAdvice is processed if an appropriate HandlerMapping-HandlerAdapter pair is configured such as the RequestMappingHandlerMapping-RequestMappingHandlerAdapter pairwhich are the default in the MVC Java config and the MVC namespace.
*/
@RestControllerAdvice
public class ExceptionHandeler{
	
	@ExceptionHandler(DuplicateDataException.class)
	public ResponseEntity<?> handleDuplicateDataException(DuplicateDataException de){
		ErrorEntity errorEntity=new ErrorEntity(de.getMessage(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(),LocalDateTime.now());
		return ResponseEntity.badRequest().body(errorEntity);
	}
	
	@ExceptionHandler(InvalidFileException.class)
	public ResponseEntity<?> handleInvalidFileException(InvalidFileException ie){
		ErrorEntity errorEntity=new ErrorEntity(ie.getMessage(),HttpStatus.NOT_ACCEPTABLE,HttpStatus.NOT_ACCEPTABLE.value(), LocalDateTime.now());
		return new ResponseEntity<ErrorEntity>(errorEntity, HttpStatus.NOT_ACCEPTABLE);
	}
	

}
