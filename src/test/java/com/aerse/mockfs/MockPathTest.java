package com.aerse.mockfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class MockPathTest {

	private MockFileSystem mockFs;
	private Path basePath;

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testCreateDirectories() throws Exception {
		Path anotherBasePath = basePath.resolve("test").resolve("test");
		Files.createDirectories(anotherBasePath);
		assertTrue(Files.exists(anotherBasePath));
	}

	@Test
	public void testEqualsHashcode() {
		Path anotherBasePath = mockFs.getPath(tempFolder.getRoot().getAbsolutePath());
		assertEquals(basePath.hashCode(), anotherBasePath.hashCode());
		assertEquals(anotherBasePath, basePath);
	}

	@Test
	public void testFullyMockUnderlaying() {
		Path expected = FileSystems.getDefault().getPath(tempFolder.getRoot().getAbsolutePath());
		assertEquals(expected.isAbsolute(), basePath.isAbsolute());
		assertEquals(expected.getNameCount(), basePath.getNameCount());
		assertEquals(expected.toUri(), basePath.toUri());
		assertEquals(expected.toFile().getAbsolutePath(), basePath.toFile().getAbsolutePath());
		assertNotEquals(basePath, expected);
		assertEquals(expected.getRoot(), ((MockPath) basePath.getRoot()).getImpl());
	}

	@Before
	public void start() {
		FileSystem fs = FileSystems.getDefault();
		mockFs = new MockFileSystem(fs);
		basePath = mockFs.getPath(tempFolder.getRoot().getAbsolutePath());
	}
}
