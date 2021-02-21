package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.model.ReviewEntry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WordWhitelistEvaluatorTest {

    private final WordWhitelistEvaluator wordWhitelistEvaluator = new WordWhitelistEvaluator("src/test/resources/test-words.txt");

    @Test
    void shouldReturnThreeWhenContainsThreeWordsThatAreOnWordWhiteList() {
        final ReviewEntry reviewEntry = mockReviewEntry("Test description other");

        final var actual = wordWhitelistEvaluator.evaluate(reviewEntry);

        final var expectedEvaluation = 3;

        assertThat(actual).isEqualTo(expectedEvaluation);
    }

    @Test
    void shouldReturnZeroWhenDoesntContainAnyWordThatAreOnWordWhiteList() {
        final ReviewEntry reviewEntry = mockReviewEntry("christmas");

        final var actual = wordWhitelistEvaluator.evaluate(reviewEntry);

        final var expectedEvaluation = 0;

        assertThat(actual).isEqualTo(expectedEvaluation);
    }

    private ReviewEntry mockReviewEntry(String christmas) {
        final var reviewEntry = mock(ReviewEntry.class);
        when(reviewEntry.getDescription()).thenReturn(christmas);
        return reviewEntry;
    }
}