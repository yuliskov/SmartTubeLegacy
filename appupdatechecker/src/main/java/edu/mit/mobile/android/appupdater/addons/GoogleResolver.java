package edu.mit.mobile.android.appupdater.addons;

import android.content.Context;
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
class GoogleResolver {
    private static final String TAG = GoogleResolver.class.getSimpleName();
    private static final String GOOGLE_DNS_IPV4 = "8.8.8.8";
    private static final String GOOGLE_DNS_IPV6 = "2001:4860:4860::8888";
    private final Context mContext;

    public GoogleResolver(Context context) {
        mContext = context;
    }

    public List<InetAddress> resolve(String host) {
        List<InetAddress> hostIPs = new ArrayList<>();
        try {
            String dns = findProperServer();
            Resolver resolver = new SimpleResolver(dns);
            resolver.setTimeout(5);
            Lookup lookup = new Lookup(host, Type.A);
            lookup.setResolver(resolver);
            Record[] records = lookup.run();
            if (records == null) {
                return hostIPs;
            }
            for (Record record : records) {
                hostIPs.add(((ARecord) record).getAddress());
            }
        } catch (UnknownHostException | TextParseException ex) {
            Helpers.showMessage(mContext, ex, TAG);
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
