package com.promtexmaster.PGPAKS.Backend;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClientAddressAllowListTest {

    @Test
    public void emptySettingAllowsAnyClient() {
        assertTrue(ClientAddressAllowList.permits("203.0.113.5", ""));
        assertTrue(ClientAddressAllowList.permits("203.0.113.5", null));
    }

    @Test
    public void exactAddressAndCidr() {
        String allowed = "127.0.0.1, ::1, 192.168.1.0/24";
        assertTrue(ClientAddressAllowList.permits("127.0.0.1", allowed));
        assertTrue(ClientAddressAllowList.permits("::1", allowed));
        assertTrue(ClientAddressAllowList.permits("0:0:0:0:0:0:0:1", allowed));
        assertTrue(ClientAddressAllowList.permits("192.168.1.40", allowed));
        assertFalse(ClientAddressAllowList.permits("192.168.2.40", allowed));
        assertFalse(ClientAddressAllowList.permits("203.0.113.8", allowed));
    }

    @Test
    public void mappedIpv4MatchesPlainAddress() {
        assertTrue(ClientAddressAllowList.permits("::ffff:10.1.2.3", "10.1.2.3"));
    }
}
