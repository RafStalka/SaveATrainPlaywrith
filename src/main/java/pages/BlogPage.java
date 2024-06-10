package pages;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.Assertions;

public class BlogPage {

    private final Page page;

    public BlogPage(Page page) {
        this.page = page;
    }

    public String getBlogPageTitle() {
        String title = page.title();
        System.out.println("page title: " + title);
        return title;
    }

    public String getBlogPageURL() {
        String url = page.url();
        System.out.println("page url : " + url);
//        Assertions.assertTrue(url.contains("https://www.saveatrain.com/blog/"),
//                "The new page URL must be https://www.saveatrain.com/blog/, but was " + url);
        return url;
    }
}
