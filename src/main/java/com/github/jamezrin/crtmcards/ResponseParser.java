package com.github.jamezrin.crtmcards;

import com.github.jamezrin.crtmcards.exceptions.ScraperException;
import com.github.jamezrin.crtmcards.types.CrtmCard;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static com.github.jamezrin.crtmcards.EndpointConstants.CRTM_BASE_URI;

public class ResponseParser {
    private final InputStream inputStream;
    private final Charset charset;
    private final Document document;

    public ResponseParser(InputStream inputStream, Charset charset) throws ScraperException {
        this.inputStream = inputStream;
        this.charset = charset;

        try {
            document = Jsoup.parse(
                    inputStream,
                    charset.name(),
                    CRTM_BASE_URI
            );
        } catch (IOException e) {
            throw new ScraperException("Could not parse the data returned by the endpoint", e);
        }
    }

    public ResponseParser(HttpResponse response) throws IOException, ScraperException {
        this(
                response.getEntity().getContent(),
                ContentType.getOrDefault(response.getEntity()).getCharset()
        );
    }

    public CrtmCard parse() throws ScraperException {
        // Runs all checks and throws exceptions accordingly
        ErrorChecker.checkForErrors(document);

        // Extracts properties and creates CrtmCard
        return DocumentExtractor.processCard(document);
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Charset getCharset() {
        return charset;
    }

    public Document getDocument() {
        return document;
    }
}
