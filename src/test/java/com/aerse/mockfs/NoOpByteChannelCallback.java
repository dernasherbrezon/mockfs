package com.aerse.mockfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class NoOpByteChannelCallback implements ByteChannelCallback {

	@Override
	public int onRead(ReadableByteChannel channel, ByteBuffer dst) throws IOException {
		return channel.read(dst);
	}

	@Override
	public int onWrite(WritableByteChannel channel, ByteBuffer dst) throws IOException {
		return channel.write(dst);
	}

}
