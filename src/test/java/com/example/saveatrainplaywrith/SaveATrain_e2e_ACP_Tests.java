package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SaveATrain_e2e_ACP_Tests extends PlaywrightTestBase {
    String departureDateDay = "25";
    String returnDateDay = "28";
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String firstNameSecondPassenger = faker.name().firstName();
    String firstNameThirdPassenger = faker.name().firstName();
    String lastName = faker.name().lastName();
    String lastNameSecondPassenger = faker.name().lastName();
    String lastNameThirdPassenger = faker.name().lastName();
    String passportNumber = faker.number().digits(28);
    String passportNumberSecondPassenger = faker.number().digits(28);
    String passportNumberThirdPassenger = faker.number().digits(28);
    String city = faker.address().cityName();
    String street = faker.address().streetName();
    String postalCode = faker.address().zipCode();
    String phoneNumber = "48" + faker.number().numberBetween(111111111, 999999999);
    String email = "test_" + faker.name().firstName() + "@gmail.com";

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
        page.click(".ng-star-inserted:nth-child(5) > .owl-dt-day-4 > .owl-dt-calendar-cell-content");

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

        // Sleep for 30 seconds
        page.waitForTimeout(30000);
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
    public void e2e_SAT_ACPRoundTrip_ForSpain_test() {
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

        // Set departure date
        page.locator("#main-form-departure-date").click();
        page.getByText(departureDateDay).click();

        // Set return date
        page.getByPlaceholder("(optional)").click();
        page.getByText(returnDateDay).click();

        // Add adult passenger type
        page.locator("#main-form-adult-plus svg").click();

        // Add youth passenger type in 17 age
        page.getByText("Show All Passenger Types").click();
        page.locator("#main-form-youth-plus path").click();
        page.getByRole(AriaRole.SPINBUTTON).click();
        page.getByRole(AriaRole.SPINBUTTON).fill("17");

        // Click on the find my tickets button
        page.click("button[name='button']");

        // Select first side connection
        page.locator("#result-1").getByText("Select").click();
        page.getByText("Proceed").click();

        // Select second side connection
        page.locator("#result-1").getByText("Select").click();
        page.getByText("Proceed").click();

        // On this moment we can check checkboxes functionality
        //page.check("lokator"); - zaznaczenie checkboxa
        //page.uncheck("lokator"); - odznaczenie checkboxa

        // Select the second option - Mr - in the dropdown first Adult passenger
        page.getByLabel("Title Mr  Mrs  Miss  Ms").selectOption("Mr");

        // Type first name into the passenger-fname input first Adult passenger
        page.getByPlaceholder("First name").first().fill(firstName);

        // Click and type last name first Adult passenger
        page.getByPlaceholder("Last name").first().click();
        page.getByPlaceholder("Last name").first().fill(lastName);

        // Click and type on the passenger-date element first Adult passenger
        page.getByPlaceholder("DD/MM/YYYY").first().click();
        page.getByPlaceholder("DD/MM/YYYY").first().fill("06/09/1985");


        // Click on the input-control passenger country element first Adult passenger
        page.getByPlaceholder("Select country").first().click();
        page.getByRole(AriaRole.MAIN).getByRole(AriaRole.LIST).getByText("Afghanistan").click();

        // Click and select on the input-control nationality element first Adult passenger
        page.click(".nationality-country .input-control");
        page.getByRole(AriaRole.MAIN).getByRole(AriaRole.LIST).getByText("Afghanistan").click();

        // Click and select on the input-control birth country element first Adult passenger
        page.click(".birth-country .input-control");
        page.getByRole(AriaRole.MAIN).getByRole(AriaRole.LIST).getByText("Afghanistan").click();

        // Enter passport number first Adult passenger
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms clearAfghanistanclearAfghanistanclearAfghanistan")).getByPlaceholder("Passport Number").click();
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms clearAfghanistanclearAfghanistanclearAfghanistan")).getByPlaceholder("Passport Number").fill(passportNumber);

        // Click on the passenger male dropdown and select - Mrs second Adult passenger
        page.locator("#passenger-prefix").nth(1).selectOption("Mrs");

        // Click and enter first name second Adult passenger
        page.getByPlaceholder("First name").nth(1).click();
        page.getByPlaceholder("First name").nth(1).fill(firstNameSecondPassenger);

        // Click and enter last name second Adult passenger
        page.getByPlaceholder("Last name").nth(1).click();
        page.getByPlaceholder("Last name").nth(1).fill(lastNameSecondPassenger);

        // Click and enter birth day second Adult passenger
        page.getByPlaceholder("DD/MM/YYYY").nth(1).click();
        page.getByPlaceholder("DD/MM/YYYY").nth(1).fill("06/09/1985");

        // Click and select on the input-control second Adult passenger country
        page.click(".ng-star-inserted:nth-child(2) > sat-passenger-info .passenger-date-country .input-control");
        Locator countryItemsList = page.locator(".ng-star-inserted:nth-child(2) > sat-passenger-info .passenger-date-country .items-list > .ng-star-inserted:first-of-type");
        countryItemsList.click();

        // Click and select on the input-control nationality element second Adult passenger
        page.click(".ng-star-inserted:nth-child(2) > sat-passenger-info .nationality-country .input-control");
        Locator nationalityItemsList = page.locator(".ng-star-inserted:nth-child(2) > sat-passenger-info .nationality-country .items-list .ng-star-inserted:first-of-type");
        nationalityItemsList.click();


        // Click and select on the input-control birth country element second Adult passenger
        page.click(".ng-star-inserted:nth-child(2) > sat-passenger-info .birth-country .input-control");
        Locator birthCountryItemsList = page.locator(".ng-star-inserted:nth-child(2) > sat-passenger-info .birth-country .items-list .ng-star-inserted:first-of-type");
        birthCountryItemsList.click();

        // Enter passport number second Adult passenger
        page.getByPlaceholder("Passport Number").nth(1).click();
        page.getByPlaceholder("Passport Number").nth(1).fill(passportNumberSecondPassenger);

        // Select the second option - Mr - in the dropdown third passenger -Youth
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).locator("#passenger-prefix").selectOption("Mr");

        // Click and enter first name Youth passenger
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("First name").click();
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("First name").fill(firstNameThirdPassenger);

        // Click and enter last name Youth passenger
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("Last name").click();
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("Last name").fill(lastNameThirdPassenger);

        // Click and enter birth day Youth passenger (age 17)
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("DD/MM/YYYY").click();
        page.locator("sat-passenger-info").filter(new Locator.FilterOptions().setHasText("Title Mr Mrs Miss Ms arrow_drop_downAfghanistanAland IslandAlbaniaAlgeriaAmerica")).getByPlaceholder("DD/MM/YYYY").fill("06/09/2006");

        // Click and select on the input-control Youth passenger country
        page.click(".ng-star-inserted:nth-child(3) > sat-passenger-info .passenger-date-country .input-control");
        Locator countryItemsListOfYouth = page.locator(".ng-star-inserted:nth-child(3) > sat-passenger-info .passenger-date-country .items-list > .ng-star-inserted:first-of-type");
        countryItemsListOfYouth.click();

        // Click and select on the input-control nationality element Youth passenger
        page.click(".ng-star-inserted:nth-child(3) > sat-passenger-info .nationality-country .input-control");
        Locator nationalityItemsListOfYouth = page.locator(".ng-star-inserted:nth-child(3) > sat-passenger-info .nationality-country .items-list .ng-star-inserted:first-of-type");
        nationalityItemsListOfYouth.click();


        // Click and select on the input-control birth country element Youth passenger
        page.click(".ng-star-inserted:nth-child(3) > sat-passenger-info .birth-country .input-control");
        Locator birthCountryItemsListOfYouth = page.locator(".ng-star-inserted:nth-child(3) > sat-passenger-info .birth-country .items-list .ng-star-inserted:first-of-type");
        birthCountryItemsListOfYouth.click();

        // Enter passport number Youth passenger
        page.getByPlaceholder("Passport Number").nth(2).click();
        page.getByPlaceholder("Passport Number").nth(2).fill(passportNumberThirdPassenger);

        // Seat preference outbound
        page.getByLabel("Outbound seat preferenceWindowAisle").selectOption("Window");

        // Seat preference inbound
        page.getByLabel("Inbound seat preferenceWindowAisle").selectOption("Aisle");

        // Enter city
        page.getByPlaceholder("City").click();
        page.getByPlaceholder("City").fill(city);

        // Enter address
        page.getByPlaceholder("Address").click();
        page.getByPlaceholder("Address").fill(street);

        // Enter postal code
        page.getByPlaceholder("Postal Code").click();
        page.getByPlaceholder("Postal Code").fill(postalCode);

        // Enter mobile
        page.getByPlaceholder("Mobile").click();
        page.getByPlaceholder("Mobile").fill(phoneNumber);

        // Enter an email in the contact-info-input form control
        page.getByPlaceholder("Email").click();
        page.getByPlaceholder("Email").fill(email);

        // Click on the "I agree, checkout" element
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

        // Sleep for 30 seconds
        page.waitForTimeout(30000);
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
        page.click(".ng-star-inserted:nth-child(5) > .owl-dt-day-4 > .owl-dt-calendar-cell-content");

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
