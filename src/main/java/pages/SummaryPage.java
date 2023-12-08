package pages;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.concurrent.TimeoutException;

public class SummaryPage {
    private final Page page;

    public SummaryPage(Page page) {
        this.page = page;
    }

    public void completingAdyenForm() {
        enterPaymentDetailsInFrame(1, "id=encryptedCardNumber", "5577 0000 5577 0004");
        enterPaymentDetailsInFrame(2, "id=encryptedExpiryDate", "03/30");
        enterPaymentDetailsInFrame(3, "id=encryptedSecurityCode", "737");

        page.locator(".adyen-checkout__input--text").click();
        page.locator(".adyen-checkout__input--text").fill("Customer");

        // Click on pay button
        page.locator(".adyen-checkout__button__content").click();

        // Sleep for 30 seconds
        page.waitForTimeout(45000);

        // directly continue if the element becomes visible before the 30 seconds timeout
        if (!page.isVisible("css=h3")) {
            try {
                throw new TimeoutException("Element not found");
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void enterPaymentDetailsInFrame(int frameIndex, String locatorId, String value) {
        Frame frame = page.frames().get(frameIndex);
        Locator inputField = frame.locator(locatorId).first();
        inputField.click();
        inputField.type(value);
        // Switch back to the main frame
        page.mainFrame();
    }

    public String getConfirmationCode() {
        return getTextContent("css=.success-url > p:nth-child(3)");
    }

    public String getSummaryPageEmail() {
        return getTextContent("css=.order-email > .property-value");
    }

    public String getDepartureStation_SummaryPage() {
        return getTextContent("css=.trip-route > span:nth-child(3)");
    }

    public String getArrivalStation_SummaryPage() {
        return getTextContent("css=.trip-route > span:nth-child(1)");
    }

    public String getDepartureDate_SummaryPage() {
        return getTextContent("css=.property-value > span:nth-child(2)");
    }

    public String getArrivalDate_SummaryPage() {
        return getTextContent("css=.property-value > span:nth-child(1)");
    }

    public String getFare() {
        return getTextContent("css=.fare-data > .property-value");
    }

    public String getOrderCode() {
        return getTextContent("css=.order-code > .property-value");
    }

    public String getPrice_SummaryPage() {
        return getTextContent("css=.price-value");
    }

    public String getReservationPrice_SummaryPage() {
        return getTextContent("css=.outbound-seat-fee-value");
    }

    public String getFinalPrice_SummaryPage() {
        return getTextContent("css=.total-discounted-price-value");
    }

    public String getPassengerName_SummaryPage() {
        return getTextContent("css=.ng-star-inserted > p > span:nth-child(2)");
    }

    public String getPassengerNameFirst_SummaryPage() {
        return getTextContent("css=.ng-star-inserted > p > span:nth-child(3)");
    }

    public String getPassengerBirthDate() {
        return getTextContent("css=.property-value:nth-child(4)");
    }

    private String getTextContent(String cssSelector) {
        return page.locator(cssSelector).textContent().trim();
    }
}
