package com.yl.merchat.util;

public class ThrowableUtil {

	public static String getMessage(Throwable throwable) {
		return throwable == null ? "" : throwable.getMessage();
	}
}