package com.liskovsoft.browser.helpers;

/**
 * TODO: copied from somewhere in the web
 */

import javax.net.ssl.SSLSocket;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteOrder;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
/**
 * APIs for interacting with Android's core library. The main purpose of this
 * class is to access hidden methods on
 *
 * org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl
 *
 * via reflection.
 */
public final class Libcore {
    private Libcore() {
    }
    private static final Class<?> openSslSocketClass;
    private static final Method setEnabledCompressionMethods;
    private static final Method setUseSessionTickets;
    private static final Method setHostname;
    private static final Method setNpnProtocols;
    private static final Method getNpnSelectedProtocol;
    private static final Constructor<DeflaterOutputStream> deflaterOutputStreamConstructor;
    static {
        try {
            openSslSocketClass = Class.forName(
                    "org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
            setEnabledCompressionMethods = openSslSocketClass.getMethod(
                    "setEnabledCompressionMethods", String[].class);
            setUseSessionTickets = openSslSocketClass.getMethod(
                    "setUseSessionTickets", boolean.class);
            setHostname = openSslSocketClass.getMethod("setHostname", String.class);
            setNpnProtocols = openSslSocketClass.getMethod("setNpnProtocols", byte[].class);
            getNpnSelectedProtocol = openSslSocketClass.getMethod("getNpnSelectedProtocol");
            deflaterOutputStreamConstructor = DeflaterOutputStream.class.getConstructor(
                    new Class[] { OutputStream.class, Deflater.class, boolean.class });
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe);
        } catch (NoSuchMethodException nsme) {
            throw new RuntimeException(nsme);
        }
    }
    public static DeflaterOutputStream newDeflaterOutputStream(
            OutputStream os, Deflater deflater, boolean syncFlush) {
        try {
            return deflaterOutputStreamConstructor.newInstance(os, deflater, syncFlush);
        } catch (InstantiationException e) {
            throw new RuntimeException("Unknown DeflaterOutputStream implementation.");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unknown DeflaterOutputStream implementation.");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unknown DeflaterOutputStream implementation.");
        }
    }
    public static void makeTlsTolerant(SSLSocket socket, String socketHost, boolean tlsTolerant) {
        if (!tlsTolerant) {
            socket.setEnabledProtocols(new String[] {"SSLv3"});
            return;
        }
        if (openSslSocketClass.isInstance(socket)) {
            try {
                String[] compressionMethods = {"ZLIB"};
                setEnabledCompressionMethods.invoke(socket,
                        new Object[] {compressionMethods});
                setUseSessionTickets.invoke(socket, true);
                setHostname.invoke(socket, socketHost);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        } else {
            throw new RuntimeException("Unknown socket implementation.");
        }
    }
    /**
     * Returns the negotiated protocol, or null if no protocol was negotiated.
     */
    public static byte[] getNpnSelectedProtocol(SSLSocket socket) {
        if (openSslSocketClass.isInstance(socket)) {
            try {
                return (byte[]) getNpnSelectedProtocol.invoke(socket);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            }
        } else {
            throw new RuntimeException("Unknown socket implementation.");
        }
    }
    public static void setNpnProtocols(SSLSocket socket, byte[] npnProtocols) {
        if (openSslSocketClass.isInstance(socket)) {
            try {
                setNpnProtocols.invoke(socket, new Object[] {npnProtocols});
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Unknown socket implementation.");
        }
    }
    public static void deleteIfExists(File file) throws IOException {
        // okhttp-changed: was Libcore.os.remove() in a try/catch block
        file.delete();
    }
    public static void logW(String warning) {
        // okhttp-changed: was System.logw()
        System.out.println(warning);
    }
    public static int getEffectivePort(URI uri) {
        return getEffectivePort(uri.getScheme(), uri.getPort());
    }
    public static int getEffectivePort(URL url) {
        return getEffectivePort(url.getProtocol(), url.getPort());
    }
    private static int getEffectivePort(String scheme, int specifiedPort) {
        if (specifiedPort != -1) {
            return specifiedPort;
        }
        if ("http".equalsIgnoreCase(scheme)) {
            return 80;
        } else if ("https".equalsIgnoreCase(scheme)) {
            return 443;
        } else {
            return -1;
        }
    }
    public static void checkOffsetAndCount(int arrayLength, int offset, int count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    public static void tagSocket(Socket socket) {
    }
    public static void untagSocket(Socket socket) throws SocketException {
    }
    public static URI toUriLenient(URL url) throws URISyntaxException {
        return url.toURI(); // this isn't as good as the built-in toUriLenient
    }
    public static void pokeInt(byte[] dst, int offset, int value, ByteOrder order) {
        if (order == ByteOrder.BIG_ENDIAN) {
            dst[offset++] = (byte) ((value >> 24) & 0xff);
            dst[offset++] = (byte) ((value >> 16) & 0xff);
            dst[offset++] = (byte) ((value >>  8) & 0xff);
            dst[offset  ] = (byte) ((value >>  0) & 0xff);
        } else {
            dst[offset++] = (byte) ((value >>  0) & 0xff);
            dst[offset++] = (byte) ((value >>  8) & 0xff);
            dst[offset++] = (byte) ((value >> 16) & 0xff);
            dst[offset  ] = (byte) ((value >> 24) & 0xff);
        }
    }
}