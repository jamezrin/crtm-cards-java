package com.github.jamezrin.crtmcards.types;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;

public class CrtmCard {
    private String fullNum;
    private String title;
    private CardType type;
    private List<CardRenewal> renewals;
    private LocalDate expirationDate;
    private EnumMap<CardType, LocalDate> profiles;

    public CrtmCard() { }

    public CrtmCard(String fullNum, String title, CardType type, List<CardRenewal> renewals, LocalDate expirationDate, EnumMap<CardType, LocalDate> profiles) {
        this.fullNum = fullNum;
        this.title = title;
        this.type = type;
        this.renewals = renewals;
        this.expirationDate = expirationDate;
        this.profiles = profiles;
    }

    public String getFullNum() {
        return fullNum;
    }

    public void setFullNum(String fullNum) {
        this.fullNum = fullNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public List<CardRenewal>  getRenewals() {
        return renewals;
    }

    public void setRenewals(List<CardRenewal>  renewals) {
        this.renewals = renewals;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public EnumMap<CardType, LocalDate> getProfiles() {
        return profiles;
    }

    public void setProfiles(EnumMap<CardType, LocalDate> profiles) {
        this.profiles = profiles;
    }

    @Override
    public String toString() {
        return "CrtmCard{" +
                "fullNum='" + fullNum + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", renewals=" + renewals +
                ", expirationDate=" + expirationDate +
                ", profiles=" + profiles +
                '}';
    }
}
