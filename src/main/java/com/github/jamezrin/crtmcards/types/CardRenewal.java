package com.github.jamezrin.crtmcards.types;

import java.time.LocalDate;

public class CardRenewal {
    private final LocalDate rechargeDate;
    private final LocalDate validityStartDate;
    private final LocalDate firstUseDate;
    private final LocalDate expirationDate;

    public CardRenewal(LocalDate rechargeDate, LocalDate validityStartDate, LocalDate firstUseDate, LocalDate expirationDate) {
        this.rechargeDate = rechargeDate;
        this.validityStartDate = validityStartDate;
        this.firstUseDate = firstUseDate;
        this.expirationDate = expirationDate;
    }

    public LocalDate getRechargeDate() {
        return rechargeDate;
    }

    public LocalDate getValidityStartDate() {
        return validityStartDate;
    }

    public LocalDate getFirstUseDate() {
        return firstUseDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return "CardRenewal{" +
                "rechargeDate=" + rechargeDate +
                ", validityStartDate=" + validityStartDate +
                ", firstUseDate=" + firstUseDate +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
