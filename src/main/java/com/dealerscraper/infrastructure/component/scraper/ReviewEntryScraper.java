package com.dealerscraper.infrastructure.component.scraper;

import com.dealerscraper.model.ReviewEntry;
import com.dealerscraper.infrastructure.component.parser.ReviewEntryParser;
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

    public Set<ReviewEntry> scrape() {
        final Set<ReviewEntry> reviewEntries = new HashSet<>();

        try (final var webClient = new WebClient()) {
            String seedUrl = buildUrl("dealer/McKaig-Chevrolet-Buick-A-Dealer-For-The-People-dealer-reviews-23685/?filter=ONLY_POSITIVE");
            HtmlPage page = webClient.getPage(seedUrl);

            for (int i = 0; i < 5; i++) {
                final var reviewEntriesSection = page.querySelectorAll("div.review-entry");
                final var parsedReviewEntries = reviewEntryParser.parse(reviewEntriesSection);

                reviewEntries.addAll(parsedReviewEntries);

                final var nextButton = page.<DomElement>getFirstByXPath("//div[contains(@class, 'next')]");
                final var onClick = nextButton.getAttribute("onclick");
                final var url = onClick.substring(onClick.indexOf("/"));

                seedUrl = buildUrl(url);
                page = webClient.getPage(seedUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reviewEntries;
    }

    private static String buildUrl(final String url) {
        final var host = "https://www.dealerrater.com";

        return String.format("%s/%s", host, url);
    }
}
