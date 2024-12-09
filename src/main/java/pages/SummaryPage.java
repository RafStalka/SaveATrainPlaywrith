package pages;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SummaryPage {
    private static final String ADYEN_CARD_NUMBER = "xpath=//input[@data-fieldtype='encryptedCardNumber']";
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

        Locator cardNumber = page.locator("iframe[title=\"Iframe for secured card number\"]").contentFrame().getByPlaceholder("5678 9012 3456");
        cardNumber.click();
        cardNumber.type("5577 0000 5577 0004");
        Locator expiryDate = page.locator("iframe[title=\"Iframe for secured card expiry date\"]").contentFrame().getByPlaceholder("MM/YY");
        expiryDate.click();
        expiryDate.type("03/30");
        Locator cvcNumber = page.locator("iframe[title=\"Iframe for secured card security code\"]").contentFrame().getByPlaceholder("digits");
        cvcNumber.click();
        cvcNumber.type("737");
        Locator cardOwner = page.getByPlaceholder("J. Smith");
        cardOwner.click();
        cardOwner.type("Customer");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Pay").setExact(true)).click();

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
