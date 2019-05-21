package com.connectgroup;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataFiltererTest {

    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB", 100).isEmpty());
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());
    }

    @Test
    public void shouldReturnOneElementCollection_WhenLogFileIsSingleAndFiltersMatches() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "GB").size() == 1);
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "GB", 100).size() == 1);
        // In this case, can not be above average with one only element in the file.
        assertFalse(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/single-line")).size() == 1);
    }

    @Test
    public void shouldReturnSeveralElementCollection_WhenLogFileIsSingleAndFiltersMatches() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "DE").size() == 1);
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", 600).size() == 2);
        // The average is 526.
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines")).size() == 3);
    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }

}
