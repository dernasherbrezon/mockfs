package com.aerse.mockfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public interface ByteChannelCallback {

	int onRead(ReadableByteChannel channel, ByteBuffer dst) throws IOException;

	int onWrite(WritableByteChannel channel, ByteBuffer dst) throws IOException;
}
