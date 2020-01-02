package com.liskovsoft.m3uparser.core.utils;


import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class IOUtils {

    public static final String UTF_8 = StandardCharsets.UTF_8.name();

    /**
     * The default buffer size to use.
     */
    static final
    private int DEFAULT_BUFFER_SIZE = 1024 * 8;

    /**
     * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     *
     * @param input  the <code>InputStream</code> to read from
     * @param output  the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.3
     */
    static
    public long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    static
    public void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (RuntimeException rethrown) {
            throw rethrown;
        } catch (Exception ignore) {

        }
    }

    static
    public void copy(String string, OutputStream output) throws IOException {
        IOUtils.copy(string, output, Charset.forName("UTF-8"));
    }

    static
    public void copy(String string, OutputStream output, Charset encoding) throws IOException {
        Writer writer = new OutputStreamWriter(output,
                ((encoding != null) ? encoding.displayName() : "UTF-8"));
        writer.write(string);
        writer.flush();
    }

    static
    public String toString(InputStream stream) throws IOException {
        return IOUtils.toString(stream, Charset.forName("UTF-8"));
    }

    static
    public String toString(InputStream stream, Charset charset) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, charset));
        StringWriter writer = new StringWriter();
        String line;
        while ((line = br.readLine()) != null) {
            writer.write(line);
            writer.write("\n");
            writer.flush();
        }

        return writer.toString();
    }

    public static String[] toStringArray(InputStream stream) throws IOException {
        return IOUtils.toStringArray(stream, Charset.forName("UTF-8"));
    }

    public static String[] toStringArray(InputStream stream, Charset charset) throws IOException {
        List<String> lines = new LinkedList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, charset));
        String line;
        while ((line = br.readLine()) != null) {
            if (!Strings.isNullOrEmpty(line))
                lines.add(line.trim());
        }

        return lines.toArray(new String[lines.size()]);
    }

    public static void  write(String data, OutputStream output, String encoding)
        throws IOException {
        if (data != null) {
            if (encoding == null) {
                write(data, output);
            } else {
                output.write(data.getBytes(encoding));
            }
        }
    }

    public static void  write(String data, OutputStream output)
        throws IOException {
        if (data != null) {
            output.write(data.getBytes());
        }
    }

    private IOUtils() {  }
}
