package com.dnia.eaas.controller.exception;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiErrorList extends ApiError {
	private static final long serialVersionUID = 1L;
	
	private List<String> errors;
	
	public ApiErrorList(int code, String msg, Instant date, List<String> errors) {
		super(code, msg, date);
		this.errors = errors;
	}

}
