package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.entity.dto.MovieReportDto;
import com.voligov.movieland.service.ReportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MovieDao movieDao;

    @Override
    public void requestAllMoviesReport() {
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
        try (FileOutputStream out = new FileOutputStream(new File("C:\\test_excel.xlsx"))) {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void requestMoviesByPeriodReport() {

    }

    @Override
    public void requestUsersReport() {

    }
}
