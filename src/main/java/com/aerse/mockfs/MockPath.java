package com.aerse.mockfs;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;

public class MockPath implements Path {
	
	private final Path impl;
	private final FileSystem fs;
	
	public MockPath(Path impl, FileSystem fs) {
		this.impl = impl;
		this.fs = fs;
	}

	@Override
	public FileSystem getFileSystem() {
		return fs;
	}

	@Override
	public boolean isAbsolute() {
		return impl.isAbsolute();
	}
	
	public Path getImpl() {
		return impl;
	}

	@Override
	public Path getRoot() {
		return new MockPath(impl.getRoot(), fs);
	}

	@Override
	public Path getFileName() {
		return new MockPath(impl.getFileName(), fs);
	}

	@Override
	public Path getParent() {
		return new MockPath(impl.getParent(), fs);
	}

	@Override
	public int getNameCount() {
		return impl.getNameCount();
	}

	@Override
	public Path getName(int index) {
		return new MockPath(impl.getName(index), fs);
	}

	@Override
	public Path subpath(int beginIndex, int endIndex) {
		return new MockPath(impl.subpath(beginIndex, endIndex), fs);
	}

	@Override
	public boolean startsWith(Path other) {
		return impl.startsWith(other);
	}

	@Override
	public boolean startsWith(String other) {
		return impl.startsWith(other);
	}

	@Override
	public boolean endsWith(Path other) {
		return impl.endsWith(other);
	}

	@Override
	public boolean endsWith(String other) {
		return impl.endsWith(other);
	}

	@Override
	public Path normalize() {
		return new MockPath(impl.normalize(), fs);
	}

	@Override
	public Path resolve(Path other) {
		return new MockPath(impl.resolve(other), fs);
	}

	@Override
	public Path resolve(String other) {
		return new MockPath(impl.resolve(other), fs);
	}

	@Override
	public Path resolveSibling(Path other) {
		return new MockPath(impl.resolveSibling(other), fs);
	}

	@Override
	public Path resolveSibling(String other) {
		return new MockPath(impl.resolveSibling(other), fs);
	}

	@Override
	public Path relativize(Path other) {
		return new MockPath(impl.relativize(other), fs);
	}

	@Override
	public URI toUri() {
		return impl.toUri();
	}

	@Override
	public Path toAbsolutePath() {
		return new MockPath(impl.toAbsolutePath(), fs);
	}

	@Override
	public Path toRealPath(LinkOption... options) throws IOException {
		return new MockPath(impl.toRealPath(options), fs);
	}

	@Override
	public File toFile() {
		return impl.toFile();
	}

	@Override
	public WatchKey register(WatchService watcher, Kind<?>[] events, Modifier... modifiers) throws IOException {
		return impl.register(watcher, events, modifiers);
	}

	@Override
	public WatchKey register(WatchService watcher, Kind<?>... events) throws IOException {
		return impl.register(watcher, events);
	}

	@Override
	public Iterator<Path> iterator() {
		return impl.iterator();
	}

	@Override
	public int compareTo(Path other) {
		return impl.compareTo(other);
	}

	@Override
	public String toString() {
		return impl.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((impl == null) ? 0 : impl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MockPath other = (MockPath) obj;
		if (impl == null) {
			if (other.impl != null)
				return false;
		} else if (!impl.equals(other.impl))
			return false;
		return true;
	}
	
}
