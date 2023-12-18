package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * This class represents a Rail Forward API page.
 */
public class RailForwardApiPage {

    public static final String ID_PRODUCTS = "id=products";
    public static final String ID_RAIL_FORWARD_API = "id=rail-forward-api";

    private final Page page;

    public RailForwardApiPage(final Page page) {
        this.page = page;
    }

    public String getRailForwardApiPageTitle() {
        navigateToRailForwardApiPage();
        final String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailForwardApiPageURL() {
        navigateToRailForwardApiPage();
        final String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }

    private void navigateToRailForwardApiPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.click(ID_PRODUCTS);
        page.click(ID_RAIL_FORWARD_API);
    }
}
