package com.github.serializepojo.validation;

import java.sql.Date;

public class Validations {

	public static boolean isSimpleType(Class<?> type) {
		if (type.isPrimitive()) {
			return true;
		}

		if (String.class == type) {
			return true;
		} else if (Boolean.class == type) {
			return true;
		} else if (Character.class == type) {
			return true;
		} else if (Byte.class == type) {
			return true;
		} else if (Short.class == type) {
			return true;
		} else if (Integer.class == type) {
			return true;
		} else if (Long.class == type) {
			return true;
		} else if (Float.class == type) {
			return true;
		} else if (Double.class == type) {
			return true;
		} else if (Date.class == type) {
			return true;
		} else if (java.util.Date.class == type) {
			return true;
		}

		return false;

	}
}
