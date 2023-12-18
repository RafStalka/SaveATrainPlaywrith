package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The RailAffiliateProductPage class represents a page object for the rail affiliate product page.
 */
public class RailAffiliateProductPage {
    private static final String PRODUCTS_TAB_LOCATOR = "id=products";
    private static final String RAIL_AFFILIATE_LOCATOR = "id=rail-affiliate";

    private Page page;

    public RailAffiliateProductPage(Page page) {
        this.page = page;
    }

    private void navigateToRailAffiliatePage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.click(PRODUCTS_TAB_LOCATOR);  // User clicks on products tab
        page.click(RAIL_AFFILIATE_LOCATOR);  // User clicks on rail book api
    }

    public String getRailAffiliateProductPageTitle() {
        navigateToRailAffiliatePage();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailAffiliatePageURL() {
        navigateToRailAffiliatePage();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
