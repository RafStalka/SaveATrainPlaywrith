package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * This class represents the Rail Search API Product Page.
 */
public class RailSearchApiProductPage {
    public static final String PRODUCTS_TAB_LOCATOR = "id=products";
    public static final String RAIL_SEARCH_API_LOCATOR = "id=rail-search-api";

    private Page page;

    public RailSearchApiProductPage(Page page) {
        this.page = page;
    }

    private void navigateToRailSearchApiPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on products tab
        page.click(PRODUCTS_TAB_LOCATOR);
        // User clicks on rail search api
        page.click(RAIL_SEARCH_API_LOCATOR);
    }

    public String getRailSearchApiPageTitle() {
        navigateToRailSearchApiPage();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailSearchApiPageURL() {
        navigateToRailSearchApiPage();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
