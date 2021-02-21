package com.dealerscraper.infrastructure.component.scraper;

import com.dealerscraper.infrastructure.component.parser.ReviewEntryParser;
import com.dealerscraper.model.ReviewEntry;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class ReviewEntryScraper {

    private final ReviewEntryParser reviewEntryParser;

    public Set<ReviewEntry> scrape(final WebClient webClient) {
        final Set<ReviewEntry> reviewEntries = new HashSet<>();

        String seedUrl = buildUrl("dealer/McKaig-Chevrolet-Buick-A-Dealer-For-The-People-dealer-reviews-23685/?filter=ONLY_POSITIVE");

        try {
            HtmlPage htmlPage = webClient.getPage(seedUrl);

            for (int page = 0; page < 5; page++) {
                final var reviewEntriesSection = htmlPage.querySelectorAll("div.review-entry");
                final var parsedReviewEntries = reviewEntryParser.parse(reviewEntriesSection);

                reviewEntries.addAll(parsedReviewEntries);

                final var nextButton = htmlPage.<DomElement>getFirstByXPath("//div[contains(@class, 'next')]");
                final var onClick = nextButton.getAttribute("onclick");
                final var url = onClick.substring(onClick.indexOf("/") + 1);

                seedUrl = buildUrl(url);
                htmlPage = webClient.getPage(seedUrl);
            }
        } catch (IOException e) {
            throw new ReviewScrapeFailedException(e);
        }

        return reviewEntries;
    }

    private static String buildUrl(final String url) {
        final var host = "https://www.dealerrater.com";

        return String.format("%s/%s", host, url);
    }
}
