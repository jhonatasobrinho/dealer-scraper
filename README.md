# Dealer Scraper

The purpose of this application is scrape the reviews of [`McKaig Chevrolet Buick`](https://www.dealerrater.com/dealer/McKaig-Chevrolet-Buick-A-Dealer-For-The-People-dealer-reviews-23685) and identify the most overly positive endorsements. It uses a seed URL that is the current URL for the page on dealerrater.com.

## How it's done?
It was chosen three strategies to evaluate a Review:

### Regex Evaluator
Based on `Regexes`, we build the evaluation of a review. Inside this evaluator, there are three rules:
 - If there are two uppercase letters in a row, adds 1 point to the evaluation. (Ex: AE)
 - If there are one uppercase letter repeated three times in a row, adds 2 points to the evaluation.  (E.g: AAA or EEE)
 - If there are one uppercase letter repeated six times in a row, adds 2 points to the evaluation. (E.g: AAAAAA)

These points are cumulative, so the maximum evaluation for this evaluator is 5.

### Rating evaluator
Based on the rating of the review. In this evaluator, the rating value of the review is added to the whole evaluation.

### Word white list evaluator
Based on a list of words, this evaluator checks if the description contains each of the words that are on a `.txt` file.
The default file location is `src/main/resources/words.txt`.
For every word that is found on the description, one point is added to the evaluation.

##### Installation
```sh
git clone https://github.com/jhonatasobrinho/dealer-scraper.git
```

##### How to execute?
```sh
cd dealer-scraper
./gradlew run
```

##### How to run the tests?
```sh
cd dealer-scraper
./gradlew check
```
