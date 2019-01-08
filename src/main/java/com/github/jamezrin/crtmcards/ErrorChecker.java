package com.github.jamezrin.crtmcards;

import com.github.jamezrin.crtmcards.exceptions.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorChecker {
    public static final Pattern FORMAT_ERROR_PATTERN = Pattern.compile("^javascript:alert\\(\'(?<message>Error. [\\s\\S]+)\'\\);$");

    public static void checkForErrors(Document document) throws ScraperException {
        checkForErrorLabel(document.getElementById("ctl00_cntPh_lblError"));
        checkForErrorScript(document.getElementsByTag("script"));
    }

    public static void checkForErrorLabel(Element element) throws ScraperException {
        if (element != null) {
            String content = element.text();
            if (!content.isEmpty()) {
                switch (content) {
                    case "POR FAVOR, INTRODUZCA DE NUEVO SU TARJETA": // get these strings in the exceptions or map
                    case "DISCULPE LA MOLESTIAS. NO TENEMOS RESULTADOS PARA SUS PARAMETROS DE BUSQUEDA":
                        throw new NotExistentCardNumberException(content);
                    case "TARJETA NO ACTIVA":
                        throw new InactiveCardNumberException(content);
                    case "En estos momentos no es posible realizar la consulta. Disculpe las molestias.":
                        throw new CurrentlyUnavailableException(content);
                    default:
                        throw new ScraperException(content);
                }
            }
        }
    }

    public static void checkForErrorScript(Elements elements) throws ScraperException {
        if (elements != null) {
            for (Element element : elements) {
                Matcher matcher = FORMAT_ERROR_PATTERN.matcher(element.html());

                if (!matcher.find()) {
                    continue;
                }

                String content = matcher.group("message");

                throw new InvalidCardNumberException(content);
            }
        }
    }
}
