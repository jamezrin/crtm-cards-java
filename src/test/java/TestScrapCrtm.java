import com.github.jamezrin.crtmcards.EndpointClient;
import com.github.jamezrin.crtmcards.ResponseParser;
import com.github.jamezrin.crtmcards.exceptions.CurrentlyUnavailableException;
import com.github.jamezrin.crtmcards.exceptions.InactiveCardNumberException;
import com.github.jamezrin.crtmcards.exceptions.InvalidCardNumberException;
import com.github.jamezrin.crtmcards.types.CrtmCard;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class TestScrapCrtm {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    public void testValidCard() throws Exception {
        String validCardPrefix = System.getenv("CRTM_VALID_CARD_PREFIX");
        String validCardNumber = System.getenv("CRTM_VALID_CARD_NUMBER");
        assumeTrue(validCardPrefix != null && validCardNumber != null);

        try {
            HttpResponse response = endpointClient.connect(
                    validCardPrefix,
                    validCardNumber
            );

            ResponseParser parser = new ResponseParser(response);
            CrtmCard card = parser.parse();

            assertNotNull(card);
        } catch (CurrentlyUnavailableException | SocketTimeoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInactiveCard() throws Exception {
        try {
            HttpResponse response = endpointClient.connect(
                    "001",
                    "0000023768"
            );

            assertThrows(InactiveCardNumberException.class,
                    () -> {
                        try {
                            new ResponseParser(response).parse();
                        } catch (CurrentlyUnavailableException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalidCard1() throws Exception {
        try {
            HttpResponse response = endpointClient.connect(
                    "251",
                    "90909090"
            );

            assertThrows(InvalidCardNumberException.class,
                    () -> new ResponseParser(response).parse()
            );
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInvalidCard2() throws Exception {
        try {
            HttpResponse response = endpointClient.connect(
                    "1337",
                    "9999999999"
            );

            assertThrows(InvalidCardNumberException.class,
                    () -> new ResponseParser(response).parse()
            );
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFetchViewState() throws Exception {
        String viewState = EndpointClient.fetchViewState(
                endpointClient.getHttpClient()
        );

        assertNotNull(viewState);
    }

    @Test
    public void testConnectInactiveCard() throws Exception {
        HttpResponse response = endpointClient.connect(
                "001",
                "0000023768"
        );

        assertNotNull(response);
    }

    @Test
    public void testParseInactiveCard() throws Exception {
        HttpResponse response = endpointClient.connect(
                "001",
                "0000023768"
        );

        ResponseParser parser = new ResponseParser(response);

        assertNotNull(parser.getDocument());
    }
}
