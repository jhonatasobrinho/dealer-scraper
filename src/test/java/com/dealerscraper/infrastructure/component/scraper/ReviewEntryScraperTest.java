package com.dealerscraper.infrastructure.component.scraper;

import com.dealerscraper.infrastructure.component.parser.ReviewEntryParser;
import com.dealerscraper.model.ReviewEntry;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReviewEntryScraperTest {

    private final ReviewEntryParser reviewEntryParser = mock(ReviewEntryParser.class);
    private final ReviewEntryScraper reviewEntryScraper = new ReviewEntryScraper(reviewEntryParser);

    @Test
    void shouldScrapeReviewSection() throws IOException {
        var seedUrl = "dealer/McKaig-Chevrolet-Buick-A-Dealer-For-The-People-dealer-reviews-23685/?filter=ONLY_POSITIVE";

        final var expectedReviewEntries = new HashSet<>();

        final var webClient = mock(WebClient.class);

        for (int page = 1; page <= 5; page++) {
            final var htmlPage = mock(HtmlPage.class);

            // setup page
            when(webClient.getPage(buildUrl(seedUrl))).thenReturn(htmlPage);

            // build dom node list
            @SuppressWarnings("unchecked")
            final var domNodeList = (DomNodeList<DomNode>) mock(DomNodeList.class);

            // build nextButton
            final var nextSeedUrl = format("dealer/page%s/McKaig-Chevrolet-Buick-A-Dealer-For-The-People-dealer-reviews-23685/?filter=ONLY_POSITIVE", page + 1);

            final var nextButton = mock(DomElement.class);
            when(nextButton.getAttribute("onclick")).thenReturn("href=/" + nextSeedUrl);

            // build htmlPage
            when(htmlPage.querySelectorAll("div.review-entry")).thenReturn(domNodeList);
            when(htmlPage.getFirstByXPath("//div[contains(@class, 'next')]")).thenReturn(nextButton);

            // build parsed review entry
            final var reviewEntry = mock(ReviewEntry.class);
            when(reviewEntryParser.parse(domNodeList)).thenReturn(Set.of(reviewEntry));

            expectedReviewEntries.add(reviewEntry);

            seedUrl = nextSeedUrl;
        }

        final var scrapedReviews = reviewEntryScraper.scrape(webClient);

        assertThat(scrapedReviews).isEqualTo(expectedReviewEntries);
    }

    private String buildUrl(final String location) {
        final var baseUrl = "https://www.dealerrater.com";

        return format("%s/%s", baseUrl, location);
    }
}