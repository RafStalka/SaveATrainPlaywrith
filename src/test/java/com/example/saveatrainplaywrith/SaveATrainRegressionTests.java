package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SaveATrainRegressionTests extends PlaywrightTestBase {

    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String passportNumber = faker.number().digits(28);
    String city = faker.address().cityName();
    String street = faker.address().streetName();
    String postalCode = faker.address().zipCode();
    String phoneNumber = "48" + faker.number().numberBetween(111111111, 999999999);
    String email = "test_" + faker.name().firstName() + "@gmail.com";

    @ParameterizedTest
    @CsvSource({"Berlin Central Station, Hamburg Central Station",
            "Amsterdam Central Station, Antwerp Central Station",
            "Rome Termini, Florence All Stations",
            "Malmo Central Station, Stockholm Central Station",
            "Madrid, Leon"})
    public void checking_CorrectnessOfSearchData(String origin, String destination) {
        page.navigate(homeSAT);

        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", origin);

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");

        // Release the action-box
        page.hover(".action-box");

        // Find the destination input, click, and type "Hamburg Central Station"
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", destination);

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");

        // Click on the search button
        page.click(".search-btn");

        // Click on the departure date input
        page.click(".departure-date > .form-control");

        // Click on the desired date
        page.click(".ng-star-inserted:nth-child(4) > .owl-dt-day-2 > .owl-dt-calendar-cell-content");

        // Click on the find my tickets button
        page.click("button[name='button']");

        String searchPage_Origin = page.locator(".direction .direction-from").textContent().trim();
        Assertions.assertEquals(origin, searchPage_Origin);

        String searchPage_Destination = page.locator(".direction .direction-to").textContent().trim();
        Assertions.assertEquals(destination, searchPage_Destination);

        // zrobic asercje na date
        // zrobic asercje na typ pasazerow
    }

    @ParameterizedTest
    @CsvSource({"Berlin Central Station, Hamburg Central Station",
            "Amsterdam Central Station, Antwerp Central Station"})
    public void e2e_SAT_NSI_test(String origin, String destination) {
        page.navigate(homeSAT);

        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", origin);

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");

        // Release the action-box
        page.hover(".action-box");

        // Find the destination input, click, and type "Hamburg Central Station"
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", destination);

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");

        // Click on the search button
        page.click(".search-btn");

        // Click on the departure date input
        page.click(".departure-date > .form-control");

        // Click on the desired date
        page.click(".ng-star-inserted:nth-child(4) > .owl-dt-day-2 > .owl-dt-calendar-cell-content");

        // Click on the find my tickets button
        page.click("button[name='button']");

        // Selects a train
        page.click("id=result-1");

        // On this moment we can check checkboxes functionality
        //page.check("lokator"); - zaznaczenie checkboxa
        //page.uncheck("lokator"); - odznaczenie checkboxa


        // Then proceeds booking
        page.click(".proceed-btn");

        // Click on the passenger-prefix element
        page.click("#passenger-prefix");

        // Select the second option in the dropdown
        Locator genderDropdown = page.locator("#passenger-prefix");
        genderDropdown.selectOption(new SelectOption().setIndex(1));

        // Click on the passenger-fname element
        page.click("#passenger-fname");

        // Type "Test" into the passenger-fname input
        page.fill("#passenger-fname", firstName);

        // Type "Tester" into the passenger-lname input
        page.fill("#passenger-lname", lastName);

        // Click on the passenger-date element
        page.click("#passenger-date");

        // Type the date into the passenger-date input
        page.fill("#passenger-date", "06/09/1985");

        // Click on the input-control element
        page.click(".input-control");

        // Find and hover over the items-list element
        Locator itemsList = page.locator(".items-list > .ng-star-inserted:first-of-type");
        itemsList.click();

        // Select "Aisle" from the dropdown
        Locator passengerTypeDropdown = page.locator(".outbound-seat-select > .form-control");
        if (passengerTypeDropdown.isVisible()) {
            passengerTypeDropdown.selectOption(new SelectOption().setIndex(1));
        }

        // Click on the ng-pristine element
        page.click(".ng-pristine");

        // Enter an email in the contact-info-input form control
        page.fill(".contact-info-input > .form-control", email);

        // Click on the submit-button element
        page.click(".submit-button > .ng-star-inserted");

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        // Switch to frame by index
        Frame frame1 = page.frames().get(1);

        // Locate the card number input field within frame1
        Locator cardNumberInput = frame1.locator("id=encryptedCardNumber").first();

        // Click the card number input field and enter the card number
        cardNumberInput.click();
        cardNumberInput.type("5577 0000 5577 0004");

        // Switch back to the main frame
        page.mainFrame();

        Frame frame2 = page.frames().get(2);
        Locator expiryDateInput = frame2.locator("id=encryptedExpiryDate").first();
        expiryDateInput.type("03/30");

        // Switch back to the main frame
        page.locator("html").click();

        Frame frame3 = page.frames().get(3);
        Locator cvcCodeInput = frame3.locator("id=encryptedSecurityCode").first();
        cvcCodeInput.type("737");

        // Switch back to the main frame
        page.locator("html").click();

        page.locator(".adyen-checkout__input--text").click();
        page.locator(".adyen-checkout__input--text").fill("Customer");

        // Click on pay button
        page.locator(".adyen-checkout__button__content").click();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle ticketElement = page.querySelector(".tickets-order-data");

        if (ticketElement != null) {
            // Use the textContent method to get the text from the element
            String elementText = ticketElement.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Locate the element from which you want to extract text
        ElementHandle orderElement = page.querySelector(".common-order-data");

        if (orderElement != null) {
            // Use the textContent method to get the text from the element
            String elementText = orderElement.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);


        // trip rout //p[@class='trip-route']
        // passenger info //span[@class='property-value']
        // price //span[@class='property-value price-value']
        // seat reserve fee //span[@class='property-value outbound-seat-fee-value']
        // final price //span[@class='property-value total-discounted-price-value']
        // order data //div[@class='common-order-data']

    }

    @Test
    public void e2e_SAT_TI_test() {
        page.navigate(homeSAT);

        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", "Rome Termini");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");

        // Release the action-box
        page.hover(".action-box");

        // Find the destination input, click, and type "Hamburg Central Station"
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", "Florence All Stations");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");

        // Click on the search button
        page.click(".search-btn");

        // Click on the departure date input
        page.click(".departure-date > .form-control");

        // Click on the desired date
        page.click(".ng-star-inserted:nth-child(4) > .owl-dt-day-2 > .owl-dt-calendar-cell-content");

        // Click on the find my tickets button
        page.click("button[name='button']");

        // Selects a train
        page.click("id=result-1");

        // On this moment we can check checkboxes functionality
        //page.check("lokator"); - zaznaczenie checkboxa
        //page.uncheck("lokator"); - odznaczenie checkboxa


        // Then proceeds booking
        page.click(".proceed-btn");

        // Click on the passenger-prefix element
        page.click("#passenger-prefix");

        // Select the second option in the dropdown
        Locator genderDropdown = page.locator("#passenger-prefix");
        genderDropdown.selectOption(new SelectOption().setIndex(1));

        // Click on the passenger-fname element
        page.click("#passenger-fname");

        // Type "Test" into the passenger-fname input
        page.fill("#passenger-fname", firstName);

        // Type "Tester" into the passenger-lname input
        page.fill("#passenger-lname", lastName);

        // Click on the passenger-date element
        page.click("#passenger-date");

        // Type the date into the passenger-date input
        page.fill("#passenger-date", "06/09/1985");

        // Click on the input-control element
        page.click(".input-control");

        // Find and hover over the items-list element
        Locator itemsList = page.locator(".items-list > .ng-star-inserted:first-of-type");
        itemsList.click();

        // Enter an email in the contact-info-input form control
        page.fill(".contact-info-input > .form-control", email);

        // Click on the submit-button element
        page.click(".submit-button > .ng-star-inserted");

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        // Switch to frame by index
        Frame frame1 = page.frames().get(1);

        // Locate the card number input field within frame1
        Locator cardNumberInput = frame1.locator("id=encryptedCardNumber").first();

        // Click the card number input field and enter the card number
        cardNumberInput.click();
        cardNumberInput.type("5577 0000 5577 0004");

        // Switch back to the main frame
        page.mainFrame();

        Frame frame2 = page.frames().get(2);
        Locator expiryDateInput = frame2.locator("id=encryptedExpiryDate").first();
        expiryDateInput.type("03/30");

        // Switch back to the main frame
        page.locator("html").click();

        Frame frame3 = page.frames().get(3);
        Locator cvcCodeInput = frame3.locator("id=encryptedSecurityCode").first();
        cvcCodeInput.type("737");

        // Switch back to the main frame
        page.locator("html").click();

        page.locator(".adyen-checkout__input--text").click();
        page.locator(".adyen-checkout__input--text").fill("Customer");

        // Click on pay button
        page.locator(".adyen-checkout__button__content").click();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle element = page.querySelector(".common-order-data");

        if (element != null) {
            // Use the textContent method to get the text from the element
            String elementText = element.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }

    @Test
    public void e2e_SAT_EURAIL_test() {
        page.navigate(homeSAT);

        // Click on the search button
        page.click(".search-btn");
        page.click("id=pass-booking-tab");

        // Find and interact with the elements
        Locator inputElement = page.locator(".input-group:nth-child(2) .input-control");
        inputElement.click();
        inputElement.type("Global Pass");

        Locator itemsListElement = page.locator(".items-list > .ng-star-inserted");
        itemsListElement.hover();
        itemsListElement.click();

        Locator invalidElement = page.locator(".ng-invalid > .autocomplete-container .input-control");
        invalidElement.hover();

        Locator formElement = page.locator(".pass-booking-content-form");
        formElement.click();

        Locator inputElement2 = page.locator(".input-group:nth-child(3) .input-control");
        inputElement2.type("Pakistan");

        formElement.click();

        Locator buttonElement = page.locator(".btn > p");
        buttonElement.click();

        page.click("id=pass-1");
        page.click(".proceed-btn");

        // Select the second option in the dropdown
        Locator genderDropdown = page.locator("#passenger-prefix");
        genderDropdown.selectOption(new SelectOption().setIndex(1));

        // Click on the passenger-fname element
        page.click("#passenger-fname");

        // Type "Test" into the passenger-fname input
        page.fill("#passenger-fname", firstName);

        // Type "Tester" into the passenger-lname input
        page.fill("#passenger-lname", lastName);

        // Click on the passenger-date element
        page.click("#passenger-date");

        // Type the date into the passenger-date input
        page.fill("#passenger-date", "06/09/1985");

        // Click on the input-control element
        page.click(".input-control");

        // Find and hover over the items-list element
        Locator itemsList = page.locator(".items-list > .ng-star-inserted:first-of-type");
        itemsList.click();

        // Fill passenger contact number
        page.click("id=passenger-contact-number");
        page.fill("id=passenger-contact-number", phoneNumber);

        // Click on the ng-pristine element
        page.click(".ng-pristine");

        // Enter an email in the contact-info-input form control
        page.fill(".contact-info-input > .form-control", email);

        // Click on the submit-button element
        page.click(".submit-button > .ng-star-inserted");

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        // Switch to frame by index
        Frame frame1 = page.frames().get(1);

        // Locate the card number input field within frame1
        Locator cardNumberInput = frame1.locator("id=encryptedCardNumber").first();

        // Click the card number input field and enter the card number
        cardNumberInput.click();
        cardNumberInput.type("5577 0000 5577 0004");

        // Switch back to the main frame
        page.mainFrame();

        Frame frame2 = page.frames().get(2);
        Locator expiryDateInput = frame2.locator("id=encryptedExpiryDate").first();
        expiryDateInput.type("03/30");

        // Switch back to the main frame
        page.locator("html").click();

        Frame frame3 = page.frames().get(3);
        Locator cvcCodeInput = frame3.locator("id=encryptedSecurityCode").first();
        cvcCodeInput.type("737");

        // Switch back to the main frame
        page.locator("html").click();

        page.locator(".adyen-checkout__input--text").click();
        page.locator(".adyen-checkout__input--text").fill("Customer");

        // Click on pay button
        page.locator(".adyen-checkout__button__content").click();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle element = page.querySelector(".common-order-data");

        if (element != null) {
            // Use the textContent method to get the text from the element
            String elementText = element.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);
    }

    @Test
    public void _changingDestinationWithOrigin_FunctionalityButton() {
        page.navigate(homeSAT);

        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", "Berlin Central Station");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");

        // Release the action-box
        page.hover(".action-box");

        // Find the destination input, click, and type "Hamburg Central Station"
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", "Hamburg Central Station");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");

        // Find and interact with the elements
        String originPrimary = page.locator(".input-control-container > .origin").inputValue();
        String destinPrimary = page.locator(".destination:nth-child(1)").inputValue();

        Assertions.assertEquals("Berlin Central Station", originPrimary);
        Assertions.assertEquals("Hamburg Central Station", destinPrimary);

        page.locator("//i[contains(@class, 'md-36')]").click();

        // Find and interact with the elements
        String originSecondary = page.locator(".input-control-container > .origin").inputValue();
        String destinSecondary = page.locator(".destination:nth-child(1)").inputValue();

        Assertions.assertEquals("Hamburg Central Station", originSecondary);
        Assertions.assertEquals("Berlin Central Station", destinSecondary);
    }

    @Test
    public void checkingProductsTabListSize() {
        page.navigate(homeSAT);

        // Find the parent element containing the list of <li> elements
        Locator parentElement = page.locator("#products-dropdown");

        // Find all <li> elements within the parent element and convert to an array
        ElementHandle[] liElements = parentElement.locator("li").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        // Check the size of the list
        int listSize = liElements.length;
        System.out.println("Number of products tab elements: " + listSize);

        Assertions.assertEquals(listSize, 7);
    }

    @Test
    public void railSearchApi_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail search api
        page.click("id=rail-search-api");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rail Search Api | Save A Train", title);
    }

    @Test
    public void railBookApi_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rails-book-api");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rail Book Api | Save A Train", title);
    }

    @Test
    public void railForwardApi_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rail-forward-api");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rails Forward Api | Save A Train", title);
    }

    @Test
    public void railAgentDashboard_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rail-agent-dashboard");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rail Agent Dashboard | Save A Train", title);
    }

    @Test
    public void whiteLabel_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=white-label");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("White Label | Save A Train", title);
    }

    @Test
    public void railAffiliate_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=rail-affiliate");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Rail Affiliate | Save A Train", title);
    }

    @Test
    public void eurailPass_ProductChecking() {
        page.navigate(homeSAT);

        // User clicks on products tab
        page.click("id=products");
        // User clicks on rail book api
        page.click("id=eurail-pass");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Eurail Pass | Save A Train", title);
    }

    @Test
    public void aboutUs_TabChecking() {
        page.navigate(homeSAT);

        // User clicks on about us tab
        page.click("id=about-us");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("About Us | Save A Train", title);
    }

    @Test
    public void redirection_BlogTab() {
        page.navigate(homeSAT);

    }

    @Test
    public void contactUs_TabChecking() {
        page.navigate(homeSAT);

        // User clicks on about us tab
        page.click("id=contact-us");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Contact Us | Save A Train", title);
    }

    @Test
    public void checkingHelpTabListSize() {
        page.navigate(homeSAT);

        // Find the parent element containing the list of <li> elements
        Locator parentElement = page.locator("#help-dropdown");

        // Find all <li> elements within the parent element and convert to an array
        ElementHandle[] liElements = parentElement.locator("li").all().stream()
                .map(Locator::elementHandle)
                .toArray(ElementHandle[]::new);

        // Check the size of the list
        int listSize = liElements.length;
        System.out.println("Number of help tab elements: " + listSize);

        Assertions.assertEquals(listSize, 2);
    }

    @Test
    public void manageBookings_HelpTabChecking() {
        page.navigate(homeSAT);

        // User clicks on help tab
        page.click("id=help-dropdown");
        // User choose manage bookings from dropdown
        page.click("id=manage-booking");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("Manage Bookings | Save A Train", title);
    }

    @Test
    public void FAQ_HelpTabChecking() {
        page.navigate(homeSAT);

        // User clicks on help tab
        page.click("id=help-dropdown");
        // User choose manage bookings from dropdown
        page.click("id=faq");
        // User should see rail search api product introduction page
        String title = page.title();
        Assertions.assertEquals("FAQ | Save A Train", title);
    }

    @Test
    public void readMore_ButtonChecking() {
        page.navigate(homeSAT);

        // Click on Read more button
        page.click(".services-body-desktop > .more-button");
        // Checking title
        String readMoreTitle = page.title();
        Assertions.assertEquals("About Us | Save A Train", readMoreTitle);
    }

    @Test
    public void privacyPolicy_ButtonChecking() {
        page.navigate(homeSAT);
        // Click on Privacy Policy button
        page.click("a:nth-of-type(1) > p");
        // Checking title
        String privacyPolicyTitle = page.title();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    public void termsAndConditions_ButtonChecking() {
        page.navigate(homeSAT);
        // Click on Terms and Conditions button
        page.click("a:nth-of-type(2) > p");
        // Checking title
        String privacyPolicyTitle = page.title();
        Assertions.assertEquals("Train Ticket Booking and Reservation | Save A Train", privacyPolicyTitle);
    }

    @Test
    public void languageAndCurrency_FunctionalityChecking() {
        page.navigate(homeSAT);

        // Click on current language box
        page.click(".current-language-box");
        //page.pause();

    }

    @Test
    public void e2e_SAT_ACPForSpain_test() {
        page.navigate(homeSAT);

        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", "Madrid");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");

        // Release the action-box
        page.hover(".action-box");

        // Find the destination input, click, and type "Hamburg Central Station"
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", "Leon");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");

        // Click on the search button
        page.click(".search-btn");

        // Click on the departure date input
        page.click(".departure-date > .form-control");

        // Click on the desired date
        page.click(".ng-star-inserted:nth-child(4) > .owl-dt-day-2 > .owl-dt-calendar-cell-content");

        // Click on the find my tickets button
        page.click("button[name='button']");

        // Selects a train
        page.click("id=result-1");

        // On this moment we can check checkboxes functionality
        //page.check("lokator"); - zaznaczenie checkboxa
        //page.uncheck("lokator"); - odznaczenie checkboxa


        // Then proceeds booking
        page.click(".proceed-btn");

        // Click on the passenger-prefix element
        page.click("#passenger-prefix");

        // Select the second option in the dropdown
        Locator genderDropdown = page.locator("#passenger-prefix");
        genderDropdown.selectOption(new SelectOption().setIndex(1));

        // Click on the passenger-fname element
        page.click("#passenger-fname");

        // Type "Test" into the passenger-fname input
        page.fill("#passenger-fname", firstName);

        // Type "Tester" into the passenger-lname input
        page.fill("#passenger-lname", lastName);

        // Click on the passenger-date element
        page.click("#passenger-date");

        // Type the date into the passenger-date input
        page.fill("#passenger-date", "06/09/1985");

        // Click on the input-control passenger country element
        page.click(".passenger-country .input-control");

        // Find and hover over the items-list passenger country element
        Locator itemsList = page.locator(".passenger-country .items-list > .ng-star-inserted:first-of-type");
        itemsList.click();

        // Click on the input-control nationality element
        page.click(".nationality-country .input-control");

        //Select nationality
        Locator nationalityList = page.locator(".nationality-country .items-list > .ng-star-inserted:first-of-type");
        nationalityList.click();

        // Click on the input-control birth country element
        page.click(".birth-country .input-control");

        //Select birth country
        Locator birthCountryList = page.locator(".birth-country .items-list > .ng-star-inserted:first-of-type");
        birthCountryList.click();

        // Enter passport number
        page.fill("id=passenger-passport", passportNumber);

        // Select "Aisle" from the dropdown
        Locator passengerTypeDropdown = page.locator(".outbound-seat-select > .form-control");
        if (passengerTypeDropdown.isVisible()) {
            passengerTypeDropdown.selectOption(new SelectOption().setIndex(1));
        }

        // Click on the ng-pristine element
        page.click(".ng-pristine");

        // Enter city
        page.fill("//input[@placeholder='City']", city);

        // Enter address
        page.fill("//input[@placeholder='Address']", street);

        // Enter postal code
        page.fill("//input[@placeholder='Postal Code']", postalCode);

        // Enter mobile
        page.fill("//input[@placeholder='Mobile']", phoneNumber);

        // Enter an email in the contact-info-input form control
        page.fill("//input[@placeholder='Email']", email);

        // Click on the submit-button element
        page.click(".submit-button > .ng-star-inserted");

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        // Switch to frame by index
        Frame frame1 = page.frames().get(1);

        // Locate the card number input field within frame1
        Locator cardNumberInput = frame1.locator("id=encryptedCardNumber").first();

        // Click the card number input field and enter the card number
        cardNumberInput.click();
        cardNumberInput.type("5577 0000 5577 0004");

        // Switch back to the main frame
        page.mainFrame();

        Frame frame2 = page.frames().get(2);
        Locator expiryDateInput = frame2.locator("id=encryptedExpiryDate").first();
        expiryDateInput.type("03/30");

        // Switch back to the main frame
        page.locator("html").click();

        Frame frame3 = page.frames().get(3);
        Locator cvcCodeInput = frame3.locator("id=encryptedSecurityCode").first();
        cvcCodeInput.type("737");

        // Switch back to the main frame
        page.locator("html").click();

        page.locator(".adyen-checkout__input--text").click();
        page.locator(".adyen-checkout__input--text").fill("Customer");

        // Click on pay button
        page.locator(".adyen-checkout__button__content").click();

        // Sleep for 7 seconds
        page.waitForTimeout(10000);
        page.isVisible("css=h3");

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle ticketElement = page.querySelector(".tickets-order-data");

        if (ticketElement != null) {
            // Use the textContent method to get the text from the element
            String elementText = ticketElement.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Locate the element from which you want to extract text
        ElementHandle orderElement = page.querySelector(".common-order-data");

        if (orderElement != null) {
            // Use the textContent method to get the text from the element
            String elementText = orderElement.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }

    @Test
    public void e2e_SAT_ACPForSweden_test() {
        page.navigate(homeSAT);

        page.click(".input-control-container > .origin");
        page.type(".input-control-container > .origin", "Malmo Central Station");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".origin .ng-star-inserted:nth-child(1)");
        page.click(".origin .ng-star-inserted:nth-child(1)");

        // Release the action-box
        page.hover(".action-box");

        // Find the destination input, click, and type "Hamburg Central Station"
        page.click(".destination:nth-child(1)");
        page.type(".destination:nth-child(1)", "Stockholm Central Station");

        // Wait for auto-suggest and select the first option
        page.waitForSelector(".destination .ng-star-inserted:nth-child(1)");
        page.click(".destination .ng-star-inserted:nth-child(1)");

        // Click on the search button
        page.click(".search-btn");

        // Click on the departure date input
        page.click(".departure-date > .form-control");

        // Click on the desired date
        page.click(".ng-star-inserted:nth-child(4) > .owl-dt-day-2 > .owl-dt-calendar-cell-content");

        // Click on the find my tickets button
        page.click("button[name='button']");

        // Selects a train
        page.click("id=result-1");

        // On this moment we can check checkboxes functionality
        //page.check("lokator"); - zaznaczenie checkboxa
        //page.uncheck("lokator"); - odznaczenie checkboxa


        // Then proceeds booking
        page.click(".proceed-btn");

        // Click on the passenger-prefix element
        page.click("#passenger-prefix");

        // Select the second option in the dropdown
        Locator genderDropdown = page.locator("#passenger-prefix");
        genderDropdown.selectOption(new SelectOption().setIndex(1));

        // Click on the passenger-fname element
        page.click("#passenger-fname");

        // Type "Test" into the passenger-fname input
        page.fill("#passenger-fname", firstName);

        // Type "Tester" into the passenger-lname input
        page.fill("#passenger-lname", lastName);

        // Click on the passenger-date element
        page.click("#passenger-date");

        // Type the date into the passenger-date input
        page.fill("#passenger-date", "06/09/1985");

        // Click on the input-control passenger country element
        page.click(".passenger-country .input-control");

        // Find and hover over the items-list passenger country element
        Locator itemsList = page.locator(".passenger-country .items-list > .ng-star-inserted:first-of-type");
        itemsList.click();

        // Click on the input-control nationality element
        page.click(".nationality-country .input-control");

        //Select nationality
        Locator nationalityList = page.locator(".nationality-country .items-list > .ng-star-inserted:first-of-type");
        nationalityList.click();

        // Click on the input-control birth country element
        page.click(".birth-country .input-control");

        //Select birth country
        Locator birthCountryList = page.locator(".birth-country .items-list > .ng-star-inserted:first-of-type");
        birthCountryList.click();

        // Enter passport number
        page.fill("id=passenger-passport", passportNumber);

        // Select "Aisle" from the dropdown
        Locator passengerTypeDropdown = page.locator(".outbound-seat-select > .form-control");
        if (passengerTypeDropdown.isVisible()) {
            passengerTypeDropdown.selectOption(new SelectOption().setIndex(1));
        }

        // Click on the ng-pristine element
        page.click(".ng-pristine");

        // Enter city
        page.fill("//input[@placeholder='City']", city);

        // Enter address
        page.fill("//input[@placeholder='Address']", street);

        // Enter postal code
        page.fill("//input[@placeholder='Postal Code']", postalCode);

        // Enter mobile
        page.fill("//input[@placeholder='Mobile']", phoneNumber);

        // Enter an email in the contact-info-input form control
        page.fill("//input[@placeholder='Email']", email);

        // Click on the submit-button element
        page.click(".submit-button > .ng-star-inserted");

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        // Switch to frame by index
        Frame frame1 = page.frames().get(1);

        // Locate the card number input field within frame1
        Locator cardNumberInput = frame1.locator("id=encryptedCardNumber").first();

        // Click the card number input field and enter the card number
        cardNumberInput.click();
        cardNumberInput.type("5577 0000 5577 0004");

        // Switch back to the main frame
        page.mainFrame();

        Frame frame2 = page.frames().get(2);
        Locator expiryDateInput = frame2.locator("id=encryptedExpiryDate").first();
        expiryDateInput.type("03/30");

        // Switch back to the main frame
        page.locator("html").click();

        Frame frame3 = page.frames().get(3);
        Locator cvcCodeInput = frame3.locator("id=encryptedSecurityCode").first();
        cvcCodeInput.type("737");

        // Switch back to the main frame
        page.locator("html").click();

        page.locator(".adyen-checkout__input--text").click();
        page.locator(".adyen-checkout__input--text").fill("Customer");

        // Click on pay button
        page.locator(".adyen-checkout__button__content").click();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle ticketElement = page.querySelector(".tickets-order-data");

        if (ticketElement != null) {
            // Use the textContent method to get the text from the element
            String elementText = ticketElement.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Locate the element from which you want to extract text
        ElementHandle orderElement = page.querySelector(".common-order-data");

        if (orderElement != null) {
            // Use the textContent method to get the text from the element
            String elementText = orderElement.textContent();

            // Print the text to the console
            System.out.println(elementText);
        } else {
            System.err.println("Element not found");
        }

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }
}
