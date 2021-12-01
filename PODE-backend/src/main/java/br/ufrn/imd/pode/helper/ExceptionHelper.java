package br.ufrn.imd.pode.helper;

public class ExceptionHelper {

	private String message;

	public ExceptionHelper() {
		this.message = "";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void add(String message) {
		if (!this.message.isEmpty()) {
			this.message += ", ";
		}
		this.message += message;
	}

}
