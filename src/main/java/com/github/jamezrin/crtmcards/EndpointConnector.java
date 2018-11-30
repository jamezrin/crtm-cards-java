package com.github.jamezrin.crtmcards;

import com.github.jamezrin.crtmcards.exceptions.UnsuccessfulRequestException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.github.jamezrin.crtmcards.EndpointConstants.CRTM_QUERY_URI;

public class EndpointConnector {
    private final String viewState;
    private final String cardPrefix;
    private final String cardNumber;

    public EndpointConnector(String viewState, String cardPrefix, String cardNumber) {
        this.viewState = viewState;
        this.cardPrefix = cardPrefix;
        this.cardNumber = cardNumber;
    }

    public HttpPost makeRequest()  {
        return buildQueryRequest(
                viewState,
                cardPrefix,
                cardNumber
        );
    }

    public HttpResponse connect(HttpClient httpClient) throws IOException, UnsuccessfulRequestException {
        HttpPost queryRequest = makeRequest();

        HttpResponse response = httpClient.execute(queryRequest);
        int statusCode = response.getStatusLine().getStatusCode();

        if (statusCode < 200 || statusCode > 299) {
            throw new UnsuccessfulRequestException(response);
        }

        return response;
    }

    public static HttpPost buildQueryRequest(String viewState, String cardPrefix, String cardNumber) {
        HttpPost httpPost = new HttpPost(CRTM_QUERY_URI);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("__SCROLLPOSITIONY", "530"));
        params.add(new BasicNameValuePair("__SCROLLPOSITIONX", "0"));
        params.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "761C03E9"));
        params.add(new BasicNameValuePair("__VIEWSTATE", viewState));
        params.add(new BasicNameValuePair("__EVENTTARGET", ""));
        params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));

        params.add(new BasicNameValuePair("ctl00$cntPh$dpdCodigoTTP", cardPrefix));
        params.add(new BasicNameValuePair("ctl00$cntPh$txtNumTTP", cardNumber));
        params.add(new BasicNameValuePair("ctl00$cntPh$btnConsultar", "Continuar"));
        httpPost.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

        return httpPost;
    }
}
