package pages;

import com.microsoft.playwright.Page;

/**
 * The RailAffiliateProductPage class represents a page object for the rail affiliate product page.
 */
public class RailAffiliateProductPage {

    private Page page;

    public RailAffiliateProductPage(Page page) {
        this.page = page;
    }

    public String getRailAffiliateProductPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailAffiliatePageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
