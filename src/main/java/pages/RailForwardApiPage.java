package pages;

import com.microsoft.playwright.Page;

/**
 * This class represents a Rail Forward API page.
 */
public class RailForwardApiPage {

    private final Page page;

    public RailForwardApiPage(final Page page) {
        this.page = page;
    }

    public String getRailForwardApiPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailForwardApiPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
