package com.proemsalud.laboratory;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.proemsalud.laboratory.error.ApiErrorResponse;
import com.proemsalud.laboratory.exception.DuplicateResourceException;
import com.proemsalud.laboratory.exception.FileUploadException;
import com.proemsalud.laboratory.exception.InactiveTestException;
import com.proemsalud.laboratory.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex) {

		ApiErrorResponse body = ApiErrorResponse.builder().timestamp(Instant.now().toString()).status(404)
				.error("Not Found").message(ex.getMessage()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiErrorResponse> handleBadRequest(IllegalArgumentException ex) {
		ApiErrorResponse body = ApiErrorResponse.builder().timestamp(Instant.now().toString()).status(400)
				.error("Bad Request").message(ex.getMessage()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ApiErrorResponse> handleDataAccess(DataAccessException ex) {
		ApiErrorResponse body = ApiErrorResponse.builder().timestamp(Instant.now().toString()).status(500)
				.error("Internal Server Error").message("Database error").detail(ex.getMostSpecificCause().getMessage())
				.build();

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

		Map<String, String> fieldErrors = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

		ApiErrorResponse body = ApiErrorResponse.builder().timestamp(Instant.now().toString()).status(400)
				.error("Bad Request").message("Validation failed").errors(fieldErrors).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {

		String detail = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();

		// Heurística: si el mensaje trae "duplicate entry", es un conflicto de unicidad
		boolean duplicate = detail != null && detail.toLowerCase().contains("duplicate entry");

		if (duplicate) {
			String value = detail.replaceAll(".*Duplicate entry '([^']+)'.*", "$1");

			ApiErrorResponse body = ApiErrorResponse.builder().timestamp(Instant.now().toString()).status(409)
					.error("Conflict").message("The value '" + value + "' already exists").detail(detail).build();

			return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
		}

		ApiErrorResponse body = ApiErrorResponse.builder().timestamp(Instant.now().toString()).status(400)
				.error("Bad Request").message("Database constraint violation").detail(detail).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	// works on categori, when name is duplicate or store id and product id are
	// equals "rare that happens".
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiErrorResponse> handleDuplicate(DuplicateResourceException ex) {
		ApiErrorResponse body = ApiErrorResponse.builder().timestamp(Instant.now().toString()).status(409)
				.error("Conflict").message(ex.getMessage()).build();

		return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
	}

	// Handles all errors on list. Special useful for the CartItemRequest
	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiErrorResponse> handleMethodValidation(HandlerMethodValidationException ex) {

		// Antes era List<String>; se normaliza a Map<String,String> para
		// que 'errors' tenga siempre el mismo tipo en toda la API.
		Map<String, String> fieldErrors = new LinkedHashMap<>();
		int i = 0;
		for (var err : ex.getAllErrors()) {
			fieldErrors.put("error_" + (i++), err.getDefaultMessage());
		}

		ApiErrorResponse body = ApiErrorResponse.builder().timestamp(Instant.now().toString()).status(400)
				.error("Bad Request").message("Validation failed").errors(fieldErrors).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiErrorResponse> handleUnexpected(Exception ex) {

	    ApiErrorResponse body = ApiErrorResponse.builder()
	            .timestamp(Instant.now().toString())
	            .status(500)
	            .error("Internal Server Error")
	            .message("An unexpected error occurred.")
	            .detail(ex.getMessage())
	            .build();

	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}
	
	// Handlres inactive gameMachine
	@ExceptionHandler(InactiveTestException.class)
	public ResponseEntity<ApiErrorResponse> handleInactiveMachine(
			InactiveTestException ex) {

	    ApiErrorResponse body = ApiErrorResponse.builder()
	            .timestamp(Instant.now().toString())
	            .status(409)
	            .error("Conflict")
	            .message(ex.getMessage())
	            .build();

	    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
	}
	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<ApiErrorResponse> uploadFile(
			FileUploadException ex) {

	    ApiErrorResponse body = ApiErrorResponse.builder()
	            .timestamp(Instant.now().toString())
	            .status(500)
	            .error("Error on upload the image")
	            .message(ex.getMessage())
	            .build();

	    return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
	}
	
}
