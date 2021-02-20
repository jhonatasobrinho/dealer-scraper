package com.dealerscraper;

import com.dealerscraper.infrastructure.component.evaluator.RatingEvaluator;
import com.dealerscraper.infrastructure.component.evaluator.RegexEvaluator;
import com.dealerscraper.infrastructure.component.evaluator.WordWhitelistEvaluator;
import com.dealerscraper.infrastructure.component.parser.ReviewEntryParser;
import com.dealerscraper.infrastructure.component.scraper.ReviewEntryScraper;
import com.dealerscraper.usecases.OverlyPositiveReviews;

public class Application {

    public static void main(String[] args) {
        final var parser = new ReviewEntryParser();
        final var scraper = new ReviewEntryScraper(parser);

        final var evaluatorChain = new RatingEvaluator(new RegexEvaluator(new WordWhitelistEvaluator()));
        final var overlyPositiveReviews = new OverlyPositiveReviews(evaluatorChain);

        final var reviewEntries = scraper.scrape();
        final var topEvaluations = overlyPositiveReviews.retrieveTopThreeEvaluations(reviewEntries);

        System.out.println("\n\n\n\n Top Evaluations \n\n\n");
        topEvaluations.forEach(System.out::println);
    }
}