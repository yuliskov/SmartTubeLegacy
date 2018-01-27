package edu.mit.mobile.android.appupdater.downloadmanager;

import android.util.Log;
import org.xbill.DNS.ARecord;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Try to resolve address with Google's dns below
 * <pre>
 * IPv4:
 * 8.8.8.8
 * 8.8.4.4
 * </pre>
 * <pre>
 * IPv6:
 * 2001:4860:4860::8888
 * 2001:4860:4860::8844
 * </pre>
 */
public class GoogleResolver {
    private static final String TAG = GoogleResolver.class.getSimpleName();
    private static final String GOOGLE_DNS_IPV4 = "8.8.8.8";
    private static final String GOOGLE_DNS_IPV6 = "2001:4860:4860::8888";
    private static final int RESOLVE_TIMEOUT_S = 5;
    private final Resolver resolver;

    public GoogleResolver() {
        String dns = findProperServer();
        resolver = createResolver(dns);
        resolver.setTimeout(RESOLVE_TIMEOUT_S);
    }

    private Resolver createResolver(String dns) {
        try {
            return new SimpleResolver(dns);
        } catch (UnknownHostException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            throw new IllegalStateException(ex);
        }
    }

    public List<InetAddress> resolve(String host) {
        List<InetAddress> hostIPs = new ArrayList<>();
        try {
            Lookup lookup = new Lookup(host, Type.A);
            lookup.setResolver(resolver);
            Record[] records = lookup.run();
            if (records == null) {
                return hostIPs;
            }
            for (Record record : records) {
                hostIPs.add(((ARecord) record).getAddress());
            }
        } catch (TextParseException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            throw new IllegalStateException(ex);
        }
        return hostIPs;
    }

    private String findProperServer() {
        return testIPv4() ? GOOGLE_DNS_IPV4 : GOOGLE_DNS_IPV6;
    }

    private boolean testIPv4() {
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            while (networks.hasMoreElements()) {
                NetworkInterface network = networks.nextElement();
                for (InterfaceAddress address : network.getInterfaceAddresses()) {
                    if (address.getAddress() instanceof Inet4Address) {
                        // found IPv4 address
                        // do any other validation of address you may need here
                        return true;
                    }
                }
            }
            return false;
        } catch (SocketException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }
}
