package com.voligov.movieland.service;

public interface ReportService {
    String requestAllMoviesReport();

    String requestMoviesByPeriodReport();

    String requestUsersReport();

    String getReport(String reportId);
}
