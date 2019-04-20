package com.aerse.mockfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class MockFileSystemTest {

	private MockFileSystem mockFs;
	private Path basePath;

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testResolve() {
		Path path = basePath.resolve("path");
		assertTrue(path.getFileSystem() == mockFs);
	}

	@Test
	public void testWriteRead() throws IOException {
		String sampleText = UUID.randomUUID().toString();
		Path path = basePath.resolve(UUID.randomUUID().toString());
		mockFs.mock(path, new NoOpByteChannelCallback());
		try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			w.write(sampleText);
		}
		StringBuilder str = new StringBuilder();
		try (BufferedReader r = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String curLine = null;
			while ((curLine = r.readLine()) != null) {
				str.append(curLine).append("\n");
			}
		}
		assertEquals(sampleText, str.toString().trim());
	}
	
	@Test(expected = IOException.class)
	public void testFailingWrite() throws IOException {
		byte[] data = createSampleData();
		Path path = basePath.resolve(UUID.randomUUID().toString());
		mockFs.mock(path, new FailingByteChannelCallback(5));
		try (OutputStream w = Files.newOutputStream(path)) {
			w.write(data);
		}
	}

	@Test
	public void testFailingReadCallback() throws IOException {
		byte[] data = createSampleData();
		Path path = basePath.resolve(UUID.randomUUID().toString());
		try (OutputStream w = Files.newOutputStream(path)) {
			w.write(data);
		}
		
		int failAfterBytes = 5;
		mockFs.mock(path, new FailingByteChannelCallback(failAfterBytes));

		byte[] output = new byte[data.length];
		int readBytes = -1;
		int currentRead = 0;
		try (InputStream r = Files.newInputStream(path)) {
			while ((readBytes += r.read(output, currentRead, output.length - currentRead)) != -1) {
				// do nothing
			}
			fail("expected IOException");
		} catch( IOException e ) {
			//do nothing
		}
		assertEquals(failAfterBytes, readBytes);
	}

	private static byte[] createSampleData() {
		byte[] data = new byte[10];
		for (byte i = 0; i < data.length; i++) {
			data[i] = i;
		}
		return data;
	}

	@Test
	public void testDirectoryStream() throws Exception {
		String subDirectoryName = UUID.randomUUID().toString();
		Files.createDirectory(basePath.resolve(subDirectoryName));
		int total = 0;
		for (Path cur : Files.newDirectoryStream(basePath)) {
			assertEquals(subDirectoryName, cur.getFileName().toString());
			total++;
		}
		assertEquals(1, total);
	}

	@Test
	public void testDelete() throws Exception {
		Path pathToDelete = basePath.resolve(UUID.randomUUID().toString());
		Files.createDirectory(pathToDelete);
		assertTrue(Files.deleteIfExists(pathToDelete));
	}

	@Test
	public void testCopy() throws Exception {
		String path1 = UUID.randomUUID().toString();
		String path2 = UUID.randomUUID().toString();
		Path source = Files.createDirectory(basePath.resolve(path1));
		Path target = basePath.resolve(path2);
		assertNotNull(Files.copy(source, target));
	}

	@Test
	public void testMove() throws Exception {
		String path1 = UUID.randomUUID().toString();
		String path2 = UUID.randomUUID().toString();
		Path source = Files.createDirectory(basePath.resolve(path1));
		Path target = basePath.resolve(path2);
		assertNotNull(Files.move(source, target));
	}

	@Test
	public void testSameFile() throws Exception {
		Path path = mockFs.getPath(tempFolder.getRoot().getAbsolutePath());
		assertTrue(Files.isSameFile(basePath, path));
	}

	@Test
	public void testHidden() throws Exception {
		assertFalse(Files.isHidden(basePath));
	}

	@Test
	public void testCheckAccess() throws Exception {
		assertTrue(Files.exists(basePath));
	}

	@Test
	public void testFilestore() throws Exception {
		assertNotNull(Files.getFileStore(basePath));
	}

	@Test
	public void testAttributes() throws Exception {
		BasicFileAttributes attrs = Files.readAttributes(basePath, BasicFileAttributes.class);
		assertTrue(attrs.isDirectory());
		Map<String, Object> result = Files.readAttributes(basePath, "size");
		assertEquals(1, result.size());
		BasicFileAttributeView view = Files.getFileAttributeView(basePath, BasicFileAttributeView.class);
		assertTrue(view.readAttributes().isDirectory());
		assertNotNull(Files.setAttribute(basePath, "lastModifiedTime", FileTime.from(System.currentTimeMillis(), TimeUnit.MILLISECONDS)));
	}

	@Before
	public void start() {
		FileSystem fs = FileSystems.getDefault();
		mockFs = new MockFileSystem(fs);
		basePath = mockFs.getPath(tempFolder.getRoot().getAbsolutePath());
	}

}
