package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.exceptions.FilePathNotProvidedException;
import com.dealerscraper.model.ReviewEntry;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WordWhitelistEvaluatorTest {

    private final WordWhitelistEvaluator wordWhitelistEvaluator = new WordWhitelistEvaluator("src/test/resources/test-words.txt");

    @Test
    void shouldThrowExceptionWhenFilePathIsNull() {
        assertThatThrownBy(() -> new WordWhitelistEvaluator(null))
                .isInstanceOf(FilePathNotProvidedException.class)
                .hasMessage("Path to the file not provided to word whitelist evaluator");
    }

    @Test
    void shouldThrowExceptionWhenFilePathIsEmpty() {
        assertThatThrownBy(() -> new WordWhitelistEvaluator(""))
                .isInstanceOf(FilePathNotProvidedException.class)
                .hasMessage("Path to the file not provided to word whitelist evaluator");
    }

    @Test
    void shouldThrowExceptionWhenFileDoesntExist() {
        assertThatThrownBy(() -> new WordWhitelistEvaluator("bla").evaluate(mock(ReviewEntry.class)))
                .isInstanceOf(NoSuchFileException.class);
    }

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