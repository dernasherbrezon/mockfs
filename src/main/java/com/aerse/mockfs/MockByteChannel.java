package com.aerse.mockfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

public class MockByteChannel implements SeekableByteChannel {

	private final SeekableByteChannel impl;
	private final ByteChannelCallback callback;

	public MockByteChannel(SeekableByteChannel impl, ByteChannelCallback callback) {
		this.impl = impl;
		this.callback = callback;
	}

	@Override
	public boolean isOpen() {
		return impl.isOpen();
	}

	@Override
	public void close() throws IOException {
		impl.close();
	}

	@Override
	public int read(ByteBuffer dst) throws IOException {
		return callback.onRead(impl, dst);
	}

	@Override
	public int write(ByteBuffer src) throws IOException {
		return callback.onWrite(impl, src);
	}

	@Override
	public long position() throws IOException {
		return impl.position();
	}

	@Override
	public SeekableByteChannel position(long newPosition) throws IOException {
		return new MockByteChannel(impl.position(newPosition), callback);
	}

	@Override
	public long size() throws IOException {
		return impl.size();
	}

	@Override
	public SeekableByteChannel truncate(long size) throws IOException {
		return impl.truncate(size);
	}

}
