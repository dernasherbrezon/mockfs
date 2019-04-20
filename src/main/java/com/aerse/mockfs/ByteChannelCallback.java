package com.aerse.mockfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Interface to defining custom read/write operations 
 */
public interface ByteChannelCallback {

	/**
	 * Handle read operations
	 * @param channel - to read from
	 * @param dst - to read to
	 * @return number of bytes actually read
	 * @throws IOException
	 */
	int onRead(ReadableByteChannel channel, ByteBuffer dst) throws IOException;

	/**
	 * Handle write operations
	 * @param channel - to read from
	 * @param dst - to write to
	 * @return number of bytes actually written
	 * @throws IOException
	 */
	int onWrite(WritableByteChannel channel, ByteBuffer dst) throws IOException;
}
