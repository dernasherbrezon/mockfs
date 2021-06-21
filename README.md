## About [![Build Status](https://travis-ci.com/dernasherbrezon/mockfs.svg?branch=master)](https://travis-ci.com/dernasherbrezon/mockfs) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.aerse%3Amockfs&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.aerse%3Amockfs)

Java FileSystem for simulating IOExceptions

## Usage

1. Add maven dependency:

```xml
<dependency>
  <groupId>com.aerse</groupId>
  <artifactId>mockfs</artifactId>
  <version>1.4</version>
</dependency>
```

2. Ensure code is ready

* Ensure `java.nio.file.Path` is used instead of `java.io.File`. FileSystem is available only for `Path`.
* Ensure `Path::resolve(...)` is used instead of `java.nio.file.Paths.get(...)` or `Path::of(...)`. The latter uses `FileSystems.getDefault()` instead of mocked
* All `Path` should be created from the FileSystem using `FileSystem::getPath(...)` method.

3. Setup the test

For example:

```java
	@Test(expected = IOException.class)
	public void testFailingWrite() throws IOException {
		byte[] data = createSampleData();
		Path path = basePath.resolve(UUID.randomUUID().toString());
		mockFs.mock(path, new FailingByteChannelCallback(5));
		try (OutputStream w = Files.newOutputStream(path)) {
			w.write(data);
		}
	}
```

## Features

 * MockFileSystem could transparently pass the data to the default FileSystem. Just use `com.aerse.mockfs.NoOpByteChannelCallback`:
 ```java
		FileSystem fs = FileSystems.getDefault();
		MockFileSystem mockFs = new MockFileSystem(fs);
		mockFs.mock(path, new NoOpByteChannelCallback());
 ```
 * Use `com.aerse.mockfs.FailingByteChannelCallback` for simulating IOExceptions after configurable number of bytes.
 * Mock read/write to every file in the directory. Just pass this directory to the `com.aerse.mockfs.MockFileSystem::mock(directoryToMock, ...)`
