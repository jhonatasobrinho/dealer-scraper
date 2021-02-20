package com.dealerscraper.infrastructure.component.parser;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import org.junit.jupiter.api.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReviewEntryParserTest {

    private final ReviewEntryParser reviewEntryParser = new ReviewEntryParser();

    @Test
    void shouldParseOneReview() {
        final var expectedTitle = "title";
        final var expectedDate = "12 Feb";
        final var expectedDescription = "Test description";
        final var expectedRating = 10;

        final var reviewEntryNode = mockReviewEntry(expectedTitle, expectedDate, expectedDescription, expectedRating);

        @SuppressWarnings("unchecked")
        final DomNodeList<DomNode> reviewEntryList = mock(DomNodeList.class);
        when(reviewEntryList.stream()).thenReturn(Stream.of(reviewEntryNode));

        final var parsedReviews = reviewEntryParser.parse(reviewEntryList);

        final var parsedReview = parsedReviews.stream().findFirst().orElseThrow();

        assertThat(parsedReview.getTitle()).isEqualTo(expectedTitle);
        assertThat(parsedReview.getDescription()).isEqualTo(expectedDescription);
        assertThat(parsedReview.getDate()).isEqualTo(expectedDate);
        assertThat(parsedReview.getRating()).isEqualTo(expectedRating);
    }

    private DomNode mockReviewEntry(final String title, final String date, final String description, final int rating) {
        final var reviewEntryNode = mock(DomNode.class);

        // get title
        final HtmlDivision titleHtmlDivision = mockTitle(title);

        // get description
        final HtmlDivision descriptionHtmlDivision = mockDescription(description);

        // build wrapper section
        mockSection(reviewEntryNode, "div[contains(@class, 'review-wrapper')]", titleHtmlDivision, descriptionHtmlDivision);

        // get date
        final HtmlDivision dateSectionHtmlDivision = mockDate(date);

        // get rating
        final HtmlDivision ratingSectionHtmlDivision = mockRating(rating);

        // build date section
        mockSection(reviewEntryNode, "div[contains(@class, 'review-date')]", dateSectionHtmlDivision, ratingSectionHtmlDivision);

        return reviewEntryNode;
    }

    private void mockSection(final DomNode reviewEntryNode, final String xPath, final HtmlDivision... elements) {
        final var dateSectionNode = mock(DomNode.class);

        when(dateSectionNode.getByXPath("div")).thenReturn(Arrays.asList(elements));

        when(reviewEntryNode.getFirstByXPath(xPath)).thenReturn(dateSectionNode);
    }

    private HtmlDivision mockTitle(final String expectedTitle) {
        final var titleHtmlDivision = mock(HtmlDivision.class);
        when(titleHtmlDivision.getVisibleText()).thenReturn(expectedTitle);
        return titleHtmlDivision;
    }

    private HtmlDivision mockDescription(final String expectedDescription) {
        final var descriptionDomNode = mock(DomNode.class);
        when(descriptionDomNode.getVisibleText()).thenReturn(expectedDescription);

        final var descriptionHtmlDivision = mock(HtmlDivision.class);
        when(descriptionHtmlDivision.querySelector("p")).thenReturn(descriptionDomNode);
        return descriptionHtmlDivision;
    }

    private HtmlDivision mockDate(final String expectedDate) {
        final var dateSectionHtmlDivision = mock(HtmlDivision.class);
        when(dateSectionHtmlDivision.getVisibleText()).thenReturn(expectedDate);
        return dateSectionHtmlDivision;
    }

    private HtmlDivision mockRating(final int expectedRating) {
        final var ratingNode = mock(Node.class);
        when(ratingNode.getNodeValue()).thenReturn(String.format("rating-%s", expectedRating));

        final var attributes = mock(NamedNodeMap.class);
        when(attributes.getNamedItem("class")).thenReturn(ratingNode);

        final var ratingDomNode = mock(DomNode.class);
        when(ratingDomNode.getAttributes()).thenReturn(attributes);

        final var ratingSectionHtmlDivision = mock(HtmlDivision.class);
        when(ratingSectionHtmlDivision.getFirstByXPath("div[contains(@class, 'rating')]")).thenReturn(ratingDomNode);
        return ratingSectionHtmlDivision;
    }
}