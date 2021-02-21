package com.dealerscraper.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class EvaluationTest {

    @Test
    void shouldReturnOneWhenCompareToAnotherEvaluationWhoHasMorePoints() {
        final var one = new Evaluation(10, Mockito.mock(ReviewEntry.class));
        final var another = new Evaluation(20, Mockito.mock(ReviewEntry.class));

        assertThat(one.compareTo(another)).isEqualTo(1);
    }

    @Test
    void shouldReturnMinusOneWhenCompareToAnotherEvaluationWhoHasLessPoints() {
        final var one = new Evaluation(20, Mockito.mock(ReviewEntry.class));
        final var another = new Evaluation(10, Mockito.mock(ReviewEntry.class));

        assertThat(one.compareTo(another)).isEqualTo(-1);
    }

    @Test
    void shouldReturnZeroWhenCompareToAnotherEvaluationWhoHasTheSameAmountOfPointss() {
        final var one = new Evaluation(20, Mockito.mock(ReviewEntry.class));
        final var another = new Evaluation(10, Mockito.mock(ReviewEntry.class));

        assertThat(one.compareTo(another)).isEqualTo(-1);
    }
}