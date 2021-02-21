package com.dealerscraper.infrastructure.component.scraper;

public class ReviewScrapeFailedException extends RuntimeException {
    public ReviewScrapeFailedException(final Throwable rootCause) {
        super("Failed to scrape the reviews", rootCause);
    }
}
