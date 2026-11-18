package com.promtexmaster.PGPAKS.Backend;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Allows every client when the setting is empty. Otherwise the remote address
 * must match an exact IP or a CIDR range from the comma-separated list.
 */
final class ClientAddressAllowList {

    private ClientAddressAllowList() {
    }

    static boolean permits(String remoteAddress, String allowedAddresses) {
        if (allowedAddresses == null || allowedAddresses.trim().isEmpty()) {
            return true;
        }
        if (remoteAddress == null || remoteAddress.trim().isEmpty()) {
            return false;
        }
        String remote = normalize(remoteAddress);
        String[] rules = allowedAddresses.split(",");
        for (String rule : rules) {
            String candidate = rule.trim();
            if (candidate.isEmpty()) {
                continue;
            }
            if (matches(remote, candidate)) {
                return true;
            }
        }
        return false;
    }

    private static boolean matches(String remoteAddress, String rule) {
        int slash = rule.indexOf('/');
        if (slash < 0) {
            return sameAddress(remoteAddress, rule);
        }
        String network = rule.substring(0, slash).trim();
        String prefixText = rule.substring(slash + 1).trim();
        int prefix;
        try {
            prefix = Integer.parseInt(prefixText);
        } catch (NumberFormatException ex) {
            return false;
        }
        try {
            byte[] address = literalAddress(remoteAddress);
            byte[] networkAddress = literalAddress(network);
            if (address.length != networkAddress.length) {
                return false;
            }
            int maxPrefix = address.length * 8;
            if (prefix < 0 || prefix > maxPrefix) {
                return false;
            }
            int fullBytes = prefix / 8;
            int remainder = prefix % 8;
            for (int i = 0; i < fullBytes; i++) {
                if (address[i] != networkAddress[i]) {
                    return false;
                }
            }
            if (remainder == 0) {
                return true;
            }
            int mask = (0xFF << (8 - remainder)) & 0xFF;
            return (address[fullBytes] & mask) == (networkAddress[fullBytes] & mask);
        } catch (UnknownHostException ex) {
            return false;
        }
    }

    private static boolean sameAddress(String left, String right) {
        try {
            return Arrays.equals(literalAddress(left), literalAddress(right));
        } catch (UnknownHostException ex) {
            return false;
        }
    }

    private static String normalize(String address) {
        String value = address.trim();
        if (value.startsWith("::ffff:") && value.indexOf('.', value.indexOf("::ffff:")) > 0) {
            return value.substring("::ffff:".length());
        }
        return value;
    }

    private static byte[] literalAddress(String address) throws UnknownHostException {
        String literal = normalize(address);
        if (literal.indexOf(':') < 0 && !literal.matches("[0-9.]+")) {
            throw new UnknownHostException(literal);
        }
        if (literal.indexOf(':') >= 0 && !literal.matches("[0-9a-fA-F:]+")) {
            throw new UnknownHostException(literal);
        }
        return InetAddress.getByName(literal).getAddress();
    }
}
