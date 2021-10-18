package com.example.covidtracker.service;

import com.example.covidtracker.model.LocationStats;

import java.util.List;

/**
 * Service interface that contains the functionality to fetch the Corona-virus tracker data.
 */
public interface CoronaVirusDataService {

    /**
     * Method that returns the latest Corona-Virus data.
     *
     * @return list of locationStats
     */
    List<LocationStats> getAllStats();
}
