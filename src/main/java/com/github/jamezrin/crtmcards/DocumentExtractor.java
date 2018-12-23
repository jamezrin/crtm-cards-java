package com.github.jamezrin.crtmcards;

import com.github.jamezrin.crtmcards.types.CrtmCard;
import com.github.jamezrin.crtmcards.types.CardRenewal;
import com.github.jamezrin.crtmcards.types.CardType;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentExtractor {
    public static final DateTimeFormatter CRTM_DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(Locale.forLanguageTag("es-ES"));
    public static final Pattern RENEWAL_DATE_PATTERN = Pattern.compile("^(?<type>[\\s\\S]+): (?<date>[0-9]{2}-[0-9]{2}-[0-9]{4})$");
    public static final Pattern PROFILE_DATE_PATTERN = Pattern.compile("^Perfil (?<type>[\\s\\S]+) caduca: (?<date>[0-9]{2}-[0-9]{2}-[0-9]{4})$");
    public static final Pattern PROFILE_LINE_PATTERN = Pattern.compile("<br>(\\s+)?");

    public static CrtmCard processCard(Document document) {
        // Full card number
        Element fullNumEl = document.getElementById("ctl00_cntPh_lblNumeroTarjeta");

        // Dates of renewals
        Element resultsTableEl = document.getElementById("ctl00_cntPh_tableResultados");
        Element resultRowEl = resultsTableEl.getElementsByTag("td").get(1);
        Elements resultsEls = resultRowEl.getElementsByTag("span");

        // CrtmCard expiration date
        Element expirationEl = document.getElementById("ctl00_cntPh_lblFechaCaducidadTarjeta");

        // CrtmCard relevant profiles w/ exp. dates
        Element profilesEl = document.getElementById("ctl00_cntPh_lblInfoDatosDeLaTarjeta");

        return new CrtmCard(
                fullNumEl.text(),
                resultsEls.get(0).text(),
                CardType.fromId(resultsEls.get(1).text()),

                new CardRenewal[]{
                        resultsEls.size() > 2 ? extractRenewal(resultsEls.subList(2, 6)) : null,
                        resultsEls.size() > 6 ? extractRenewal(resultsEls.subList(6, 10)) : null
                },

                extractSimpleDate(expirationEl.text()),
                extractProfiles(profilesEl.html())
        );
    }

    public static CardRenewal extractRenewal(List<Element> list) {
        return new CardRenewal(
                extractSimpleDate(list.get(0).text()),
                extractSimpleDate(list.get(1).text()),
                extractSimpleDate(list.get(2).text()),
                extractSimpleDate(list.get(3).text())
        );
    }

    public static LocalDate extractSimpleDate(String string) {
        if (string == null) {
            return null;
        }

        Matcher matcher = RENEWAL_DATE_PATTERN.matcher(string);

        if (!matcher.find()) {
            return null;
        }

        String dateString = matcher.group("date");

        return LocalDate.parse(dateString, CRTM_DATE_FORMAT);
    }

    public static EnumMap<CardType, LocalDate> extractProfiles(String string) {
        EnumMap<CardType, LocalDate> map = new EnumMap<>(CardType.class);
        String[] parts = PROFILE_LINE_PATTERN.split(string);

        for (String part : parts) {
            Matcher matcher = PROFILE_DATE_PATTERN.matcher(part);

            if (!matcher.find()) {
                continue;
            }

            String typeString = matcher.group("type");
            String dateString = matcher.group("date");

            CardType type = CardType.fromId(typeString);
            LocalDate date = LocalDate.parse(dateString, CRTM_DATE_FORMAT);

            if (type == null) {
                continue;
            }

            map.put(type, date);
        }

        return map;
    }
}
