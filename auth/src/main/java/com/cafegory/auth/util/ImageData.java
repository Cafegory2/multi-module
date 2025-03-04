package com.cafegory.auth.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageData {

	private final byte[] bytes;
	private final String contentType;

	public int getContentLength() {
		return bytes.length;
	}
}
