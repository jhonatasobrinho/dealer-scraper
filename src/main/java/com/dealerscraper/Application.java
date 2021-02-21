package com.dealerscraper;

import com.dealerscraper.infrastructure.component.evaluator.RatingEvaluator;
import com.dealerscraper.infrastructure.component.evaluator.RegexEvaluator;
import com.dealerscraper.infrastructure.component.evaluator.WordWhitelistEvaluator;
import com.dealerscraper.infrastructure.component.parser.ReviewEntryParser;
import com.dealerscraper.infrastructure.component.scraper.ReviewEntryScraper;
import com.dealerscraper.usecases.OverlyPositiveReviews;
import com.gargoylesoftware.htmlunit.WebClient;

public class Application {

    private static final String FILE_PATH = "src/main/resources/words.txt";

    public static void main(String[] args) {
        final var parser = new ReviewEntryParser();
        final var scraper = new ReviewEntryScraper(parser);

        final var evaluatorChain = new RegexEvaluator(new RatingEvaluator(new WordWhitelistEvaluator(FILE_PATH)));
        final var overlyPositiveReviews = new OverlyPositiveReviews(evaluatorChain);

        try (final WebClient webClient = new WebClient()) {
            final var reviewEntries = scraper.scrape(webClient);
            final var topEvaluations = overlyPositiveReviews.retrieveTopThreeEvaluations(reviewEntries);

            System.out.println("\n\n\n\n Top Evaluations \n\n\n");
            topEvaluations.forEach(System.out::println);
        }
    }
}