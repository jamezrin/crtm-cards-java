package com.github.jamezrin.crtmcards.security;

import org.apache.http.conn.ssl.DefaultHostnameVerifier;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AdditionalSubjectHostnameVerifier implements HostnameVerifier {
    private final HostnameVerifier hostnameVerifier = new DefaultHostnameVerifier();
    private final String[] additionalSubjectAlts;

    public AdditionalSubjectHostnameVerifier(String... additionalSubjectAlts) {
        this.additionalSubjectAlts = additionalSubjectAlts;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        SSLSession sessionWrapper = new AdditionalSubjectSessionWrapper(
                session, additionalSubjectAlts);
        return hostnameVerifier.verify(hostname, sessionWrapper);
    }

    public String[] getAdditionalSubjectAlts() {
        return additionalSubjectAlts;
    }
}
