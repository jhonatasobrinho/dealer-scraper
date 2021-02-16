package com.dealerscraper.usecases;

import com.dealerscraper.infrastructure.component.comparator.ReviewComparator;
import com.dealerscraper.model.ReviewEntry;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OverlyPositiveReviews {

    private final ReviewComparator reviewComparator;

    public List<ReviewEntry> retrieveTopReviews(final Set<ReviewEntry> reviewEntries) {
        return reviewEntries.stream()
                .sorted(reviewComparator)
                .limit(3)
                .collect(Collectors.toUnmodifiableList());
    }
}
