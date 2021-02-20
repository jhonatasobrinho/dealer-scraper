package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.model.ReviewEntry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RatingEvaluator implements Evaluator {

    private final Evaluator nextEvaluator;

    public RatingEvaluator() {
        this.nextEvaluator = null;
    }

    @Override
    public Integer evaluate(final ReviewEntry reviewEntry) {
        return reviewEntry.getRating() + getGetNextEvaluatorValue(reviewEntry);
    }

    @Override
    public Integer getGetNextEvaluatorValue(final ReviewEntry reviewEntry) {
        return nextEvaluator != null ? nextEvaluator.evaluate(reviewEntry) : 0;
    }
}
