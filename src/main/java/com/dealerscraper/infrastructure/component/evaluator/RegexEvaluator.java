package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.model.ReviewEntry;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class RegexEvaluator implements Evaluator {

    private final Evaluator nextEvaluator;

    public RegexEvaluator() {
        this.nextEvaluator = null;
    }

    @Override
    public Integer evaluate(final ReviewEntry reviewEntry) {
        int points = 0;

        final var description = reviewEntry.getDescription();

        final var words = description.split(" ");

        if (Arrays.stream(words).anyMatch(word -> word.matches("[A-Z]{2,}"))) {
            points++;
        }
        if (description.matches(".*([A-Z])\\1{2}.*")) {
            points += 2;
        }
        if (description.matches(".*([A-Z])\\1{5}.*")) {
            points += 2;
        }

        return points + getGetNextEvaluatorValue(reviewEntry);
    }

    @Override
    public Integer getGetNextEvaluatorValue(final ReviewEntry reviewEntry) {
        return nextEvaluator != null ? nextEvaluator.evaluate(reviewEntry) : 0;
    }
}
