package com.github.jamezrin.crtmcards.types;

import java.time.LocalDate;

public class CardRenewal {
    private LocalDate rechargeDate;
    private LocalDate validityStartDate;
    private LocalDate firstUseDate;
    private LocalDate expirationDate;

    public CardRenewal() { }

    public CardRenewal(LocalDate rechargeDate, LocalDate validityStartDate, LocalDate firstUseDate, LocalDate expirationDate) {
        this.rechargeDate = rechargeDate;
        this.validityStartDate = validityStartDate;
        this.firstUseDate = firstUseDate;
        this.expirationDate = expirationDate;
    }

    public LocalDate getRechargeDate() {
        return rechargeDate;
    }

    public void setRechargeDate(LocalDate rechargeDate) {
        this.rechargeDate = rechargeDate;
    }

    public LocalDate getValidityStartDate() {
        return validityStartDate;
    }

    public void setValidityStartDate(LocalDate validityStartDate) {
        this.validityStartDate = validityStartDate;
    }

    public LocalDate getFirstUseDate() {
        return firstUseDate;
    }

    public void setFirstUseDate(LocalDate firstUseDate) {
        this.firstUseDate = firstUseDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
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
