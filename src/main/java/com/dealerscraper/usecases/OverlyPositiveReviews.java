package com.dealerscraper.usecases;

import com.dealerscraper.infrastructure.component.evaluator.Evaluator;
import com.dealerscraper.model.Evaluation;
import com.dealerscraper.model.ReviewEntry;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OverlyPositiveReviews {

    private final Evaluator evaluator;

    public List<Evaluation> retrieveTopThreeEvaluations(final Set<ReviewEntry> reviewEntries) {
        return reviewEntries.stream()
                .map(reviewEntry -> new Evaluation(evaluator.evaluate(reviewEntry), reviewEntry))
                .peek(System.out::println)
                .sorted()
                .limit(10)
                .collect(Collectors.toUnmodifiableList());
    }
}
