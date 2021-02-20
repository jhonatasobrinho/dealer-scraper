package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.model.ReviewEntry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WordWhitelistEvaluator implements Evaluator {

    private final Evaluator nextEvaluator;

    public WordWhitelistEvaluator() {
        this.nextEvaluator = null;
    }

    @Override
    public Integer evaluate(final ReviewEntry reviewEntry) {
        return 0;
    }

    @Override
    public Integer getGetNextEvaluatorValue(final ReviewEntry reviewEntry) {
        return null;
    }
}
