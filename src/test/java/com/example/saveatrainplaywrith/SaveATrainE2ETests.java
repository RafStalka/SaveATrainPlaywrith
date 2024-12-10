package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import static io.qameta.allure.SeverityLevel.CRITICAL;

/**
 * SaveATrainE2ETests class is responsible for performing end-to-end tests related to the Save A Train application.
 * It extends the PlaywrightTestBase class and inherits its fields and methods.
 * Class Fields:
 * - faker: An instance of the Faker class for generating fake data.
 * - firstName: The first name of the first adult passenger.
 * - firstNameSecondAdultPassenger: The first name of the second adult passenger.
 * - firstNameYouthPassenger: The first name of the youth passenger.
 * - lastName: The last name of the first adult passenger.
 * - lastNameSecondAdultPassenger: The last name of the second adult passenger.
 * - lastNameYouthPassenger: The last name of the youth passenger.
 * - email: The email address of the passenger.
 * Class Methods:
 * - setUp(): Annotated method that runs before each test method in the class to set up the test environment.
 * - e2e_SAT_NSI_test(origin: String, destination: String): Annotated parameterized test method that checks
 * the end-to-end path on the NSI provider. It takes the origin and destination as input parameters.
 * - e2e_SAT_NSI_twoAdultsAndOneYouth_test(origin: String, destination: String): Annotated parameterized test method
 * that checks the end-to-end path on the NSI provider with two adult passengers and one youth passenger.
 * It takes the origin and destination as input parameters.
 * - e2e_SAT_TI_test(origin: String, destination: String): Annotated parameterized test method that checks
 * the end-to-end path on the TI provider. It takes the origin and destination as input parameters.
 * Super Classes:
 * - PlaywrightTestBase: A base class for Playwright tests that provides common fields and methods related to the Save A Train application.
 * Note: This class does not contain any example code, @author, or @version tags as requested.
 */
public class SaveATrainE2ETests extends PlaywrightTestBase {

    public static final String BIRTH_DATE = "06/09/1985";
    public static final String TEST = "test_";
    public static final String GMAIL_COM = "@gmail.com";
    public static final String MALE_VALUE = "Mr";
    public static final String CURRENCY = "€";
    public static final String YOU_FOR_PURCHASE = "Thank you for purchase!";
    public static final String INCORRECT_DEPARTURE_ON_SUMMARY_PAGE = "Incorrect departure on summary page.";
    public static final String INCORRECT_ARRIVAL_ON_SUMMARY_PAGE = "Incorrect arrival on summary page.";
    public static final String INCORRECT_DEPARTURE_DATE_AND_TIME_ON_SUMMARY_PAGE = "Incorrect departure date and time on summary page.";
    public static final String INCORRECT_ARRIVAL_DATE_AND_TIME_ON_SUMMARY_PAGE = "Incorrect arrival date and time on summary page.";
    public static final String INCORRECT_PRICE_ON_SUMMARY_PAGE = "Incorrect price on summary page.";
    public static final String INCORRECT_TOTAL_PRICE_ON_SUMMARY_PAGE = "Incorrect total price on summary page.";
    public static final String INCORRECT_PASSENGER_FIRST_NAME_ON_SUMMARY_PAGE = "Incorrect passenger first name on summary page.";
    public static final String INCORRECT_PASSENGER_LAST_NAME_ON_SUMMARY_PAGE = "Incorrect passenger last name on summary page.";
    public static final String INCORRECT_PASSENGER_BIRTH_DAY_ON_SUMMARY_PAGE = "Incorrect passenger birth day on summary page.";
    public static final String INCORRECT_PASSENGER_EMAIL_ON_SUMMARY_PAGE = "Incorrect passenger email on summary page.";
    public static final String INCORRECT_FARE_ON_SUMMARY_PAGE = "Incorrect fare on summary page.";
    public static final String INCORRECT_LENGTH_OF_THE_ORDER_CODE_ON_SUMMARY_PAGE = "Incorrect length of the order code on summary page.";
    public static final String YOUTH_AGE = "17";
    public static final String FEMALE_VALUE = "Mrs";
    public static final String ADULT_PASSENGER_BIRTH_DATE = "22/09/1987";
    public static final String YOUTH_PASSENGER_BIRTH_DATE = "01/01/2007";

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
    String email = TEST + faker.name().firstName() + GMAIL_COM;

    @ParameterizedTest
    @DisplayName("NSI provider.")
    @Description("Test checking e2e path on NSI provider.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @CsvSource({"Berlin Central Station, Hamburg Central Station",
            "Amsterdam Central Station, Antwerp Central Station"})
    public void e2e_SAT_NSI_test(String origin, String destination) {
        mainPage.navigateToHomePage();
        mainPage.performSearch();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        String departureFirst = resultsPage.getDepartureStationFirstField_ResultPage().trim();

        String arrival = null;
        try {
            Locator arrivalSecond = page.locator("id=arrival-transfer-station-name-1-2");
            if (arrivalSecond.isVisible()) {
                arrival = resultsPage.getArrivalStationSecondField_ResultPage().strip(); // second
            } else {
                arrival = resultsPage.getArrivalStationFirstField_ResultPage().strip(); // first
            }
        } catch (Exception e) {
            System.err.println("Timeout while waiting for the element: " + e.getMessage());
        }

        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime.strip() + " -";
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix(MALE_VALUE);
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterFirstAdultBirthDate(27);
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.selectDepartureAisleOption();
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.enterMobilePhone("500135432");
        passengersDetailsPage.passengersDataSubmitButtonClick();

        double delta = 0.001;
        String finalDeparture = summaryPage.getDepartureStation_SummaryPage().strip();
        String finalArrival = summaryPage.getArrivalStation_SummaryPage().strip();
        String finalDepartureDate = summaryPage.getDepartureDate_SummaryPage().strip();
        String finalArrivalDate = summaryPage.getArrivalDate_SummaryPage().strip();
        String finalPrice = summaryPage.getPrice_SummaryPage().strip();
        String finalPriceWithoutEuroSign = finalPrice.replace(CURRENCY, "");
        double priceValue = Double.parseDouble(finalPriceWithoutEuroSign);
        String reservationPrice = summaryPage.getReservationPrice_SummaryPage().strip();
        String reservationPriceWithoutEuroSign = reservationPrice.replace(CURRENCY, "");
        double reservationValue = Double.parseDouble(reservationPriceWithoutEuroSign);
        String totalPrice = summaryPage.getFinalPrice_SummaryPage().strip();
        String totalPriceWithoutEuroSign = totalPrice.replace(CURRENCY, "");
        double totalPriceValue = Double.parseDouble(totalPriceWithoutEuroSign);
        String orderCode = summaryPage.getOrderCode().strip();
        int orderCodeLength = orderCode.length();
        String fareSummaryPage = summaryPage.getFare().strip();
        String passengerFirstName = summaryPage.getPassengerName_SummaryPage().strip();
        String passengerSurname = summaryPage.getPassengerSurname_SummaryPage();
        String passengerBirthDay = summaryPage.getPassengerBirthDate().strip();
        String passengerEmail = summaryPage.getPassengerEmail().strip();

        boolean compareDeparture = Objects.equals(departureFirst != null ?
                departureFirst.trim().toLowerCase() : null , finalDeparture != null ?
                finalDeparture.trim().toLowerCase() : null);
        boolean compareArrival = Objects.equals(arrival != null ? arrival.trim().toLowerCase() : null,
                finalArrival != null ? finalArrival.trim().toLowerCase() : null);

        Assertions.assertAll(
                () -> Assertions.assertTrue(compareDeparture, INCORRECT_DEPARTURE_ON_SUMMARY_PAGE),
                () -> Assertions.assertTrue(compareArrival, INCORRECT_ARRIVAL_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, INCORRECT_DEPARTURE_DATE_AND_TIME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, INCORRECT_ARRIVAL_DATE_AND_TIME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(price, finalPrice, INCORRECT_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(priceValue + reservationValue, totalPriceValue, delta, INCORRECT_TOTAL_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), INCORRECT_PASSENGER_FIRST_NAME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), INCORRECT_PASSENGER_LAST_NAME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(expectedDateOfBirth(27), passengerBirthDay, INCORRECT_PASSENGER_BIRTH_DAY_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(email, passengerEmail, INCORRECT_PASSENGER_EMAIL_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(fare.toUpperCase(), fareSummaryPage, INCORRECT_FARE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(6, orderCodeLength, INCORRECT_LENGTH_OF_THE_ORDER_CODE_ON_SUMMARY_PAGE)
        );

        page.waitForLoadState(LoadState.LOAD);
        System.out.println(page.url());

        summaryPage.completingAdyenForm();
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
        mainPage.performSearch();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.departureDate();
        mainPage.addOneAdultPassenger();
        mainPage.addOneYouthPassengerIn_17_YearsAge(YOUTH_AGE);
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        String departureFirst = resultsPage.getDepartureStationFirstField_ResultPage().trim();

        String arrival = null;
        try {
            Locator arrivalSecond = page.locator("id=arrival-transfer-station-name-1-2");
            if (arrivalSecond.isVisible()) {
                arrival = resultsPage.getArrivalStationSecondField_ResultPage().strip(); // second
            } else {
                arrival = resultsPage.getArrivalStationFirstField_ResultPage().strip(); // first
            }
        } catch (Exception e) {
            System.err.println("Timeout while waiting for the element: " + e.getMessage());
        }

        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime.strip() + " -";
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix(MALE_VALUE);
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterFirstAdultBirthDate(27);
        passengersDetailsPage.choosePassengerCountry();

        passengersDetailsPage.secondPassengerPrefixDropdown(FEMALE_VALUE);
        passengersDetailsPage.enter_SecondAdultPassenger_FirstAndLastName(firstNameSecondAdultPassenger, lastNameSecondAdultPassenger);
        passengersDetailsPage.enterSecondAdultPassengerBirthDate(28);
        passengersDetailsPage.choose_SecondAdultPassenger_Country();

        passengersDetailsPage.thirdPassengerPrefixDropdown(MALE_VALUE);
        passengersDetailsPage.enter_YouthPassenger_FirstAndLastName(firstNameYouthPassenger, lastNameYouthPassenger);
        passengersDetailsPage.enterYouthPassengerBirthDate(17);
        passengersDetailsPage.choose_YouthPassenger_Country();

        passengersDetailsPage.selectDepartureAisleOption();

        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.enterMobilePhone("500135432");

        passengersDetailsPage.passengersDataSubmitButtonClick();

        double delta = 0.001;
        String finalDeparture = summaryPage.getDepartureStation_SummaryPage().strip();
        String finalArrival = summaryPage.getArrivalStation_SummaryPage().strip();
        String finalDepartureDate = summaryPage.getDepartureDate_SummaryPage().strip();
        String finalArrivalDate = summaryPage.getArrivalDate_SummaryPage().strip();
        String finalPrice = summaryPage.getPrice_SummaryPage().strip();
        String finalPriceWithoutEuroSign = finalPrice.replace(CURRENCY, "");
        double priceValue = Double.parseDouble(finalPriceWithoutEuroSign);
        String reservationPrice = summaryPage.getReservationPrice_SummaryPage().strip();
        String reservationPriceWithoutEuroSign = reservationPrice.replace(CURRENCY, "");
        double reservationValue = Double.parseDouble(reservationPriceWithoutEuroSign);
        String totalPrice = summaryPage.getFinalPrice_SummaryPage().strip();
        String totalPriceWithoutEuroSign = totalPrice.replace(CURRENCY, "");
        double totalPriceValue = Double.parseDouble(totalPriceWithoutEuroSign);
        String orderCode = summaryPage.getOrderCode().strip();
        int orderCodeLength = orderCode.length();
        String fareSummaryPage = summaryPage.getFare().strip();
        String passengerFirstName = summaryPage.getPassengerName_SummaryPage().strip();
        String passengerSurname = summaryPage.getPassengerSurname_SummaryPage();
        String passengerBirthDay = summaryPage.getPassengerBirthDate().strip();
        String passengerEmail = summaryPage.getPassengerEmail().strip();

        boolean compareDeparture = Objects.equals(departureFirst != null ?
                departureFirst.trim().toLowerCase() : null , finalDeparture != null ?
                finalDeparture.trim().toLowerCase() : null);
        boolean compareArrival = Objects.equals(arrival != null ? arrival.trim().toLowerCase() : null,
                finalArrival != null ? finalArrival.trim().toLowerCase() : null);

        Assertions.assertAll(
                () -> Assertions.assertTrue(compareDeparture, INCORRECT_DEPARTURE_ON_SUMMARY_PAGE),
                () -> Assertions.assertTrue(compareArrival, INCORRECT_ARRIVAL_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, INCORRECT_DEPARTURE_DATE_AND_TIME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, INCORRECT_ARRIVAL_DATE_AND_TIME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(price, finalPrice, INCORRECT_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(priceValue + reservationValue, totalPriceValue, delta, INCORRECT_TOTAL_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), INCORRECT_PASSENGER_FIRST_NAME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), INCORRECT_PASSENGER_LAST_NAME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(expectedDateOfBirth(27), passengerBirthDay, INCORRECT_PASSENGER_BIRTH_DAY_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(email, passengerEmail, INCORRECT_PASSENGER_EMAIL_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(fare.toUpperCase(), fareSummaryPage, INCORRECT_FARE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(6, orderCodeLength, INCORRECT_LENGTH_OF_THE_ORDER_CODE_ON_SUMMARY_PAGE)
        );

        page.waitForLoadState(LoadState.LOAD);
        System.out.println(page.url());

        summaryPage.completingAdyenForm();
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
        mainPage.performSearch();
        mainPage.complementingTheOriginStations(origin);
        mainPage.complementingTheDestinationStations(destination);
        mainPage.departureDate();
        mainPage.findMyTicketButtonClick();

        resultsPage.selectFirstOption();

        String departureFirst = resultsPage.getDepartureStationFirstField_ResultPage().trim();

        String arrival = null;
        try {
            Locator arrivalSecond = page.locator("id=arrival-transfer-station-name-1-2");
            if (arrivalSecond.isVisible()) {
                arrival = resultsPage.getArrivalStationSecondField_ResultPage().strip(); // second
            } else {
                arrival = resultsPage.getArrivalStationFirstField_ResultPage().strip(); // first
            }
        } catch (Exception e) {
            System.err.println("Timeout while waiting for the element: " + e.getMessage());
        }

        String departureTime = resultsPage.getFirstResultDepartureTime_ResultPage();
        String departureDate = resultsPage.getFirstResultDepartureDate_ResultPage().strip() + ", " + departureTime.strip() + " -";
        String arrivalTime = resultsPage.getFirstResultArrivalTime_ResultPage();
        String arrivalDate = resultsPage.getFirstResultArrivalDate_ResultPage().strip() + ", " + arrivalTime;
        String price = resultsPage.getPriceFirstResult_ResultPage().strip();
        String fare = resultsPage.getFere_ResultPage().strip();

        resultsPage.proceed();

        passengersDetailsPage.selectFirstPassengerPrefix(MALE_VALUE);
        passengersDetailsPage.enterFirstAndLastName(firstName, lastName);
        passengersDetailsPage.enterFirstAdultBirthDate(27);
        passengersDetailsPage.choosePassengerCountry();
        passengersDetailsPage.enterEmail(email);
        passengersDetailsPage.enterMobilePhone("500135432");
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

        boolean compareDeparture = Objects.equals(departureFirst != null ?
                departureFirst.trim().toLowerCase() : null , finalDeparture != null ?
                finalDeparture.trim().toLowerCase() : null);
        boolean compareArrival = Objects.equals(arrival != null ? arrival.trim().toLowerCase() : null,
                finalArrival != null ? finalArrival.trim().toLowerCase() : null);

        Assertions.assertAll(
                () -> Assertions.assertTrue(compareDeparture, INCORRECT_DEPARTURE_ON_SUMMARY_PAGE),
                () -> Assertions.assertTrue(compareArrival, INCORRECT_ARRIVAL_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(departureDate, finalDepartureDate, INCORRECT_DEPARTURE_DATE_AND_TIME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(arrivalDate, finalArrivalDate, INCORRECT_ARRIVAL_DATE_AND_TIME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(price, finalPrice, INCORRECT_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals("€0.00", reservationPrice, "Incorrect reservation price on summary page."),
                () -> Assertions.assertEquals(price, totalPrice, INCORRECT_TOTAL_PRICE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(passengerFirstName, firstName.toUpperCase(), INCORRECT_PASSENGER_FIRST_NAME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(passengerSurname, lastName.toUpperCase(), INCORRECT_PASSENGER_LAST_NAME_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(expectedDateOfBirth(27), passengerBirthDay, INCORRECT_PASSENGER_BIRTH_DAY_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(email, passengerEmail, INCORRECT_PASSENGER_EMAIL_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(fare.toUpperCase(), fareSummaryPage, INCORRECT_FARE_ON_SUMMARY_PAGE),
                () -> Assertions.assertEquals(6, orderCodeLength, INCORRECT_LENGTH_OF_THE_ORDER_CODE_ON_SUMMARY_PAGE)
        );

        page.waitForLoadState(LoadState.LOAD);
        System.out.println(page.url());

        summaryPage.completingAdyenForm();
    }

    public static String expectedDateOfBirth(int dateOfBirth) {
        LocalDate expectedDateOfBirth = LocalDate.now().minusYears(dateOfBirth);
        return expectedDateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
