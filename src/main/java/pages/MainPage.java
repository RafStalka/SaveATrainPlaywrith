package pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import constans.AppConstants;

public class MainPage {
    public Page page;

    public MainPage(Page page) {
        this.page = page;
    }

    public String getMainPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getMainPageURL() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.waitForTimeout(2000);
        String url =  page.url();
        System.out.println("page url : " + url);
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
        System.out.println("Number of products tab elements: " + listSize);
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
        System.out.println("Number of help tab elements: " + listSize);
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
        System.out.println("Number of help tab elements: " + listSize);
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
        System.out.println("Number of help tab elements: " + listSize);
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

        // Find the destination input, click, and type
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", destination);

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");
    }

    public void performSearch() {
        // Click on the search button
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

    public void clickExchangeButton() {
        page.locator("//i[contains(@class, 'md-36')]").click();
    }

    public void departureDate() {
        // Click on the departure date input
        page.click(".departure-date > .form-control");
        // Click on the desired date
        page.click(".ng-star-inserted:nth-child(5) > .owl-dt-day-4 > .owl-dt-calendar-cell-content");
    }

    public void returnDate() {
        // Click on the departure date input
        page.click(".departure-date > .form-control");
        // Click on the desired date
        page.click(".ng-star-inserted:nth-child(5) > .owl-dt-day-4 > .owl-dt-calendar-cell-content");
    }

    public void findMyTicketButtonClick() {
        // Click on the find my tickets button
        page.click("button[name='button']");
    }

    public void addOneAdultPassenger() {
        // Add adult passenger type
        page.locator("#main-form-adult-plus svg").click();
    }

    public void addOneYouthPassengerIn_17_YearsAge(String youthAge) {
        // Add youth passenger type in 17 age
        page.getByText("Show All Passenger Types").click();
        page.locator("#main-form-youth-plus path").click();
        page.getByRole(AriaRole.SPINBUTTON).click();
        page.getByRole(AriaRole.SPINBUTTON).fill(youthAge);
    }
}
