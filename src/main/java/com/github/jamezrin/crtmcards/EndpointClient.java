package com.github.jamezrin.crtmcards;

import com.github.jamezrin.crtmcards.exceptions.UnsuccessfulRequestException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.jamezrin.crtmcards.EndpointConstants.*;

public class EndpointClient {
    private CloseableHttpClient httpClient;
    private String viewState = null;

    public EndpointClient() {
        this(makeHttpClient(20000));
    }

    public EndpointClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;

        try {
            this.viewState = fetchViewState(httpClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpResponse connect(String cardPrefix, String cardNumber, boolean retryIfUnsuccessful) throws IOException, UnsuccessfulRequestException {
        EndpointConnector connector = new EndpointConnector(viewState, cardPrefix, cardNumber);

        try {
            return connector.connect(httpClient);
        } catch (UnsuccessfulRequestException e) {
            if (!retryIfUnsuccessful) {
                throw e;
            }
        }

        // It is possible that our view state is not valid
        // Just in case, it fetches a new one and tries again
        this.viewState = fetchViewState(httpClient);

        return connect(
                cardPrefix,
                cardNumber,
                false
        );
    }

    public HttpResponse connect(String cardPrefix, String cardNumber) throws IOException, UnsuccessfulRequestException {
        return connect(
                cardPrefix,
                cardNumber,
                true
        );
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public String getViewState() {
        return viewState;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setViewState(String viewState) {
        this.viewState = viewState;
    }

    public static String fetchViewState(HttpClient httpClient) throws IOException {
        HttpGet viewRequest = new HttpGet(CRTM_QUERY_URI);
        HttpResponse viewResponse = httpClient.execute(viewRequest);
        HttpEntity viewResponseEntity = viewResponse.getEntity();

        Document viewStateDocument = Jsoup.parse(
                viewResponseEntity.getContent(),
                StandardCharsets.UTF_8.name(),
                CRTM_BASE_URI
        );

        Element viewStateEl = viewStateDocument.getElementById("__VIEWSTATE");
        return viewStateEl.attr("value");
    }

    public static CloseableHttpClient makeHttpClient(int timeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .build();
        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .setUserAgent(CRTM_USER_AGENT)
                .build();
    }
}
