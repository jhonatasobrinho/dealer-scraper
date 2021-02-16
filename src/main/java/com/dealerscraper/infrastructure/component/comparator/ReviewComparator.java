package com.dealerscraper.infrastructure.component.comparator;

import com.dealerscraper.model.ReviewEntry;

import java.util.Arrays;
import java.util.Comparator;

public class ReviewComparator implements Comparator<ReviewEntry> {

    @Override
    public int compare(final ReviewEntry r1, ReviewEntry r2) {
        final Evaluator evaluator = string -> {
            int points = 0;

            final var words = string.split(" ");

            if (Arrays.stream(words).anyMatch(word -> word.matches("[A-Z]{2,}"))) {
                points++;
            }
            if (string.matches("([A-Z])\1{3}")) {
                points += 2;
            }
            if (string.matches("([A-Z])\1{6}")) {
                points += 2;
            }
            return points;
        };

        final var r1Points = evaluator.evaluate(r1.getDescription());
        final var r2Points = evaluator.evaluate(r2.getDescription());

        return r1Points.compareTo(r2Points);
    }
}
