package pages;

import com.microsoft.playwright.Page;

public class ResultsPage {
    private static final String RESULT_1_ID_SELECTOR = "id=result-1";
    private static final String PROCEED_BUTTON_SELECTOR = ".proceed-btn";
    private static final String DEPARTURE_STATION_ID = "id=departure-transfer-station-name-1-1";
    private static final String ARRIVAL_STATION_ID = "id=arrival-transfer-station-name-1-1";
    private static final String PRICE_1_ID_SELECTOR = "id=price-1";
    private static final String DEPARTURE_DATE_1_ID_SELECTOR = "id=departure-d-1";
    private static final String DEPARTURE_TIME_1_ID_SELECTOR = "id=departure-t-1";
    private static final String ARRIVAL_DATE_1_ID_SELECTOR = "id=arrival-d-1";
    private static final String ARRIVAL_TIME_1_ID_SELECTOR = "id=arrival-t-1";
    private static final String DURATION_TIME_XPATH = "xpath=//sat-result-item[@id='result-01']/div/div/div/div[2]/div[2]/p[1]";
    private static final String SELECT_TEXT = "Select";
    private static final String PROCEED_TEXT = "Proceed";

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
        return getTextContent(DEPARTURE_STATION_ID);
    }

    public String getArrivalStation_ResultPage() {
        return getTextContent(ARRIVAL_STATION_ID);
    }

    public String getPriceFirstResult_ResultPage() {
        return getTextContent(PRICE_1_ID_SELECTOR);
    }

    public String getFirstResultDepartureDate_ResultPage() {
        return getTextContent(DEPARTURE_DATE_1_ID_SELECTOR);
    }

    public String getFirstResultDepartureTime_ResultPage() {
        return getTextContent(DEPARTURE_TIME_1_ID_SELECTOR);
    }

    public String getFirstResultArrivalDate_ResultPage() {
        return getTextContent(ARRIVAL_DATE_1_ID_SELECTOR);
    }

    public String getFirstResultArrivalTime_ResultPage() {
        return getTextContent(ARRIVAL_TIME_1_ID_SELECTOR);
    }

    public String getDurationTime_ResultPage() {
        return getTextContent(DURATION_TIME_XPATH);
    }

    private String getTextContent(String cssSelector) {
        return page.locator(cssSelector).textContent().trim();
    }

}
