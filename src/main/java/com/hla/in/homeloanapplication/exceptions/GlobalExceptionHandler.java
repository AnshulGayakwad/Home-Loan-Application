package com.hla.in.homeloanapplication.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request){
				
		Map<String, String> map = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String msg = error.getDefaultMessage();
			
			map.put(fieldName, msg);
		});
		
		return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	    @ExceptionHandler(AdminApprovalException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(AdminApprovalException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(CustomerNotFoundException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(CustomerNotFoundException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }


	    @ExceptionHandler(FinanceVerificationException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(FinanceVerificationException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(InvalidCredentialException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(InvalidCredentialException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(InvalidLoanApplicationException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(InvalidLoanApplicationException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }

	    @ExceptionHandler(InvalidLoanAgreementException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(InvalidLoanAgreementException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }


	    @ExceptionHandler(LandVerificationException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(LandVerificationException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }


	    @ExceptionHandler(CouldNotBeAddedException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(CouldNotBeAddedException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }


	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<String> handleEmployeeNotFoundException(ResourceNotFoundException ex){
	        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND);
	    }

}
