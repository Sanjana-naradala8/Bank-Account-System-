package Banking;

import java.io.IOException;
import java.io.OutputStream;

public class TeeOutputStream extends OutputStream {
	private OutputStream consoleStream;
	private OutputStream fileStream;

	public TeeOutputStream(OutputStream consoleStream, OutputStream fileStream) {
		this.consoleStream = consoleStream;
		this.fileStream = fileStream;
	}

	@Override
	public void write(int b) throws IOException {
		consoleStream.write(b);
		fileStream.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		consoleStream.write(b, off, len);
		fileStream.write(b, off, len);
	}

	@Override
	public void flush() throws IOException {
		consoleStream.flush();
		fileStream.flush();
	}

	@Override
	public void close() throws IOException {
		consoleStream.close();
		fileStream.close();
	}
}