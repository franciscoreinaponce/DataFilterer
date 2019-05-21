package com.connectgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class DataFilterer {

    private static final String SEPARATOR = ",";

    private static final int POSITION_COUNTRY_CODE = 1;
    private static final int POSITION_RESPONSE_TIME = 2;

    public static Collection<?> filterByCountry(Reader source, String country) {
        BufferedReader buffered = new BufferedReader(source);
        List<String> countriesFiltered = new LinkedList<>();

        try {
            String header = buffered.readLine();
            if (header == null) {
                return Collections.emptyList();
            }
            String line;

            while ((line = buffered.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);

                if (parts[POSITION_COUNTRY_CODE].equals(country)) {
                    countriesFiltered.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countriesFiltered;
    }

    public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
        BufferedReader buffered = new BufferedReader(source);
        List<String> countriesFiltered = new LinkedList<>();

        try {
            String header = buffered.readLine();
            if (header == null) {
                return Collections.emptyList();
            }
            String line;

            while ((line = buffered.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);

                if (parts[POSITION_COUNTRY_CODE].equals(country) && Integer.parseInt(parts[POSITION_RESPONSE_TIME]) > limit) {
                    countriesFiltered.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return countriesFiltered;
    }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
        BufferedReader buffered = new BufferedReader(source);
        List<String> countriesFiltered = new LinkedList<>();

        try {
            Map<Integer, String> timeLimitByCountry = new HashMap<>();
            String header = buffered.readLine();
            if (header == null) {
                return Collections.emptyList();
            }
            String line;

            int totalTime = 0;
            int numberOfCountries = 0;
            while ((line = buffered.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);
                int responseTime = Integer.parseInt(parts[POSITION_RESPONSE_TIME]);
                timeLimitByCountry.put(responseTime, line);
                totalTime += responseTime;
                numberOfCountries++;
            }
            double media = totalTime / numberOfCountries;

            countriesFiltered = timeLimitByCountry.entrySet().stream()
                    .filter(x -> x.getKey() > media).map(x -> x.getValue()).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return countriesFiltered;
    }

}