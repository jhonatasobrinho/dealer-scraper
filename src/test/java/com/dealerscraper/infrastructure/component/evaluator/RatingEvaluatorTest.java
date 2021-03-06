package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.model.ReviewEntry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RatingEvaluatorTest {

    private final RatingEvaluator ratingEvaluator = new RatingEvaluator();

    @Test
    void shouldEvaluateByRating() {
        final var expectedRating = 10;

        final var reviewEntry = mock(ReviewEntry.class);
        when(reviewEntry.getRating()).thenReturn(expectedRating);

        final var actualRating = ratingEvaluator.evaluate(reviewEntry);

        assertThat(actualRating).isEqualTo(expectedRating);
    }
}