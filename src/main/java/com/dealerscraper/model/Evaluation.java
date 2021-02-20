package com.dealerscraper.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Evaluation implements Comparable<Evaluation> {
    private final Integer points;
    private final ReviewEntry reviewEntry;

    @Override
    public int compareTo(final Evaluation other) {
        return other.points.compareTo(points);
    }
}
