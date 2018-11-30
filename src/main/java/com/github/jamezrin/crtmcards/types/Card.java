package com.github.jamezrin.crtmcards.types;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;

public class Card {
    private final String fullNum;
    private final String title;
    private final CardType type;

    private final CardRenewal[] renewals;

    private final LocalDate expirationDate;
    private final EnumMap<CardType, LocalDate> profiles;

    public Card(String fullNum, String title, CardType type, CardRenewal[] renewals, LocalDate expirationDate, EnumMap<CardType, LocalDate> profiles) {
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

    public String getTitle() {
        return title;
    }

    public CardType getType() {
        return type;
    }

    public CardRenewal[] getRenewals() {
        return renewals;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public EnumMap<CardType, LocalDate> getProfiles() {
        return profiles;
    }

    @Override
    public String toString() {
        return "Card{" +
                "fullNum='" + fullNum + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", renewals=" + Arrays.toString(renewals) +
                ", expirationDate=" + expirationDate +
                ", profiles=" + profiles +
                '}';
    }
}
