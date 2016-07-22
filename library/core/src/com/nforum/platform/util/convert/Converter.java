package com.nforum.platform.util.convert;

public interface Converter <T,S>{

	S convert(T t);
}
