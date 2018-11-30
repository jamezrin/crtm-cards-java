import com.github.jamezrin.crtmcards.DocumentExtractor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestDateParsing {
    @Test
    public void testOkDate1Simple() {
        String string = "Caduca: 05-05-2025";
        LocalDate date = DocumentExtractor.extractSimpleDate(string);
        assertEquals(5, date.getDayOfMonth());
        assertEquals(5, date.getMonthValue());
        assertEquals(2025, date.getYear());
    }

    @Test
    public void testOkDate2Simple() {
        String string = "Caduca: 05-10-2025";
        LocalDate date = DocumentExtractor.extractSimpleDate(string);
        assertEquals(5, date.getDayOfMonth());
        assertEquals(10, date.getMonthValue());
        assertEquals(2025, date.getYear());
    }

    @Test
    public void testEmptyDateSimple() {
        String string = "Caduca:";
        LocalDate date = DocumentExtractor.extractSimpleDate(string);
        assertNull(date);
    }

    @Test
    public void testNoDateSimple() {
        String string = "";
        LocalDate date = DocumentExtractor.extractSimpleDate(string);
        assertNull(date);
    }

    @Test
    public void testInvalidDateSimple() {
        String string = "Caduca: 2010/10/10";
        LocalDate date = DocumentExtractor.extractSimpleDate(string);
        assertNull(date);
    }
}
