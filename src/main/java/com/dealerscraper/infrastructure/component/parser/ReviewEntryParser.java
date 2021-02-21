package com.dealerscraper.infrastructure.component.parser;

import com.dealerscraper.model.ReviewEntry;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;

import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class ReviewEntryParser {
    public Set<ReviewEntry> parse(final DomNodeList<DomNode> reviewEntries) {
        return reviewEntries.stream().map(this::parse).collect(toUnmodifiableSet());
    }

    private ReviewEntry parse(final DomNode node) {
        final var wrapperSection = getContentByXPathContainingClass(node, "review-wrapper");
        final var title = getTitle(wrapperSection);
        final var description = getDescription(wrapperSection);

        final var dateSection = getContentByXPathContainingClass(node, "review-date");
        final var date = getDate(dateSection);
        final var rating = getRating(dateSection);

        return new ReviewEntry(title, description, date, rating);
    }

    private Integer getRating(final List<HtmlDivision> dateSection) {
        final var ratesSection = dateSection.get(1);
        final var contents = getByXPathContainingClass(ratesSection, "rating");

        final var aClass = contents.getAttributes().getNamedItem("class").getNodeValue();
        final var rating = aClass.replaceAll("\\D+", "");
        return parseInt(rating);
    }

    private String getTitle(final List<HtmlDivision> wrapperSection) {
        return wrapperSection.get(0).getVisibleText();
    }

    private String getDescription(final List<HtmlDivision> wrapperSection) {
        final var p = wrapperSection.get(1).querySelector("p");
        return p.getVisibleText();
    }

    private String getDate(List<HtmlDivision> dateSection) {
        return dateSection.get(0).getVisibleText();
    }

    private List<HtmlDivision> removeEmptyContentGettingOnlyDivs(final DomNode node) {
        return node.getByXPath("div");
    }

    private DomNode getByXPathContainingClass(final DomNode node, final String className) {
        return node.getFirstByXPath(String.format("div[contains(@class, '%s')]", className));
    }

    private List<HtmlDivision> getContentByXPathContainingClass(final DomNode node, final String className) {
        final DomNode firstByXPath = getByXPathContainingClass(node, className);

        return removeEmptyContentGettingOnlyDivs(firstByXPath);
    }
}
