package com.aerse.mockfs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

public class MockFileSystem extends FileSystem {

	private final FileSystem impl;
	private final MockFileSystemProvider fileSystemProvider;

	public MockFileSystem(FileSystem impl) {
		if (impl == null) {
			throw new IllegalArgumentException("impl cannot be empty");
		}
		this.impl = impl;
		fileSystemProvider = new MockFileSystemProvider(impl.provider());
	}

	@Override
	public FileSystemProvider provider() {
		return fileSystemProvider;
	}

	@Override
	public void close() throws IOException {
		impl.close();
	}

	@Override
	public boolean isOpen() {
		return impl.isOpen();
	}

	@Override
	public boolean isReadOnly() {
		return impl.isReadOnly();
	}

	@Override
	public String getSeparator() {
		return impl.getSeparator();
	}

	@Override
	public Iterable<Path> getRootDirectories() {
		return impl.getRootDirectories();
	}

	@Override
	public Iterable<FileStore> getFileStores() {
		return impl.getFileStores();
	}

	@Override
	public Set<String> supportedFileAttributeViews() {
		return impl.supportedFileAttributeViews();
	}

	@Override
	public Path getPath(String first, String... more) {
		return new MockPath(impl.getPath(first, more), this);
	}

	@Override
	public PathMatcher getPathMatcher(String syntaxAndPattern) {
		return impl.getPathMatcher(syntaxAndPattern);
	}

	@Override
	public UserPrincipalLookupService getUserPrincipalLookupService() {
		return impl.getUserPrincipalLookupService();
	}

	@Override
	public WatchService newWatchService() throws IOException {
		return impl.newWatchService();
	}

}
