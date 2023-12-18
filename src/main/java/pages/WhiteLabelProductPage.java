package pages;

import com.microsoft.playwright.Page;
import constans.AppConstants;

/**
 * The WhiteLabelProductPage class represents a page that contains the white-label products.
 */
public class WhiteLabelProductPage {
    private static final String PRODUCTS_TAB_ID = "id=products";
    private static final String WHITE_LABEL_PRODUCT_ID = "id=white-label";

    private Page page;

    public WhiteLabelProductPage(Page page) {
        this.page = page;
    }

    private void navigateToWhiteLabelProductPage() {
        page.navigate(AppConstants.SAT_HOME_PAGE);
        page.click(PRODUCTS_TAB_ID);
        page.click(WHITE_LABEL_PRODUCT_ID);
    }

    public String getWhiteLabelProductPageTitle() {
        navigateToWhiteLabelProductPage();
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getWhiteLabelPageURL() {
        navigateToWhiteLabelProductPage();
        String url = page.url();
        System.out.println("page url: " + url);
        return url;
    }
}
