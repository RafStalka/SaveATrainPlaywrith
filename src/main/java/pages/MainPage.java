package pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import constans.AppConstants;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The MainPage class provides methods to interact with the main page.
 */
public class MainPage {

    private static final String PRODUCTS_SELECTOR = "id=products";
    private static final String RAIL_AFFILIATE_SELECTOR = "id=rail-affiliate";
    private static final String MANAGE_BOOKING_SELECTOR = "id=manage-booking";
    private static final String HELP_DROPDOWN_SELECTOR = "id=help-dropdown";
    private static final String FAQ_SELECTOR = "id=faq";
    private static final String ABOUT_US_SELECTOR = "id=about-us";
    private static final String EURAIL_PASS_SELECTOR = "id=eurail-pass";
    private static final String CONTACT_US_SELECTOR = "id=contact-us";
    private static final String MAIN_PAGE_TITLE = "page title: ";
    private static final String MAIN_PAGE_URL = "page url : ";
    private static final String PRODUCT_TAB_ELEMENTS_COUNT = "Number of products tab elements: ";
    private static final String NUM_HELP_TAB_ELEMENTS = "Number of help tab elements: ";
    private static final String MAIN_FORM_YOUTH_PLUS_PATH = "#main-form-youth-plus path";
    private static final String SHOW_ALL_PASSENGER_TYPES = "Show All Passenger Types";
    private static final String RAIL_AGENT_DASHBOARD_SELECTOR = "id=rail-agent-dashboard";
    private static final String RAILS_BOOK_API_SELECTOR = "id=rails-book-api";
    private static final String RAIL_ENRICHMENT_SELECTOR = "id=rail-enrichment";
    private static final String RAIL_FORWARD_API_SELECTOR = "id=rail-forward-api";
    private static final String RAIL_SEARCH_API_SELECTOR = "id=rail-search-api";
    private static final String WHITE_LABEL_SELECTOR = "id=white-label";
    private static final String PRIVACY_POLICY_SELECTOR = "a:nth-of-type(1) > p";
    private static final String TERMS_AND_CONDITIONS_SELECTOR = "a:nth-of-type(2) > p";
    private static final String ORIGIN_INPUT_SELECTOR = ".input-control-container > .origin";
    private static final String ORIGIN_STATION_SELECTOR = ".origin .ng-star-inserted:nth-child(1)";
    private static final String ACTION_BOX_SELECTOR = ".action-box";
    private static final String DESTINATION_INPUT_SELECTOR = ".destination:nth-child(1)";
    private static final String DESTINATION_STATION_SELECTOR = ".destination .ng-star-inserted:nth-child(1)";
    private static final String SEARCH_BUTTON_SELECTOR = ".search-btn";
    private static final String PASS_BOOKING_TAB_SELECTOR = "id=pass-booking-tab";
    private static final String COUNTRY_INPUT_SELECTOR = ".input-group:nth-child(2) .input-control";
    private static final String COUNTRY_LIST_SELECTOR = ".items-list > .ng-star-inserted";
    private static final String AUTOCOMPLETE_INPUT_SELECTOR = ".ng-invalid > .autocomplete-container .input-control";
    private static final String RESIDENCE_INPUT_SELECTOR = ".pass-booking-content-form";
    private static final String RESIDENCE_LIST_SELECTOR = ".input-group:nth-child(3) .input-control";
    private static final String EXCHANGE_BUTTON_SELECTOR = "//i[contains(@class, 'md-36')]";
    private static final String DEPARTURE_DATE_SELECTOR = "id=main-form-departure-date";
    private static final String NOT_MATCH_THE_EXPECTED_DATE = "The departure date filled in the input field does not match the expected date.";
    private static final String LANGUAGE_CURRENCY_SELECTOR = "#language-currency a";
    private static final String LANGUAGE_SELECTOR = "id=language";
    private static final String DEPARTURE_DATE_XPATH = "//*[@aria-label='%s']";
    private static final String FIELD_DOES_NOT_MATCH_THE_EXPECTED_DATE = "The return date filled in the input field does not match the expected date.";
    private static final String FIND_MY_PASS_BUTTON_SELECTOR = "css=.btn > p";
    private static final String FIND_TICKET_BUTTON = "button[name='button']";
    private static final String ADULT_PLUS_BUTTON = "#main-form-adult-plus svg";
    private static final String RESIDENCE_COUNTRY = "Pakistan";

    public Page page;

    public MainPage(Page page) {
        this.page = page;
    }

    public String getMainPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        String title = page.title();
        System.out.println(MAIN_PAGE_TITLE + title);
        return title;
    }

    public String getMainPageURL() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.waitForTimeout(2000);
        String url = page.url();
        System.out.println(MAIN_PAGE_URL + url);
        return url;
    }

    public int productListSizeChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // Find the parent element containing the list of products tab elements
        Locator parentElement = page.locator("#products-dropdown");
        // Find all products elements within the parent element and convert to an array
        ElementHandle[] liElements = parentElement.locator("li").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        int listSize = liElements.length;
        System.out.println(PRODUCT_TAB_ELEMENTS_COUNT + listSize);
        return listSize;
    }

    public int helpListSizeChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // Find the parent element containing the list of help tab elements
        Locator parentElement = page.locator("#help-dropdown");
        // Find all help tab elements within the parent element and convert to an array
        ElementHandle[] liElements = parentElement.locator("li").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        int listSize = liElements.length;
        System.out.println(NUM_HELP_TAB_ELEMENTS + listSize);
        return listSize;
    }

    public int currencyListSizeChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.locator(LANGUAGE_CURRENCY_SELECTOR).click();
        // Find the parent element containing the list of currency elements
        Locator parentElement = page.locator("id=currency");

        // Find all currency elements within the parent element and convert to an array
        ElementHandle[] currencyElements = parentElement.locator("option").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        // Check the size of the list
        int listSize = currencyElements.length;
        System.out.println(NUM_HELP_TAB_ELEMENTS + listSize);
        return listSize;
    }

    public int languageListSizeChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.locator(LANGUAGE_CURRENCY_SELECTOR).click();
        Locator parentElement = page.locator(LANGUAGE_SELECTOR);

        // Find all currency elements within the parent element and convert to an array
        ElementHandle[] languageElements = parentElement.locator("option").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        // Check the size of the list
        int listSize = languageElements.length;
        System.out.println(NUM_HELP_TAB_ELEMENTS + listSize);
        return listSize;
    }

    public void navigateToHomePage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
    }

    public void complementingTheOriginStations(String origin) {
        page.click(ORIGIN_INPUT_SELECTOR);
        page.type(ORIGIN_INPUT_SELECTOR, origin);
        page.waitForSelector(ORIGIN_STATION_SELECTOR);
        page.click(ORIGIN_STATION_SELECTOR);
        page.hover(ACTION_BOX_SELECTOR);
    }

    public void complementingTheDestinationStations(String destination) {
        page.click(DESTINATION_INPUT_SELECTOR);
        page.type(DESTINATION_INPUT_SELECTOR, destination);
        page.waitForSelector(DESTINATION_STATION_SELECTOR);
        page.click(DESTINATION_STATION_SELECTOR);
    }

    public void performSearch() {
        page.click(SEARCH_BUTTON_SELECTOR);
    }

    public void passBookingTabClick() {
        page.click(PASS_BOOKING_TAB_SELECTOR);
    }

    public void fillCountryToVisit(String countryToVisit) {
        Locator inputElement = page.locator(COUNTRY_INPUT_SELECTOR);
        inputElement.click();
        inputElement.fill(countryToVisit);
        Locator itemsListElement = page.locator(COUNTRY_LIST_SELECTOR);
        itemsListElement.hover();
        itemsListElement.click();
        Locator invalidElement = page.locator(AUTOCOMPLETE_INPUT_SELECTOR);
        invalidElement.hover();
    }

    public void fillCountryOfResidence() {
        Locator formElement = page.locator(RESIDENCE_INPUT_SELECTOR);
        formElement.click();
        Locator inputElement2 = page.locator(RESIDENCE_LIST_SELECTOR);
        inputElement2.type(RESIDENCE_COUNTRY);
        formElement.click();
    }

    public void clickExchangeButton() {
        page.locator(EXCHANGE_BUTTON_SELECTOR).click();
    }

    public void departureDate() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = tomorrow.format(dateFormat);
        //page.fill("id=main-form-departure-date", formattedDate);
        page.click(DEPARTURE_DATE_SELECTOR);
        String xpathChoosingDate = String.format(DEPARTURE_DATE_XPATH, formattedDate);
        page.click(xpathChoosingDate);
        String actualDate = page.inputValue(DEPARTURE_DATE_SELECTOR);
        Assertions.assertEquals(formattedDate, actualDate, NOT_MATCH_THE_EXPECTED_DATE);
    }

    public void returnDate() {
        LocalDate tomorrow = LocalDate.now().plusDays(2);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = tomorrow.format(dateFormat);
        //page.fill("id=main-form-return-date", formattedDate);
        page.click("id=main-form-return-date");
        String xpathChoosingDate = String.format(DEPARTURE_DATE_XPATH, formattedDate);
        page.click(xpathChoosingDate);
        String actualDate = page.inputValue("id=main-form-return-date");
        Assertions.assertEquals(formattedDate, actualDate, FIELD_DOES_NOT_MATCH_THE_EXPECTED_DATE);
    }

    public void findMyTicketButtonClick() {
        page.click(FIND_TICKET_BUTTON);
    }

    public void findMyPassButtonClick() {
        page.click(FIND_MY_PASS_BUTTON_SELECTOR);
    }

    public void addOneAdultPassenger() {
        page.locator(ADULT_PLUS_BUTTON).click();
    }

    public void addOneYouthPassengerIn_17_YearsAge(String youthAge) {
        page.getByText(SHOW_ALL_PASSENGER_TYPES).click();
        page.locator(MAIN_FORM_YOUTH_PLUS_PATH).click();
        page.getByRole(AriaRole.SPINBUTTON).click();
        page.getByRole(AriaRole.SPINBUTTON).fill(youthAge);
    }

    public ContactUsPage navigateToContactUsPage() {
        navigateToHomePage();
        page.click(CONTACT_US_SELECTOR);
        return new ContactUsPage(page);
    }

    public EurailProductPage navigateToEurailProductPage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        page.click(EURAIL_PASS_SELECTOR);
        return new EurailProductPage(page);
    }

    public AboutUsPage navigateToAboutUsPage() {
        navigateToHomePage();
        page.click(ABOUT_US_SELECTOR);
        return new AboutUsPage(page);
    }

    public FAQHelpTabPage navigateToFAQPage() {
        navigateToHomePage();
        page.click(HELP_DROPDOWN_SELECTOR);
        page.click(FAQ_SELECTOR);
        return new FAQHelpTabPage(page);
    }

    public ManageBookingsHelpTabPage navigateToManageBookingsHelpTabPage() {
        navigateToHomePage();
        page.click(HELP_DROPDOWN_SELECTOR);
        page.click(MANAGE_BOOKING_SELECTOR);
        return new ManageBookingsHelpTabPage(page);
    }

    public RailAffiliateProductPage navigateToRailAffiliateProductPage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        page.click(RAIL_AFFILIATE_SELECTOR);
        return new RailAffiliateProductPage(page);
    }

    public RailAgentDashboardProductPage navigateToRailAgentDashboardProductPage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        page.click(RAIL_AGENT_DASHBOARD_SELECTOR);
        return new RailAgentDashboardProductPage(page);
    }

    public RailBookApiProductPage navigateToRailBookApiProductPage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        page.click(RAILS_BOOK_API_SELECTOR);
        return new RailBookApiProductPage(page);
    }

    public RailEnrichmentAPIProductPage navigateToRailEnrichmentAPIProductPage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        page.click(RAIL_ENRICHMENT_SELECTOR);
        return new RailEnrichmentAPIProductPage(page);
    }

    public RailForwardApiPage navigateToRailForwardApiPagePage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        page.click(RAIL_FORWARD_API_SELECTOR);
        return new RailForwardApiPage(page);
    }

    public RailSearchApiProductPage navigateToRailSearchApiProductPage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        page.click(RAIL_SEARCH_API_SELECTOR);
        return new RailSearchApiProductPage(page);
    }

    public WhiteLabelProductPage navigateToWhiteLabelProductPage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        page.click(WHITE_LABEL_SELECTOR);
        return new WhiteLabelProductPage(page);
    }

    public ProductsPage navigateToProductsPage() {
        navigateToHomePage();
        page.click(PRODUCTS_SELECTOR);
        return new ProductsPage(page);
    }

    public PrivacyPolicyPage navigateToPrivacyPolicyPage() {
        navigateToHomePage();
        page.click(PRIVACY_POLICY_SELECTOR);
        return new PrivacyPolicyPage(page);
    }

    public TermsAndConditionsPage navigateToTermsAndConditionsPage() {
        navigateToHomePage();
        page.click(TERMS_AND_CONDITIONS_SELECTOR);
        return new TermsAndConditionsPage(page);
    }
}
