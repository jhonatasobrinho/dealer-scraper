package com.dealerscraper.infrastructure.component.evaluator;

import com.dealerscraper.exceptions.FilePathNotProvidedException;
import com.dealerscraper.model.ReviewEntry;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.BiFunction;

public class PointingWordsEvaluator implements Evaluator {

    private final String filePath;
    private final Evaluator nextEvaluator;

    public PointingWordsEvaluator(final String filePath, final Evaluator nextEvaluator) {
        if (filePath == null || filePath.isEmpty()) {
            throw new FilePathNotProvidedException("Path to the file not provided to word whitelist evaluator");
        }

        this.filePath = filePath;
        this.nextEvaluator = nextEvaluator;
    }

    public PointingWordsEvaluator(final String filePath) {
        this(filePath, null);
    }

    @Override
    @SneakyThrows
    public Integer evaluate(final ReviewEntry reviewEntry) {
        final var lines = Files.lines(Paths.get(Objects.requireNonNull(filePath))).map(String::toLowerCase);

        final BiFunction<Integer, String, Integer> reduceIfReviewEntryContainsWord = (subtotal, word) -> {
            if (reviewEntryContainsWord(reviewEntry, word)) {
                return subtotal + 1;
            }

            return subtotal;
        };

        return lines.reduce(0, reduceIfReviewEntryContainsWord, Integer::sum) + getGetNextEvaluatorValue(reviewEntry);
    }

    @Override
    public Integer getGetNextEvaluatorValue(final ReviewEntry reviewEntry) {
        return nextEvaluator != null ? nextEvaluator.evaluate(reviewEntry) : 0;
    }

    private boolean reviewEntryContainsWord(final ReviewEntry reviewEntry, final String word) {
        return reviewEntry.getDescription().toLowerCase().contains(word);
    }
}
