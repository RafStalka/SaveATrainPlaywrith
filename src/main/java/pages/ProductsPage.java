package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

public class ProductsPage {

    public Page page;

    public ProductsPage(Page page) {
        this.page = page;
    }

    public String getProductsPageTitle() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.click("id=products");
        String title =  page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getProductsPageURL() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.click("id=products");
        page.waitForTimeout(2000);
        String url =  page.url();
        System.out.println("page url : " + url);
        return url;
    }
}
