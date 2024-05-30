package pages;

import com.microsoft.playwright.Page;

/**
 * This class represents the Rail Search API Product Page.
 */
public class RailSearchApiProductPage {

    private Page page;

    public RailSearchApiProductPage(Page page) {
        this.page = page;
    }

    public String getRailSearchApiPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailSearchApiPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
