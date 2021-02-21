package com.dealerscraper.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ReviewEntry {

    private final String title;
    private final String description;
    private final String date;
    private final Integer rating;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public Integer getRating() {
        return rating;
    }
}
