package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.SelectOption;
import constans.AppConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.PassengersDetailsPage;
import pages.ResultsPage;
import pages.SummaryPage;

import static io.qameta.allure.SeverityLevel.CRITICAL;

public class SaveATrain_e2e_ACP_Tests extends PlaywrightTestBase {

    @BeforeEach
    public void setUp() {
        // Assuming 'page' is initialized in base class
        mainPage = new MainPage(page);
        passengersDetailsPage = new PassengersDetailsPage(page);
        resultsPage = new ResultsPage(page);
        summaryPage = new SummaryPage(page);
    }
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
    @DisplayName("ACP provider for Spain.")
    @Description("Test checking e2e path on ACP provider for Spain.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    public void e2e_SAT_ACPForSpain_test() {
        mainPage.complementingTheOriginStations("Madrid");
        mainPage.complementingTheDestinationStations("Leon");
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        //resultsPage.selectFirstResult();

        //passengersDetailsPage.passengerPrefixDropdown("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.choosePassengerNationality();
        passengersDetailsPage.choosePassengerBirthCountry();
        passengersDetailsPage.enterPassportNumber(passportNumber);
        //passengersDetailsPage.chooseAislePlace();
        passengersDetailsPage.enterCity(city);
        passengersDetailsPage.enterStreet(street);
        passengersDetailsPage.enterPostalCode(postalCode);
        passengersDetailsPage.enterMobilePhone(phoneNumber);
        //passengersDetailsPage.enterEmeil(email);
        //passengersDetailsPage.passangersDataSubmitButtonClick();

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }

    @Test
    @DisplayName("ACP provider for Spain with round trip.")
    @Description("Test checking e2e path on ACP provider for Spain with round trip.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    public void e2e_SAT_ACPRoundTrip_ForSpain_test() {
        mainPage.complementingTheOriginStations("Madrid");
        mainPage.complementingTheDestinationStations("Leon");
        mainPage.departureDate();
        mainPage.returnDate();
        mainPage.addOneAdultPassenger();
        mainPage.addOneYouthPassengerIn_17_YearsAge("17");
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();
        resultsPage.selectSecondSideConnection();
        resultsPage.proceed();



        //passengersDetailsPage.passengerPrefixDropdown("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        // Click on the input-control passenger country element first Adult passenger
        passengersDetailsPage.choosePassengerCountry();
        // Click and select on the input-control nationality element first Adult passenger
        passengersDetailsPage.choosePassengerNationality();
        // Click and select on the input-control birth country element first Adult passenger
        passengersDetailsPage.choosePassengerBirthCountry();
        // Enter passport number first Adult passenger
        passengersDetailsPage.enterPassportNumber(passportNumber);

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

        summaryPage.completingAdyenForm();

        // Sleep for 30 seconds
        page.waitForTimeout(30000);
        page.isVisible("css=h3");

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle ticketElement = page.querySelector(".tickets-order-data");

        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }

    @Test
    @DisplayName("ACP provider for Sweden.")
    @Description("Test checking e2e path on ACP provider for Sweden.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    public void e2e_SAT_ACPForSweden_test() {
        page.navigate(AppConstants.SAT_HOME_PAGE);

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

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent();

        // Locate the element from which you want to extract text
        ElementHandle ticketElement = page.querySelector(".tickets-order-data");


        // Use Assertions for the assertion
        Assertions.assertEquals("Thank you for purchase!", actualHeaderText);

    }
}
