package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.model.ReviewEntry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EvaluatorChainIntegrationTest {

    private final Evaluator evaluatorChain = new RegexEvaluator(new PointingWordsEvaluator("src/test/resources/test-words.txt", new RatingEvaluator()));

    @Test
    void shouldChainCallToTheEvaluatorsAndAcummulateTheEvaluation() {
        final var reviewEntry = mock(ReviewEntry.class);
        when(reviewEntry.getDescription()).thenReturn("AWEEEEEESOME store. highly recommend.");
        when(reviewEntry.getRating()).thenReturn(50);

        final var actual = evaluatorChain.evaluate(reviewEntry);

        assertThat(actual).isEqualTo(57);
    }
}
