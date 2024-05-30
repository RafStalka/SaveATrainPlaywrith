package pages;

import com.microsoft.playwright.Page;

/**
 * The EurailProductPage class represents a page that displays Eurail products.
 */
public class EurailProductPage {
    private final Page page;

    public EurailProductPage(Page page) {
        this.page = page;
    }

    public String getEurailProductPageTitle() {
        return page.title();
    }

    public String getEurailPassPageURL() {
        return page.url();
    }
}
