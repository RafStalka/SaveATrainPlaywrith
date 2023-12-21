package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The RailAgentDashboardProductPage class represents a page in the rail agent dashboard containing information about a specific product.
 */
public class RailAgentDashboardProductPage {
    public static final String PRODUCTS_TAB_LOCATOR = "id=products";
    public static final String DASHBOARD_LOCATOR = "id=rail-agent-dashboard";

    public Page page;

    public RailAgentDashboardProductPage(Page page) {
        this.page = page;
    }

    // private helper method to navigate and click on elements
    private void navigateAndClick() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        // User clicks on products tab
        page.click(PRODUCTS_TAB_LOCATOR);
        // User clicks on rail book api
        page.click(DASHBOARD_LOCATOR);
    }

    public String getRailAgentDashboardProductPageTitle() {
        navigateAndClick();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailAgentDashboardProductPageURL() {
        navigateAndClick();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
