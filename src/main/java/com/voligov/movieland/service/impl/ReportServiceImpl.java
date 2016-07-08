package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.entity.report.MovieReport;
import com.voligov.movieland.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ReportServiceImpl implements ReportService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDao;

    private List<MovieReport> reportRequests = new CopyOnWriteArrayList<>();
    private Map<String, Future<String>> futures = new ConcurrentHashMap<>();

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    @Override
    public String requestAllMoviesReport() {
        MovieReport movieReport = new MovieReport(movieDao);
        String reportId = movieReport.getId();
        reportRequests.add(movieReport);
        return reportId;
    }

    @Override
    public String requestMoviesByPeriodReport() {
        return null;
    }

    @Override
    public String requestUsersReport() {
        return null;
    }

    @Override
    public String getReport(String reportId) {
        Future<String> result = futures.get(reportId);
        if (result.isDone()) {
            try {
                return result.get();
            } catch (InterruptedException | ExecutionException e) {
                log.warn(e.toString());
            }
        }
        return null;
    }

    @Scheduled(fixedRate = 60 * 1000)
    public synchronized void startReportGeneration() {
        for (MovieReport report : reportRequests) {
            Future<String> future = taskExecutor.submit(report);
            futures.put(report.getId(), future);
        }
        reportRequests.clear();
    }
}
