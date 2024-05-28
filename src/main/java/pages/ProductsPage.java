package pages;

import com.microsoft.playwright.Page;

public class ProductsPage {

    public Page page;

    public ProductsPage(Page page) {
        this.page = page;
    }

    public String getProductsPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getProductsPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
