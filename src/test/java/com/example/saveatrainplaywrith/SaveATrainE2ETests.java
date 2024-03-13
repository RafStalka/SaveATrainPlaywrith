package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.MainPage;
import pages.PassengersDetailsPage;
import pages.ResultsPage;
import pages.SummaryPage;

import static io.qameta.allure.SeverityLevel.CRITICAL;

/**
 * SaveATrainE2ETests class represents the test cases for end-to-end path testing on Save A Train application.
 * It extends the PlaywrightTestBase class.
 */
public class SaveATrainE2ETests extends PlaywrightTestBase {
    @BeforeEach
    public void setUp() {
        mainPage = new MainPage(page);
        passengersDetailsPage = new PassengersDetailsPage(page);
        resultsPage = new ResultsPage(page);
        summaryPage = new SummaryPage(page);
    }
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String firstNameSecondAdultPassenger = faker.name().firstName();
    String firstNameYouthPassenger = faker.name().firstName();
    String lastName = faker.name().lastName();
    String lastNameSecondAdultPassenger = faker.name().lastName();
    String lastNameYouthPassenger = faker.name().lastName();
    String email = "test_" + faker.name().firstName() + "@gmail.com";

    @ParameterizedTest
    @DisplayName("NSI provider.")
    @Description("Test checking e2e path on NSI provider.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Berlin Central Station, Hamburg Central Station",
            "Amsterdam Central Station, Antwerp Central Station"})
    public void e2e_SAT_NSI_test(String origin, String destination) {
        mainPage.navigateToHomePage();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.performSearch();
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        String departure = resultsPage.getDepartureStation_ResultPage().strip();
        String arrival = resultsPage.getArrivalStation_ResultPage();
        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime.strip() + " -";
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();
        String durationTime = resultsPage.getDurationTime_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.selectDepartureAisleOption();
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.passengersDataSubmitButtonClick();

        double delta = 0.001;
        String finalDeparture = summaryPage.getDepartureStation_SummaryPage().strip();
        String finalArrival = summaryPage.getArrivalStation_SummaryPage().strip();
        String finalDepartureDate = summaryPage.getDepartureDate_SummaryPage().strip();
        String finalArrivalDate = summaryPage.getArrivalDate_SummaryPage().strip();
        String finalPrice = summaryPage.getPrice_SummaryPage().strip();
        String finalPriceWithoutEuroSign = finalPrice.replace("€", "");
        double priceValue = Double.parseDouble(finalPriceWithoutEuroSign);
        String reservationPrice = summaryPage.getReservationPrice_SummaryPage().strip();
        String reservationPriceWithoutEuroSign = reservationPrice.replace("€", "");
        double reservationValue = Double.parseDouble(reservationPriceWithoutEuroSign);
        String totalPrice = summaryPage.getFinalPrice_SummaryPage().strip();
        String totalPriceWithoutEuroSign = totalPrice.replace("€", "");
        double totalPriceValue = Double.parseDouble(totalPriceWithoutEuroSign);
        String orderCode = summaryPage.getOrderCode().strip();
        int orderCodeLength = orderCode.length();
        String fareSummaryPage = summaryPage.getFare().strip();
        String passengerFirstName = summaryPage.getPassengerName_SummaryPage().strip();
        String passengerSurname = summaryPage.getPassengerSurname_SummaryPage();
        String passengerBirthDay = summaryPage.getPassengerBirthDate().strip();
        String passengerEmail = summaryPage.getPassengerEmail().strip();

        Assertions.assertAll(
                () -> Assertions.assertEquals(departure.toUpperCase(), finalDeparture, "Incorrect departure on summary page."),
                () -> Assertions.assertEquals(arrival.toUpperCase(), finalArrival, "Incorrect arrival on summary page."),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, "Incorrect departure date and time on summary page."),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, "Incorrect arrival date and time on summary page."),
                () -> Assertions.assertEquals(price, finalPrice, "Incorrect price on summary page."),
                () -> Assertions.assertEquals(priceValue + reservationValue, totalPriceValue, delta, "Incorrect total price on summary page."),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), "Incorrect passenger first name on summary page."),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), "Incorrect passenger last name on summary page."),
                () -> Assertions.assertEquals("06/09/1985", passengerBirthDay, "Incorrect passenger birth day on summary page."),
                () -> Assertions.assertEquals(email, passengerEmail, "Incorrect passenger email on summary page."),
                () -> Assertions.assertEquals(fare.toUpperCase(), fareSummaryPage, "Incorrect fare on summary page."),
                () -> Assertions.assertEquals(6, orderCodeLength, "Incorrect length of the order code on summary page.")
        );

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent().strip();

        Assertions.assertEquals("Thank you for purchase!", actualHeaderText.strip());

    }

    @Disabled("Disabled until bug will be repair.")
    @ParameterizedTest
    @DisplayName("NSI provider 2xAdult and 1xYouth passenger.")
    @Description("Test checking e2e path on NSI provider with different types of passenger.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Berlin Central Station, Hamburg Central Station"})
    public void e2e_SAT_NSI_twoAdultsAndOneYouth_test(String origin, String destination) {
        mainPage.navigateToHomePage();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.performSearch();
        mainPage.departureDate();
        mainPage.addOneAdultPassenger();
        mainPage.addOneYouthPassengerIn_17_YearsAge("17");
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        String departure = resultsPage.getDepartureStation_ResultPage().strip();
        String arrival = resultsPage.getArrivalStation_ResultPage();
        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime.strip() + " -";
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();
        String durationTime = resultsPage.getDurationTime_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("12/07/1985");
        passengersDetailsPage.choosePassengerCountry();

        passengersDetailsPage.secondPassengerPrefixDropdown("Mrs");
        passengersDetailsPage.enter_SecondAdultPassenger_FirstAndLastName(firstNameSecondAdultPassenger, lastNameSecondAdultPassenger);
        passengersDetailsPage.enter_SecondAdultPassenger_birthDate("22/09/1987");
        passengersDetailsPage.choose_SecondAdultPassenger_Country();

        passengersDetailsPage.thirdPassengerPrefixDropdown("Mr");
        passengersDetailsPage.enter_YouthPassenger_FirstAndLastName(firstNameYouthPassenger, lastNameYouthPassenger);
        passengersDetailsPage.enter_YouthPassenger_birthDate("01/01/2007");
        passengersDetailsPage.choose_YouthPassenger_Country();

        passengersDetailsPage.selectDepartureAisleOption();

        passengersDetailsPage.enterEmail(email);

        passengersDetailsPage.passengersDataSubmitButtonClick();

        double delta = 0.001;
        String finalDeparture = summaryPage.getDepartureStation_SummaryPage().strip();
        String finalArrival = summaryPage.getArrivalStation_SummaryPage().strip();
        String finalDepartureDate = summaryPage.getDepartureDate_SummaryPage().strip();
        String finalArrivalDate = summaryPage.getArrivalDate_SummaryPage().strip();
        String finalPrice = summaryPage.getPrice_SummaryPage().strip();
        String finalPriceWithoutEuroSign = finalPrice.replace("€", "");
        double priceValue = Double.parseDouble(finalPriceWithoutEuroSign);
        String reservationPrice = summaryPage.getReservationPrice_SummaryPage().strip();
        String reservationPriceWithoutEuroSign = reservationPrice.replace("€", "");
        double reservationValue = Double.parseDouble(reservationPriceWithoutEuroSign);
        String totalPrice = summaryPage.getFinalPrice_SummaryPage().strip();
        String totalPriceWithoutEuroSign = totalPrice.replace("€", "");
        double totalPriceValue = Double.parseDouble(totalPriceWithoutEuroSign);
        String orderCode = summaryPage.getOrderCode().strip();
        int orderCodeLength = orderCode.length();
        String fareSummaryPage = summaryPage.getFare().strip();
        String passengerFirstName = summaryPage.getPassengerName_SummaryPage().strip();
        String passengerSurname = summaryPage.getPassengerSurname_SummaryPage();
        String passengerBirthDay = summaryPage.getPassengerBirthDate().strip();
        String passengerEmail = summaryPage.getPassengerEmail().strip();

        Assertions.assertAll(
                () -> Assertions.assertEquals(departure.toUpperCase(), finalDeparture, "Incorrect departure on summary page."),
                () -> Assertions.assertEquals(arrival.toUpperCase(), finalArrival, "Incorrect arrival on summary page."),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, "Incorrect departure date and time on summary page."),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, "Incorrect arrival date and time on summary page."),
                () -> Assertions.assertEquals(price, finalPrice, "Incorrect price on summary page."),
                () -> Assertions.assertEquals(priceValue + reservationValue, totalPriceValue, delta, "Incorrect total price on summary page."),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), "Incorrect passenger first name on summary page."),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), "Incorrect passenger last name on summary page."),
                () -> Assertions.assertEquals("06/09/1985", passengerBirthDay, "Incorrect passenger birth day on summary page."),
                () -> Assertions.assertEquals(email, passengerEmail, "Incorrect passenger email on summary page."),
                () -> Assertions.assertEquals(fare.toUpperCase(), fareSummaryPage, "Incorrect fare on summary page."),
                () -> Assertions.assertEquals(6, orderCodeLength, "Incorrect length of the order code on summary page.")
        );

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent().strip();

        Assertions.assertEquals("Thank you for purchase!", actualHeaderText.strip());
    }

    @ParameterizedTest
    @DisplayName("TI provider.")
    @Description("Test checking e2e path on TI provider.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Rome Termini, Florence Santa Maria Novella",
            "Milan Central Station, Rimini Central Station"})
    public void e2e_SAT_TI_test(String origin, String destination) {
        mainPage.navigateToHomePage();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.performSearch();
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        String departure = resultsPage.getDepartureStation_ResultPage().strip();
        String arrival = resultsPage.getArrivalStation_ResultPage();
        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime.strip() + " -";
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();
        String durationTime = resultsPage.getDurationTime_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix("Mr");
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterbirthDate("06/09/1985");
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.passengersDataSubmitButtonClick();

        String finalDeparture = summaryPage.getDepartureStation_SummaryPage().strip();
        String finalArrival = summaryPage.getArrivalStation_SummaryPage().strip();
        String finalDepartureDate = summaryPage.getDepartureDate_SummaryPage().strip();
        String finalArrivalDate = summaryPage.getArrivalDate_SummaryPage().strip();
        String finalPrice = summaryPage.getPrice_SummaryPage().strip();
        String reservationPrice = summaryPage.getReservationPrice_SummaryPage().strip();
        String totalPrice = summaryPage.getFinalPrice_SummaryPage().strip();
        String orderCode = summaryPage.getOrderCode().strip();
        int orderCodeLength = orderCode.length();
        String fareSummaryPage = summaryPage.getFare().strip();
        String passengerFirstName = summaryPage.getPassengerName_SummaryPage().strip();
        String passengerSurname = summaryPage.getPassengerSurname_SummaryPage();
        String passengerBirthDay = summaryPage.getPassengerBirthDate().strip();
        String passengerEmail = summaryPage.getPassengerEmail().strip();

        Assertions.assertAll(
                () -> Assertions.assertEquals(departure.toUpperCase(), finalDeparture, "Incorrect departure on summary page."),
                () -> Assertions.assertEquals(arrival.toUpperCase(), finalArrival, "Incorrect arrival on summary page."),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, "Incorrect departure date and time on summary page."),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, "Incorrect arrival date and time on summary page."),
                () -> Assertions.assertEquals(price, finalPrice, "Incorrect price on summary page."),
                () -> Assertions.assertEquals("€0.00", reservationPrice, "Incorrect reservation price on summary page."),
                () -> Assertions.assertEquals(price, totalPrice, "Incorrect total price on summary page."),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), "Incorrect passenger first name on summary page."),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), "Incorrect passenger last name on summary page."),
                () -> Assertions.assertEquals("06/09/1985", passengerBirthDay, "Incorrect passenger birth day on summary page."),
                () -> Assertions.assertEquals(email, passengerEmail, "Incorrect passenger email on summary page."),
                () -> Assertions.assertEquals(fare.toUpperCase(), fareSummaryPage, "Incorrect fare on summary page."),
                () -> Assertions.assertEquals(6, orderCodeLength, "Incorrect length of the order code on summary page.")
        );

        // Sleep for 7 seconds
        page.waitForTimeout(7000);

        summaryPage.completingAdyenForm();

        // Find the h3 element and get its text
        String actualHeaderText = page.locator("css=h3").textContent().trim();

        Assertions.assertEquals("Thank you for purchase!", actualHeaderText.strip());

    }
}
