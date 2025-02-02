package com.management.exception;

import org.springframework.http.HttpStatus;


public class ApiError {

	private String timestamp;
    private int status;
    private HttpStatus error;
    private String message;
    private String path;
    
    
    
    
    
	public ApiError() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApiError(String timestamp, int status, HttpStatus error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public HttpStatus getError() {
		return error;
	}
	public void setError(HttpStatus error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
    
    
}
