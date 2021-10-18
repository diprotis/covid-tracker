package com.example.covidtracker.controlller;

import com.example.covidtracker.model.LocationStats;
import com.example.covidtracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Controller class that responds to the home requests.
 */
@Controller
public class HomeController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    private static final String HOME_PAGE = "home";

    @GetMapping("/")
    public String home(final Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalCases = allStats.stream().mapToInt(LocationStats::getLatestTotalCases).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalCases);
        return HOME_PAGE;
    }
}
