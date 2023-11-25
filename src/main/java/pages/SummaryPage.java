package pages;

import com.microsoft.playwright.Page;

public class SummaryPage {
    private Page page;

    public SummaryPage(Page page) {
        this.page = page;
    }

    public String getSummaryPageTitle() {
        return page.title();
    }
}
