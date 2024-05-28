package pages;

import com.microsoft.playwright.Page;

/**
 * The RailBookApiProductPage class represents a page that contains rail book API information.
 */
public class RailBookApiProductPage {

    public Page page;

    public RailBookApiProductPage(Page page) {
        this.page = page;
    }

    public String getRailBookApiPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailBookApiPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
