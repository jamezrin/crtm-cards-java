package com.github.jamezrin.crtmcards.security;

import sun.security.x509.EDIPartyName;

import javax.security.auth.x500.X500Principal;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
import java.util.*;

public class AdditionalSubjectX509CertificateWrapper extends X509Certificate {
    private final X509Certificate wrappedCertificate;
    private final String[] additionalSubjectAlts;

    public AdditionalSubjectX509CertificateWrapper(X509Certificate wrappedCertificate, String[] additionalSubjectAlts) {
        this.wrappedCertificate = wrappedCertificate;
        this.additionalSubjectAlts = additionalSubjectAlts;
    }

    @Override
    public X500Principal getIssuerX500Principal() {
        return wrappedCertificate.getIssuerX500Principal();
    }

    @Override
    public X500Principal getSubjectX500Principal() {
        return wrappedCertificate.getSubjectX500Principal();
    }

    @Override
    public List<String> getExtendedKeyUsage() throws CertificateParsingException {
        return wrappedCertificate.getExtendedKeyUsage();
    }

    @Override
    public Collection<List<?>> getSubjectAlternativeNames() throws CertificateParsingException {
        Collection<List<?>> subjectAltNames = wrappedCertificate.getSubjectAlternativeNames();
        List<List<?>> result = new ArrayList<>(subjectAltNames);

        for (String additionalSubjectAlt : additionalSubjectAlts) {
            result.add(Arrays.asList(EDIPartyName.NAME_DNS, additionalSubjectAlt));
        }

        return Collections.unmodifiableCollection(result);
    }

    @Override
    public Collection<List<?>> getIssuerAlternativeNames() throws CertificateParsingException {
        return wrappedCertificate.getIssuerAlternativeNames();
    }

    @Override
    public void verify(PublicKey key, Provider sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        super.verify(key, sigProvider);
    }

    @Override
    public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        wrappedCertificate.checkValidity();
    }

    @Override
    public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        wrappedCertificate.checkValidity(date);
    }

    @Override
    public int getVersion() {
        return wrappedCertificate.getVersion();
    }

    @Override
    public BigInteger getSerialNumber() {
        return wrappedCertificate.getSerialNumber();
    }

    @Override
    public Principal getIssuerDN() {
        return wrappedCertificate.getIssuerDN();
    }

    @Override
    public Principal getSubjectDN() {
        return wrappedCertificate.getSubjectDN();
    }

    @Override
    public Date getNotBefore() {
        return wrappedCertificate.getNotBefore();
    }

    @Override
    public Date getNotAfter() {
        return wrappedCertificate.getNotAfter();
    }

    @Override
    public byte[] getTBSCertificate() throws CertificateEncodingException {
        return wrappedCertificate.getTBSCertificate();
    }

    @Override
    public byte[] getSignature() {
        return wrappedCertificate.getSignature();
    }

    @Override
    public String getSigAlgName() {
        return wrappedCertificate.getSigAlgName();
    }

    @Override
    public String getSigAlgOID() {
        return wrappedCertificate.getSigAlgOID();
    }

    @Override
    public byte[] getSigAlgParams() {
        return wrappedCertificate.getSigAlgParams();
    }

    @Override
    public boolean[] getIssuerUniqueID() {
        return wrappedCertificate.getIssuerUniqueID();
    }

    @Override
    public boolean[] getSubjectUniqueID() {
        return wrappedCertificate.getSubjectUniqueID();
    }

    @Override
    public boolean[] getKeyUsage() {
        return wrappedCertificate.getKeyUsage();
    }

    @Override
    public int getBasicConstraints() {
        return wrappedCertificate.getBasicConstraints();
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return wrappedCertificate.getEncoded();
    }

    @Override
    public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        wrappedCertificate.verify(key);
    }

    @Override
    public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        wrappedCertificate.verify(key, sigProvider);
    }

    @Override
    public String toString() {
        return wrappedCertificate.toString();
    }

    @Override
    public PublicKey getPublicKey() {
        return wrappedCertificate.getPublicKey();
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        return wrappedCertificate.hasUnsupportedCriticalExtension();
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        return wrappedCertificate.getCriticalExtensionOIDs();
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        return wrappedCertificate.getNonCriticalExtensionOIDs();
    }

    @Override
    public byte[] getExtensionValue(String oid) {
        return wrappedCertificate.getExtensionValue(oid);
    }

    public X509Certificate getWrappedCertificate() {
        return wrappedCertificate;
    }
}
