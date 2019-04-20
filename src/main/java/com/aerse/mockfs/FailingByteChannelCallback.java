package com.aerse.mockfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class FailingByteChannelCallback implements ByteChannelCallback {

	private final int failAfterBytes;

	private int processedBytes = 0;

	public FailingByteChannelCallback(int failAfterBytes) {
		this.failAfterBytes = failAfterBytes;
	}

	@Override
	public int onRead(ReadableByteChannel channel, ByteBuffer dst) throws IOException {
		if (failAfterBytes <= processedBytes) {
			throw new IOException("controlled failure after " + failAfterBytes + " bytes");
		}
		if (dst.remaining() < (failAfterBytes - processedBytes)) {
			int result = channel.read(dst);
			processedBytes += result;
			return result;
		}
		ByteBuffer smaller = (ByteBuffer) dst.limit(dst.position() + (failAfterBytes - processedBytes) + 1);
		int result = channel.read(smaller);
		processedBytes += result;
		return result;
	}

	@Override
	public int onWrite(WritableByteChannel channel, ByteBuffer dst) throws IOException {
		if (failAfterBytes <= processedBytes) {
			throw new IOException("controlled failure after " + failAfterBytes + " bytes");
		}
		if (dst.remaining() < (failAfterBytes - processedBytes)) {
			int result = channel.write(dst);
			processedBytes += result;
			return result;
		}
		int previousLimit = dst.limit();
		ByteBuffer smaller = (ByteBuffer) dst.limit(dst.position() + (failAfterBytes - processedBytes));
		int result = channel.write(smaller);
		processedBytes += result;
		//restore limit so next onWrite will be called
		dst.limit(previousLimit);
		return result;
	}

}
