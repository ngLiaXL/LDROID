package com.ldroid.kwei.common.util;

import java.lang.reflect.Type;

import com.ldroid.kwei.common.lib.gson.GsonBuilder;
import com.ldroid.kwei.common.lib.gson.JsonSyntaxException;

public class JsonUtils {

	private JsonUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static <T> String toJson(Object obj, Type typeOfT) {
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
				.toJson(obj, typeOfT);
	}

	public static String toJson(Object obj) {
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(obj);
	}

	public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
				.fromJson(json, classOfT);
	}

	public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
				.fromJson(json, typeOfT);
	}

}
