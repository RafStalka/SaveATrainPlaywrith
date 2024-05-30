package pages;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SummaryPage {
    private static final String ADYEN_CARD_NUMBER = "xpath=//*[@data-fieldtype='encryptedCardNumber']";
    private static final String ADYEN_EXPIRY_DATE = "xpath=//*[@data-fieldtype='encryptedExpiryDate']";
    private static final String ADYEN_FORM_SC = "xpath=//*[@data-fieldtype='encryptedSecurityCode']";
    private static final String ADYEN_CHECKOUT = ".adyen-checkout__input--text";
    private static final String ADYEN_BUTTON_CONTENT = ".adyen-checkout__button__content";
    private static final String SUCCESS_URL = "css=.success-url > p:nth-child(3)";
    private static final String SUMMARY_PAGE_EMAIL = "css=.order-email > .property-value";
    private final Page page;

    public SummaryPage(Page page) {
        this.page = page;
    }

    public void completingAdyenForm() {
        enterPaymentDetailsInFrame(1, ADYEN_CARD_NUMBER, "5577 0000 5577 0004");
        enterPaymentDetailsInFrame(2, ADYEN_EXPIRY_DATE, "03/30");
        enterPaymentDetailsInFrame(3, ADYEN_FORM_SC, "737");

        page.locator(ADYEN_CHECKOUT).click();
        page.locator(ADYEN_CHECKOUT).fill("Customer");

        // Click on pay button
        page.locator(ADYEN_BUTTON_CONTENT).click();
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
        return getTextContent(SUCCESS_URL);
    }

    public String getSummaryPageEmail() {
        return getTextContent(SUMMARY_PAGE_EMAIL);
    }

    public String getDepartureStation_SummaryPage() {
        return getTextContent("css=.trip-route > span:nth-child(1)");
    }

    public String getArrivalStation_SummaryPage() {
        return getTextContent("css=.trip-route > span:nth-child(3)");
    }

    public String getDepartureDate_SummaryPage() {
        return getTextContent("css=.property-value > span:nth-child(1)");
    }

    public String getArrivalDate_SummaryPage() {
        return getTextContent("css=.property-value > span:nth-child(2)");
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

    public String getPassengerSurname_SummaryPage() {
        return getTextContent("css=.ng-star-inserted > p > span:nth-child(3)");
    }

    public String getPassengerBirthDate() {
        return getTextContent("css=.property-value:nth-child(4)");
    }

    public String getPassengerEmail() {
        return getTextContent("xpath=//*[@class='order-email']/span[2]");
    }

    private String getTextContent(String cssSelector) {
        return page.locator(cssSelector).textContent().strip();
    }
}
