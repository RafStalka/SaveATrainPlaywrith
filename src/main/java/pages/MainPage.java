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
    public static final String PRODUCTS_SELECTOR = "id=products";
    public static final String RAIL_AFFILIATE_SELECTOR = "id=rail-affiliate";
    public static final String MANAGE_BOOKING_SELECTOR = "id=manage-booking";
    public static final String HELP_DROPDOWN_SELECTOR = "id=help-dropdown";
    public static final String FAQ_SELECTOR = "id=faq";
    public static final String ABOUT_US_SELECTOR = "id=about-us";
    public static final String EURAIL_PASS_SELECTOR = "id=eurail-pass";
    public static final String CONTACT_US_SELECTOR = "id=contact-us";
    public static final String MAIN_PAGE_TITLE = "page title: ";
    public static final String MAIN_PAGE_URL = "page url : ";
    public static final String PRODUCT_TAB_ELEMENTS_COUNT = "Number of products tab elements: ";
    public static final String NUM_HELP_TAB_ELEMENTS = "Number of help tab elements: ";
    public static final String MAIN_FORM_YOUTH_PLUS_PATH = "#main-form-youth-plus path";
    public static final String SHOW_ALL_PASSENGER_TYPES = "Show All Passenger Types";
    public static final String RAIL_AGENT_DASHBOARD_SELECTOR = "id=rail-agent-dashboard";
    public static final String RAILS_BOOK_API_SELECTOR = "id=rails-book-api";
    public static final String RAIL_ENRICHMENT_SELECTOR = "id=rail-enrichment";
    public static final String RAIL_FORWARD_API_SELECTOR = "id=rail-forward-api";
    public static final String RAIL_SEARCH_API_SELECTOR = "id=rail-search-api";
    public static final String WHITE_LABEL_SELECTOR = "id=white-label";
    public static final String PRIVACY_POLICY_SELECTOR = "a:nth-of-type(1) > p";
    public static final String TERMS_AND_CONDITIONS_SELECTOR = "a:nth-of-type(2) > p";
    public Page page;

    public MainPage(Page page) {
        this.page = page;
    }

    public String getMainPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        String title =  page.title();
        System.out.println(MAIN_PAGE_TITLE + title);
        return title;
    }

    public String getMainPageURL() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.waitForTimeout(2000);
        String url =  page.url();
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

        // The size of the list
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

        // The size of the list
        int listSize = liElements.length;
        System.out.println(NUM_HELP_TAB_ELEMENTS + listSize);
        return listSize;
    }

    public int currencyListSizeChecking() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.locator("#language-currency a").click();
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
        page.locator("#language-currency a").click();
        // Find the parent element containing the list of language elements
        Locator parentElement = page.locator("id=language");

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
        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", origin);
        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");
        // Release the action-box
        page.hover(".action-box");
    }

    public void complementingTheDestinationStations(String destination) {
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", destination);
        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");
    }

    public void performSearch() {
        page.click(".search-btn");
    }

    public void passBookingTabClick() {
        page.click("id=pass-booking-tab");
    }

    public void fillCountryToVisit(String countryToVisit) {
        Locator inputElement = page.locator(".input-group:nth-child(2) .input-control");
        inputElement.click();
        inputElement.fill(countryToVisit);
        Locator itemsListElement = page.locator(".items-list > .ng-star-inserted");
        itemsListElement.hover();
        itemsListElement.click();
        Locator invalidElement = page.locator(".ng-invalid > .autocomplete-container .input-control");
        invalidElement.hover();
    }

    public void fillCountryOfResidence() {
        Locator formElement = page.locator(".pass-booking-content-form");
        formElement.click();
        Locator inputElement2 = page.locator(".input-group:nth-child(3) .input-control");
        inputElement2.type("Pakistan");
        formElement.click();
    }

    public void clickExchangeButton() {
        page.locator("//i[contains(@class, 'md-36')]").click();
    }

    public void departureDate() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = tomorrow.format(dateFormat);
        //page.fill("id=main-form-departure-date", formattedDate);
        page.click("id=main-form-departure-date");
        String xpathChoosingDate = String.format("//*[@aria-label='%s']", formattedDate);
        page.click(xpathChoosingDate);
        String actualDate = page.inputValue("id=main-form-departure-date");
        Assertions.assertEquals(formattedDate, actualDate, "The departure date filled in the input field does not match the expected date.");
    }

    public void returnDate() {
        LocalDate tomorrow = LocalDate.now().plusDays(2);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = tomorrow.format(dateFormat);
        //page.fill("id=main-form-return-date", formattedDate);
        page.click("id=main-form-return-date");
        String xpathChoosingDate = String.format("//*[@aria-label='%s']", formattedDate);
        page.click(xpathChoosingDate);
        String actualDate = page.inputValue("id=main-form-return-date");
        Assertions.assertEquals(formattedDate, actualDate, "The return date filled in the input field does not match the expected date.");
    }

    public void findMyTicketButtonClick() {
        page.click("button[name='button']");
    }

    public void findMyPassButtonClick() {
        page.click("css=.btn > p");
    }

    public void addOneAdultPassenger() {
        page.locator("#main-form-adult-plus svg").click();
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
