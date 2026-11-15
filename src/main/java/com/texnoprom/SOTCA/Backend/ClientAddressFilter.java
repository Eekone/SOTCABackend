package com.texnoprom.SOTCA.Backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Rejects clients whose direct connection address is not on
 * {@code sotca.allowed-addresses}. The forwarded-for header is ignored, so a
 * client cannot put itself on the list by sending that header.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ClientAddressFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(ClientAddressFilter.class);

    private final String allowedAddresses;

    public ClientAddressFilter(@Value("${sotca.allowed-addresses:}") String allowedAddresses) {
        this.allowedAddresses = allowedAddresses;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (ClientAddressAllowList.permits(request.getRemoteAddr(), allowedAddresses)) {
            chain.doFilter(request, response);
            return;
        }
        log.warn("Rejected client address {}", request.getRemoteAddr());
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    public void destroy() {
    }
}
