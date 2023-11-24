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

public class SaveATrain_e2e_Tests extends PlaywrightTestBase {
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
}
