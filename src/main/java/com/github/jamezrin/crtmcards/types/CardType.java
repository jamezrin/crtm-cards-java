package com.github.jamezrin.crtmcards.types;

public enum CardType {
    NORMAL("Normal"),
    YOUTH("Joven"),
    BLUE("Tarjeta Azul"),
    SENIOR("Tercera Edad"),
    CHILD("Infantil"), // not sure
    UNDEFINED("");

    private final String id;

    CardType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CardType{" +
                "id='" + id + '\'' +
                '}';
    }

    public static CardType fromId(String type) {
        for (CardType card : CardType.values()) {
            if (card.getId().equals(type)) {
                return card;
            }
        }

        return null;
    }
}
