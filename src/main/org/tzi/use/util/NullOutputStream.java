package org.tzi.use.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * OuputStream that does nothing.
 * All output is lost
 * @author lhamann
 *
 */
public class NullOutputStream extends OutputStream {
	@Override
	public void write(int b) throws IOException {
	}
}
