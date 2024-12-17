package com.example.saveatrainplaywrith;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.MainPage;
import pages.PassengersDetailsPage;
import pages.ResultsPage;
import pages.SummaryPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static io.qameta.allure.SeverityLevel.CRITICAL;

public class NewAttemptForAllTests extends PlaywrightTestBase {

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

    public static final String BASE_URL = "https://sat-client-staging.saveatrain.com";

    public static List<String> LANGUAGES = Arrays.asList(
            "/en",
            "/es",
            "/de",
            "/pl",
            "/ru",
            "/zh-cn",
            "/tr",
            "/ja",
            "/nl",
            "/fr"
    );
    public static List<String> ENDPOINTS = Arrays.asList(
            "/rome-to-milan",
            "/Rome_All_Stations-to-Florence_All_Stations",
            "/Rome_All_Stations-to-Naples_Central_Station",
            "/Rome_All_Stations-to-Milan_Central_Station",
            "/Rome_Termini-to-Airport_Rome_Fiumicino",
            "/Milan_Central_Station-to-Florence_All_Stations",
            "/Milan_Central_Station-to-Rome_All_Stations",
            "/Milan_Central_Station-to-Bologna_All_Stations",
            "/Berlin_Central_Station-to-Zurich_Central_Station",
            "/Geneva-to-Zermatt",
            "/Baden_Baden-to-Strasbourg",
            "/London_St_Pancras_International-to-Ashford",
            "/London_St_Pancras_International-to-Paris_All_Stations",
            "/London_St_Pancras_International-to-Ebbsfleet_International",
            "/London_St_Pancras_International-to-Amsterdam_Central_Station",
            "/Berlin_Central_Station-to-Amsterdam_Central_Station",
            "/Berlin_Central_Station-to-Hamburg_Central_Station",
            "/Berlin_Central_Station-to-Munich_Central_Station",
            "/Frankfurt-to-Berlin",
            "/Brussels_Midi_South-to-Amsterdam_Central_Station",
            "/Brussels_Midi_South-to-Paris_All_Stations",
            "/Paris_All_Stations-to-London_St_Pancras_International",
            "/Paris_All_Stations-to-Amsterdam_Central_Station",
            "/Paris_All_Stations-to-Strasbourg",
            "/Paris_All_Stations-to-Rennes",
            "/Paris_All_Stations-to-Brussels_Central_Station",
            "/Amsterdam_Central_Station-to-Paris_All_Stations",
            "/Amsterdam_Central_Station-to-London_St_Pancras_International",
            "/Amsterdam_Central_Station-to-Brussels_Central_Station",
            "/Amsterdam_Central_Station-to-Berlin_Central_Station",
            "/Amsterdam_Central_Station-to-Antwerp_Central_Station",
            "/Antwerp_Central_Station-to-Charleroi_West",
            "/amsterdam-to-london"
    );

    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String firstNameSecondAdultPassenger = faker.name().firstName();
    String firstNameYouthPassenger = faker.name().firstName();
    String lastName = faker.name().lastName();
    String lastNameSecondAdultPassenger = faker.name().lastName();
    String lastNameYouthPassenger = faker.name().lastName();
    String email = TEST + faker.name().firstName() + GMAIL_COM;

    @BeforeEach
    public void setUp() {
        mainPage = new MainPage(page);
        passengersDetailsPage = new PassengersDetailsPage(page);
        resultsPage = new ResultsPage(page);
        summaryPage = new SummaryPage(page);
    }

    public static List<String> generateUrls() {
        List<String> urls = new ArrayList<>();
        for (String language : LANGUAGES) {
            for (String endpoint : ENDPOINTS) {
                urls.add(BASE_URL + language + endpoint);
            }
        }
        return urls;
    }

    public static Stream<String> generateUrlsStream() {
        List<String> urls = generateUrls();
        return urls.stream();
    }

    @Disabled("Time reasosns.")
    @ParameterizedTest(name = "Testing URL: {0}")
    @DisplayName("NSI provider.")
    @Description("Test checking e2e path on NSI provider.")
    @Severity(CRITICAL)
    @Owner("Save A Train")
    @MethodSource("generateUrlsStream")
    public void e2e_SAT_test(String url) {
        try {
            mainPage.navigateToUrls(url);

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
            passengersDetailsPage.enterYouthPassengerBirthDate(27);
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

            // Sleep for 7 seconds
            page.waitForTimeout(7000);

            System.out.println(page.url());
            summaryPage.completingAdyenForm();

            // Find the h3 element and get its text
            String actualHeaderText = page.locator("css=h3").textContent().strip();

            Assertions.assertEquals(YOU_FOR_PURCHASE, actualHeaderText.strip());

        } catch (Exception e) {
            System.err.println("Error processing URL " + url + ": " + e.getMessage());
        }

    }

    public static String expectedDateOfBirth(int dateOfBirth) {
        LocalDate expectedDateOfBirth = LocalDate.now().minusYears(dateOfBirth);
        return expectedDateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
