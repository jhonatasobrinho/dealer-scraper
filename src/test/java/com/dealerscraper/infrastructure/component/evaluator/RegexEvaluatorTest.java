package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.model.ReviewEntry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegexEvaluatorTest {

    private final RegexEvaluator regexEvaluator = new RegexEvaluator();

    @Test
    void shouldEvaluateOneWhenThereAreTwoUppercaseLetterInARow() {
        final var expected = 1;

        final var reviewEntry = mock(ReviewEntry.class);
        when(reviewEntry.getDescription()).thenReturn("AB");

        final var actual = regexEvaluator.evaluate(reviewEntry);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldEvaluateThreeWhenThereAreThreeEqualLettersInARow() {
        final var expected = 3;

        final var reviewEntry = mock(ReviewEntry.class);
        when(reviewEntry.getDescription()).thenReturn("AAA");

        final var actual = regexEvaluator.evaluate(reviewEntry);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldEvaluateFiveWhenThereAreSixEqualLettersInARow() {
        final var expected = 5;

        final var reviewEntry = mock(ReviewEntry.class);
        when(reviewEntry.getDescription()).thenReturn("AAAAAA");

        final var actual = regexEvaluator.evaluate(reviewEntry);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldEvaluateZeroWhenThereIsNoMatchWithAnyOfTheRegexes() {
        final var expected = 0;

        final var reviewEntry = mock(ReviewEntry.class);
        when(reviewEntry.getDescription()).thenReturn("aaa");

        final var actual = regexEvaluator.evaluate(reviewEntry);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}