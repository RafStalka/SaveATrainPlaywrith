package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class RailEnrichmentAPIProductPage {

    private static final String PRODUCTS_TAB_LOCATOR = "id=products";
    private static final String RAIL_ENRICHMENT_LOCATOR = "id=rail-enrichment";

    private Page page;

    public RailEnrichmentAPIProductPage(Page page) {
        this.page = page;
    }

    private void navigateToRailEnrichmentAPIProductPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.click(PRODUCTS_TAB_LOCATOR);  // User clicks on products tab
        page.click(RAIL_ENRICHMENT_LOCATOR);  // User clicks on rail enrichment api
    }

    public String getRailEnrichmentAPIProductPageTitle() {
        navigateToRailEnrichmentAPIProductPage();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailEnrichmentAPIProductPageURL() {
        navigateToRailEnrichmentAPIProductPage();
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
