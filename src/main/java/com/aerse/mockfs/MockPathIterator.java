package com.aerse.mockfs;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.Iterator;

class MockPathIterator implements Iterator<Path> {

	private final Iterator<Path> impl;
	private final FileSystem fs;

	MockPathIterator(Iterator<Path> impl, FileSystem fs) {
		this.impl = impl;
		this.fs = fs;
	}

	@Override
	public boolean hasNext() {
		return impl.hasNext();
	}

	@Override
	public Path next() {
		return new MockPath(impl.next(), fs);
	}

}
