package com.example.covidtracker.service.impl;

import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.service.CoronaVirusDataService;
import com.google.common.collect.Streams;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Class that implements the service {@link CoronaVirusDataService}
 */
@Slf4j
@Service
public class CoronaVirusDataServiceImpl implements CoronaVirusDataService {

    private static final String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @Getter
    private List<LocationStats> allStats = new ArrayList<>();

    /**
     * Method that fetches the Corona-Virus data from the HTTP call.
     */
    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
       final HttpClient client = HttpClient.newHttpClient();
       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(DATA_URL))
               .build();
       HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
       final Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
                .parse(new StringReader(httpResponse.body()));

       allStats = Streams.stream(records).map(record -> {
           String provinceState = record.get("Province/State");
           String countryRegion = record.get("Country/Region");
           int latestTotalCases = Integer.parseInt(record.get(record.size() - 1));
           int previousDayCases = Integer.parseInt(record.get(record.size() - 2));
           return LocationStats.builder().state(provinceState).country(countryRegion)
                   .latestTotalCases(latestTotalCases).diffPreviousDay(latestTotalCases - previousDayCases).build();
       }).collect(Collectors.toList());
    }
}
