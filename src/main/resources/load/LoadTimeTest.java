package load;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadTimeTest {
    private static final BrowserType browserType = Playwright.create().chromium();
    private static Browser browser;

    public static final String BASE_URL = "https://www.saveatrain.com";

    public static final List<String> LANGUAGES = Arrays.asList(
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
    public static final List<String> ENDPOINTS = Arrays.asList(
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

    private Map<String, Long> pageLoadTimesFirstRun = new HashMap<>();
    private Map<String, Long> pageLoadTimesSecondRun = new HashMap<>();

    @BeforeAll
    static void setUpBeforeClass() {
        browser = browserType.launch();
    }

    @AfterAll
    static void tearDownAfterClass() {
        if (browser != null) {
            browser.close();
        }
    }


    @Test
    void testLoadTime() {
        // First run
        executeLoadTest(pageLoadTimesFirstRun);
        // Second run
        executeLoadTest(pageLoadTimesSecondRun);

        // Print differences
        System.out.println("Differences in load times:");
        for (String url : pageLoadTimesFirstRun.keySet()) {
            long firstRunTime = pageLoadTimesFirstRun.get(url);
            long secondRunTime = pageLoadTimesSecondRun.get(url);
            System.out.println("Load time for " + url + ", 1st run: " + firstRunTime + "ms, 2nd run: "
                    + secondRunTime + "ms, difference: " + (firstRunTime-secondRunTime) + "ms");
        }
    }

    private void executeLoadTest(Map<String, Long> loadTimes) {
        Page page = browser.newPage();

        for (String lang : LANGUAGES) {
            for (String endpoint : ENDPOINTS) {
                long startTime = System.currentTimeMillis();
                String url = BASE_URL + lang + endpoint;
                page.navigate(url);
                page.waitForLoadState(LoadState.LOAD);
                long endTime = System.currentTimeMillis();
                long loadTime = endTime - startTime;

                // Store the result in the map
                loadTimes.put(url, loadTime);

                System.out.println("Page load time for " + lang + endpoint + ": " + loadTime + "ms");
            }
        }

        page.close();
    }
}
