package com.github.jamezrin.crtmcards.security;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
import java.security.Principal;
import java.security.cert.Certificate;

public class AdditionalSubjectSessionWrapper implements SSLSession {
    private final SSLSession wrappedSession;
    private final String[] additionalSubjectAlts;
    private Certificate[] wrappedPeerCertificates;

    public AdditionalSubjectSessionWrapper(SSLSession wrappedSession, String[] additionalSubjectAlts) {
        this.wrappedSession = wrappedSession;
        this.additionalSubjectAlts = additionalSubjectAlts;
    }

    @Override
    public byte[] getId() {
        return wrappedSession.getId();
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return wrappedSession.getSessionContext();
    }

    @Override
    public long getCreationTime() {
        return wrappedSession.getCreationTime();
    }

    @Override
    public long getLastAccessedTime() {
        return wrappedSession.getLastAccessedTime();
    }

    @Override
    public void invalidate() {
        wrappedSession.invalidate();
    }

    @Override
    public boolean isValid() {
        return wrappedSession.isValid();
    }

    @Override
    public void putValue(String s, Object o) {
        wrappedSession.putValue(s, o);
    }

    @Override
    public Object getValue(String s) {
        return wrappedSession.getValue(s);
    }

    @Override
    public void removeValue(String s) {
        wrappedSession.removeValue(s);
    }

    @Override
    public String[] getValueNames() {
        return wrappedSession.getValueNames();
    }

    @Override
    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        if (wrappedPeerCertificates == null) {
            Certificate[] certificates = wrappedSession.getPeerCertificates();
            wrappedPeerCertificates = new Certificate[certificates.length];

            for (int i = 0; i < certificates.length; i++) {
                Certificate certificate = certificates[i];
                if (certificate instanceof java.security.cert.X509Certificate) {
                    java.security.cert.X509Certificate x509cert = (java.security.cert.X509Certificate) certificate;
                    wrappedPeerCertificates[i] = new AdditionalSubjectX509CertificateWrapper(x509cert, additionalSubjectAlts);
                } else {
                    wrappedPeerCertificates[i] = certificate;
                }
            }
        }

        return wrappedPeerCertificates;
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return wrappedSession.getLocalCertificates();
    }

    @Override
    public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return wrappedSession.getPeerCertificateChain();
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return wrappedSession.getPeerPrincipal();
    }

    @Override
    public Principal getLocalPrincipal() {
        return wrappedSession.getLocalPrincipal();
    }

    @Override
    public String getCipherSuite() {
        return wrappedSession.getCipherSuite();
    }

    @Override
    public String getProtocol() {
        return wrappedSession.getProtocol();
    }

    @Override
    public String getPeerHost() {
        return wrappedSession.getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return wrappedSession.getPeerPort();
    }

    @Override
    public int getPacketBufferSize() {
        return wrappedSession.getPacketBufferSize();
    }

    @Override
    public int getApplicationBufferSize() {
        return wrappedSession.getApplicationBufferSize();
    }

    public String[] getAdditionalSubjectAlts() {
        return additionalSubjectAlts;
    }

    public SSLSession getWrappedSession() {
        return wrappedSession;
    }
}
