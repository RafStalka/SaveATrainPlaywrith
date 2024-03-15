package pages;

import com.microsoft.playwright.Page;

public class ResultsPage {
    private static final String RESULT_1_ID_SELECTOR = "id=result-1";
    private static final String PROCEED_BUTTON_SELECTOR = ".proceed-btn";
    private static final String DEPARTURE_STATION = "xpath=//*[@class='direction-from']";
    private static final String ARRIVAL_STATION = "xpath=//*[@class='direction-to']";
    private static final String PRICE_1_ID_SELECTOR = "id=price-1";
    private static final String DEPARTURE_DATE_1_ID_SELECTOR = "id=departure-d-1";
    private static final String DEPARTURE_TIME_1_ID_SELECTOR = "id=departure-t-1";
    private static final String ARRIVAL_DATE_1_ID_SELECTOR = "id=arrival-d-1";
    private static final String ARRIVAL_TIME_1_ID_SELECTOR = "id=arrival-t-1";
    private static final String DURATION_TIME_ID = "id=duration-1";
    private static final String SELECT_TEXT = "Select";
    private static final String PROCEED_TEXT = "Proceed";
    private static final String FARE_XPATH = "xpath=//*[@id='selected-fare-1']/span/span[2]";

    private final Page page;

    public ResultsPage(Page page) {
        this.page = page;
    }

    public void selectFirstOption() {
        page.click(RESULT_1_ID_SELECTOR);
    }

    public void selectFirstOptionOnEurailPass() {
        page.click("id=pass-1");
    }

    public void proceed() {
        page.click(PROCEED_BUTTON_SELECTOR);
    }

    public void selectSecondSideConnection() {
        page.locator(RESULT_1_ID_SELECTOR).getByText(SELECT_TEXT).click();
    }

    public void proceedSecondSideConnection() {
        page.getByText(PROCEED_TEXT).click();
    }

    public String getDepartureStation_ResultPage() {
        return getTextContent(DEPARTURE_STATION);
    }

    public String getArrivalStation_ResultPage() {
        return getTextContent(ARRIVAL_STATION);
    }

    public String getPriceFirstResult_ResultPage() {
        return getTextContent(PRICE_1_ID_SELECTOR);
    }

    public String getFirstResultDepartureDate_ResultPage() {
        return getTextContent(DEPARTURE_DATE_1_ID_SELECTOR);
    }

    public String getFirstResultDepartureTime_ResultPage() {
        return getTextContent(DEPARTURE_TIME_1_ID_SELECTOR).strip();
    }

    public String getFirstResultArrivalDate_ResultPage() {
        return getTextContent(ARRIVAL_DATE_1_ID_SELECTOR);
    }

    public String getFirstResultArrivalTime_ResultPage() {
        return getTextContent(ARRIVAL_TIME_1_ID_SELECTOR);
    }

    public String getDurationTime_ResultPage() {
        return getTextContent(DURATION_TIME_ID);
    }

    public String getFere_ResultPage() {
        return getTextContent(FARE_XPATH);
    }

    private String getTextContent(String cssSelector) {
        return page.locator(cssSelector).textContent().strip();
    }

}
