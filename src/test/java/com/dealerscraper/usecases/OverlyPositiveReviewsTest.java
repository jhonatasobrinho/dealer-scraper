package com.dealerscraper.usecases;

import com.dealerscraper.infrastructure.component.evaluator.Evaluator;
import com.dealerscraper.model.Evaluation;
import com.dealerscraper.model.ReviewEntry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestReviewComparator implements Comparator<ReviewEntry> {

    @Override
    public int compare(final ReviewEntry review1, final ReviewEntry review2) {
        return review2.getRating().compareTo(review1.getRating());
    }
}

class OverlyPositiveReviewsTest {

    private final Evaluator evaluator = mock(Evaluator.class);
    private final OverlyPositiveReviews overlyPositiveReviews = new OverlyPositiveReviews(evaluator);

    @Test
    void retrieveTopReviews() {
        final ReviewEntry reviewEntry1 = mock(ReviewEntry.class);
        final ReviewEntry reviewEntry2 = mock(ReviewEntry.class);
        final ReviewEntry reviewEntry3 = mock(ReviewEntry.class);
        final ReviewEntry reviewEntry4 = mock(ReviewEntry.class);

        final Set<ReviewEntry> reviewEntries = Set.of(reviewEntry1, reviewEntry2, reviewEntry3, reviewEntry4);

        when(evaluator.evaluate(reviewEntry1)).thenReturn(10);
        when(evaluator.evaluate(reviewEntry2)).thenReturn(12);
        when(evaluator.evaluate(reviewEntry3)).thenReturn(14);
        when(evaluator.evaluate(reviewEntry4)).thenReturn(16);

        final var topEvaluations = overlyPositiveReviews.retrieveTopThreeEvaluations(reviewEntries);

        assertThat(topEvaluations).containsExactly(new Evaluation(16, reviewEntry4),
                new Evaluation(14, reviewEntry3),
                new Evaluation(12, reviewEntry2));
    }
}