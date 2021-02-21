package com.dealerscraper.exceptions;

public class FilePathNotProvidedException extends RuntimeException {
    public FilePathNotProvidedException(final String message) {
        super(message);
    }
}
