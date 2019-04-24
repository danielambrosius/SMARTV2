package smrt2;

import java.io.IOException;

public class FileNotClosedException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	FileNotClosedException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
