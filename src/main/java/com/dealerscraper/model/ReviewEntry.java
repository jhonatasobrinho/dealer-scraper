package com.dealerscraper.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ReviewEntry {
    private final String title;
    private final String description;
    private final String date;
    private final Integer rating;
}
