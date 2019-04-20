package com.aerse.mockfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
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
	public void testEqualsHashcode() {
		Path anotherBasePath = mockFs.getPath(tempFolder.getRoot().getAbsolutePath());
		assertEquals(basePath.hashCode(), anotherBasePath.hashCode());
		assertTrue(anotherBasePath.equals(basePath));
	}

	@Test
	public void testFullyMockUnderlaying() {
		Path expected = FileSystems.getDefault().getPath(tempFolder.getRoot().getAbsolutePath());
		assertEquals(expected.isAbsolute(), basePath.isAbsolute());
		assertEquals(expected.getNameCount(), basePath.getNameCount());
		assertEquals(expected.toUri(), basePath.toUri());
		assertEquals(expected.toFile().getAbsolutePath(), basePath.toFile().getAbsolutePath());
		assertFalse(basePath.equals(expected));
		assertTrue(expected.getRoot().equals(((MockPath) basePath.getRoot()).getImpl()));
	}

	@Before
	public void start() {
		FileSystem fs = FileSystems.getDefault();
		mockFs = new MockFileSystem(fs);
		basePath = mockFs.getPath(tempFolder.getRoot().getAbsolutePath());
	}
}
