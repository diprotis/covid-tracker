package com.example.covidtracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model containing the location information.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationStats {

    private String state;
    private String country;
    private int latestTotalCases;
    private int diffPreviousDay;
}
