package com.aerse.mockfs;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;
import java.util.Set;

public class MockFileSystemProvider extends FileSystemProvider {

	private final FileSystemProvider impl;

	public MockFileSystemProvider(FileSystemProvider impl) {
		this.impl = impl;
	}

	@Override
	public String getScheme() {
		return impl.getScheme();
	}

	@Override
	public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {
		return new MockFileSystem(impl.newFileSystem(uri, env));
	}

	@Override
	public FileSystem getFileSystem(URI uri) {
		return new MockFileSystem(impl.getFileSystem(uri));
	}

	@Override
	public Path getPath(URI uri) {
		return impl.getPath(uri);
	}

	@Override
	public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		return impl.newByteChannel(mockPath.getImpl(), options, attrs);
	}

	@Override
	public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
		if (!(dir.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) dir;
		return impl.newDirectoryStream(mockPath.getImpl(), filter);
	}

	@Override
	public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
		if (!(dir.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) dir;
		impl.createDirectory(mockPath.getImpl(), attrs);
	}

	@Override
	public void delete(Path path) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		impl.delete(mockPath.getImpl());
	}

	@Override
	public void copy(Path source, Path target, CopyOption... options) throws IOException {
		if (!(source.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		if (!(target.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockSource = (MockPath) source;
		MockPath mockTarget = (MockPath) target;
		impl.copy(mockSource.getImpl(), mockTarget.getImpl(), options);
	}

	@Override
	public void move(Path source, Path target, CopyOption... options) throws IOException {
		if (!(source.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		if (!(target.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockSource = (MockPath) source;
		MockPath mockTarget = (MockPath) target;
		impl.move(mockSource.getImpl(), mockTarget.getImpl(), options);
	}

	@Override
	public boolean isSameFile(Path path, Path path2) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		if (!(path2.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockSource = (MockPath) path;
		MockPath mockTarget = (MockPath) path2;
		return impl.isSameFile(mockSource.getImpl(), mockTarget.getImpl());
	}

	@Override
	public boolean isHidden(Path path) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		return impl.isHidden(mockPath.getImpl());
	}

	@Override
	public FileStore getFileStore(Path path) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		return impl.getFileStore(mockPath.getImpl());
	}

	@Override
	public void checkAccess(Path path, AccessMode... modes) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		impl.checkAccess(mockPath.getImpl(), modes);
	}

	@Override
	public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options) {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		return impl.getFileAttributeView(mockPath.getImpl(), type, options);
	}

	@Override
	public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		return impl.readAttributes(mockPath.getImpl(), type, options);
	}

	@Override
	public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		return impl.readAttributes(mockPath.getImpl(), attributes, options);
	}

	@Override
	public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
		if (!(path.getFileSystem() instanceof MockFileSystem)) {
			throw new ProviderMismatchException();
		}
		MockPath mockPath = (MockPath) path;
		impl.setAttribute(mockPath.getImpl(), attribute, value, options);
	}

}
