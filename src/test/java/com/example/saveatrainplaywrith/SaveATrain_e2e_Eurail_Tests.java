package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SaveATrain_e2e_Eurail_Tests extends PlaywrightTestBase {
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String passportNumber = faker.number().digits(28);
    String city = faker.address().cityName();
    String street = faker.address().streetName();
    String postalCode = faker.address().zipCode();
    String phoneNumber = "48" + faker.number().numberBetween(111111111, 999999999);
    String email = "test_" + faker.name().firstName() + "@gmail.com";

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
}
