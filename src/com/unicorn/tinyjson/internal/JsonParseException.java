/*
 * Copyright (C) 2014 The TinyJson Project of Unicorn
 *
 */

package com.unicorn.tinyjson.internal;


public class JsonParseException extends RuntimeException {

	private static final long serialVersionUID = -2355788424696985719L;

	public JsonParseException(String msg) {
		super(msg);
	}

	public JsonParseException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public JsonParseException(Throwable cause) {
		super(cause);
	}
}
