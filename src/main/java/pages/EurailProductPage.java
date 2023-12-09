package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The EurailProductPage class represents a page that displays Eurail products.
 */
public class EurailProductPage {
    private final Page page;
    private static final String PRODUCTS_SELECTOR = "id=products";
    private static final String EURAIL_PASS_SELECTOR = "id=eurail-pass";

    public EurailProductPage(Page page) {
        this.page = page;
        navigateToEurailProductPage();
    }

    private void navigateToEurailProductPage() {
        this.page.navigate(AppConstants.SAT_HOME_PAGE);
        this.page.click(PRODUCTS_SELECTOR);
        this.page.click(EURAIL_PASS_SELECTOR);
    }

    public String getEurailProductPageTitle() {
        return this.page.title();
    }

    public String getEurailPassPageURL() {
        return this.page.url();
    }
}
