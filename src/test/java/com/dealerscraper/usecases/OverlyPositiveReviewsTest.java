package com.dealerscraper.usecases;

import com.dealerscraper.infrastructure.component.comparator.ReviewComparator;
import com.dealerscraper.model.ReviewEntry;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OverlyPositiveReviewsTest {

    private final ReviewComparator reviewComparator = mock(ReviewComparator.class);
    private final OverlyPositiveReviews overlyPositiveReviews = new OverlyPositiveReviews(reviewComparator);

    @Test
    void retrieveTopReviews() {
        final ReviewEntry reviewEntry1 = new ReviewEntry("Test", "Description", "Feb 12", 10);
        final ReviewEntry reviewEntry2 = new ReviewEntry("Test", "Description", "Feb 12", 12);
        final ReviewEntry reviewEntry3 = new ReviewEntry("Test", "Description", "Feb 12", 14);
        final ReviewEntry reviewEntry4 = new ReviewEntry("Test", "Description", "Feb 12", 16);

        final Set<ReviewEntry> reviewEntries = Set.of(reviewEntry1, reviewEntry2, reviewEntry3, reviewEntry4);

        final var topReviews = overlyPositiveReviews.retrieveTopReviews(reviewEntries);

        // TODO implement the tests of the usecase

    }
}