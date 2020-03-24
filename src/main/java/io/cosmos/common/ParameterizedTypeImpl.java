package io.cosmos.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings("rawtypes")
public class ParameterizedTypeImpl implements ParameterizedType {

	private final Class raw;
	private final Type[] args;

	public ParameterizedTypeImpl(Class raw, Type[] args) {
		this.raw = raw;
		this.args = args != null ? args : new Type[0];
	}

	public Type[] getActualTypeArguments() {
		return args;
	}

	public Type getRawType() {
		return raw;
	}

	public Type getOwnerType() {
		return null;
	}
}
