package pages;

import com.microsoft.playwright.Page;

/**
 * The WhiteLabelProductPage class represents a page that contains the white-label products.
 */
public class WhiteLabelProductPage {

    private Page page;

    public WhiteLabelProductPage(Page page) {
        this.page = page;
    }

    public String getWhiteLabelProductPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getWhiteLabelPageURL() {
        String url = page.url();
        System.out.println("page url: " + url);
        return url;
    }
}
