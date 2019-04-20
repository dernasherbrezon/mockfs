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

/**
 * <p>Java FileSystem for simulating IOExceptions.
 * <b>Usage</b>:
 * <pre>
 * MockFileSystem fs = new MockFileSystem(FileSystems.getDefault());
 * fs.mock(fs.getPath("/some/file"), new FailingByteChannelCallback(5));
 * </pre>
 * 
 * Implement {@link ByteChannelCallback} to inject various behaivour. Currently implemented:
 * <ul>
 * 	<li>{@link NoOpByteChannelCallback} - no operation. Just read/write data. Same behaivour as if no mock was configured</li>
 *  <li>{@link FailingByteChannelCallback} - throw IOException after specified number of bytes was read/written</li>
 * </ul>
 *
 */
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
	
	/**
	 * Mock read/write to the specified path.
	 * @param path - if path points to fail, then access to this file will be mocked. If path is directory, then callback will be executed for every file in this directory
	 * @param callback - callback to handle read/write operations
	 */
	public void mock(Path path, ByteChannelCallback callback) {
		fileSystemProvider.mock(path, callback);
	}
	
	/**
	 * Remove mock from the specified path
	 * @param path - cannot be null
	 */
	public void removeMock(Path path) {
		fileSystemProvider.removeMock(path);
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
