package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.model.ReviewEntry;

public interface Evaluator {
    Integer evaluate(final ReviewEntry reviewEntry);
    Integer getGetNextEvaluatorValue(final ReviewEntry reviewEntry);
}