package com.voligov.movieland.entity.report;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.entity.dto.MovieReportDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

public class MovieReport implements Callable<String> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private String id;

    private MovieDao movieDao;

    public MovieReport(MovieDao movieDao) {
        this.movieDao = movieDao;
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public String call() throws Exception {
        List<MovieReportDto> movies = movieDao.getMoviesForReport();
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("All movies report");
        int rownum = 0;
        for (MovieReportDto movie : movies) {
            Row row = sheet.createRow(rownum++);
            Cell cell = row.createCell(0);
            cell.setCellValue(movie.getId());
            cell = row.createCell(1);
            cell.setCellValue(movie.getName());
            cell = row.createCell(2);
            cell.setCellValue(movie.getNameOriginal());
            cell = row.createCell(3);
            cell.setCellValue(movie.getDescription());
            cell = row.createCell(4);
            cell.setCellValue(movie.getGenres());
            cell = row.createCell(5);
            cell.setCellValue(movie.getPrice());
            cell = row.createCell(6);
            cell.setCellValue(movie.getAddedTimestamp().toString());
            cell = row.createCell(7);
            cell.setCellValue(movie.getUpdatedTimestamp().toString());
            cell = row.createCell(8);
            cell.setCellValue(movie.getRating());
            cell = row.createCell(9);
            cell.setCellValue(movie.getReviewCount());
        }
        String filePath = "C:\\test_excel.xlsx";
        try (FileOutputStream out = new FileOutputStream(new File(filePath))) {
            workbook.write(out);
        } catch (IOException e) {
            log.warn(e.toString());
        }
        return filePath;
    }
}
