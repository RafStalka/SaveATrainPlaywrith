package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The RailBookApiProductPage class represents a page that contains rail book API information.
 */
public class RailBookApiProductPage {
    public Page page;
    private static final String PRODUCTS_TAB_ID = "id=products";
    private static final String RAIL_BOOK_API_ID = "id=rails-book-api";

    public RailBookApiProductPage(Page page) {
        this.page = page;
    }

    private Page navigateToRailBookApiPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on products tab
        page.click(PRODUCTS_TAB_ID);
        // User clicks on rail book api
        page.click(RAIL_BOOK_API_ID);

        return page;
    }

    public String getRailBookApiPageTitle() {
        Page page = navigateToRailBookApiPage();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailBookApiPageURL() {
        Page page = navigateToRailBookApiPage();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
