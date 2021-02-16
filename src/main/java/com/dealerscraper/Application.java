package com.dealerscraper;

import com.dealerscraper.infrastructure.component.comparator.ReviewComparator;
import com.dealerscraper.infrastructure.component.parser.ReviewEntryParser;
import com.dealerscraper.infrastructure.component.scraper.ReviewEntryScraper;
import com.dealerscraper.usecases.OverlyPositiveReviews;

public class Application {

    public static void main(String[] args) {
        final var overlyPositiveReviews = new OverlyPositiveReviews(new ReviewComparator());
        final var parser = new ReviewEntryParser();
        final var scraper = new ReviewEntryScraper(parser);

        final var reviewEntries = scraper.scrape();

        reviewEntries.forEach(System.out::println);

        System.out.println("\n\n\n\n Top Reviews \n\n\n");

        final var topReviews = overlyPositiveReviews.retrieveTopReviews(reviewEntries);

        topReviews.forEach(System.out::println);
    }
}