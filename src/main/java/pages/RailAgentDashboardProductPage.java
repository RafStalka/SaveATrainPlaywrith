package pages;

import com.microsoft.playwright.Page;

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

    public String getRailAgentDashboardProductPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailAgentDashboardProductPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
