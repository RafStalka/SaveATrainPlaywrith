package pages;

import com.microsoft.playwright.Page;

/**
 * The FAQHelpTabPage class represents the FAQ help tab page in a web application.
 * It provides methods to retrieve the title and URL of the FAQ page.
 */
public class FAQHelpTabPage {

    public Page page;

    public FAQHelpTabPage(Page page) {
        this.page = page;
    }

    public String getFAQHelpTabPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getFAQPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
