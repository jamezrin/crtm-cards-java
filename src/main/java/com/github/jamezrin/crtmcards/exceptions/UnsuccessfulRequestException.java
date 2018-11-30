package com.github.jamezrin.crtmcards.exceptions;

import org.apache.http.HttpResponse;

public class UnsuccessfulRequestException extends ScraperException {
    private final HttpResponse response;

    public UnsuccessfulRequestException(HttpResponse response) {
        super(response.getStatusLine().toString());
        this.response = response;
    }

    public HttpResponse getResponse() {
        return response;
    }
}
