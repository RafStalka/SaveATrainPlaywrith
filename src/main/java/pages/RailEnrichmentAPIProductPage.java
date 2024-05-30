package pages;

import com.microsoft.playwright.Page;

public class RailEnrichmentAPIProductPage {

    private Page page;

    public RailEnrichmentAPIProductPage(Page page) {
        this.page = page;
    }

    public String getRailEnrichmentAPIProductPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getRailEnrichmentAPIProductPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
