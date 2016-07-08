package com.voligov.movieland.controller;

import com.voligov.movieland.controller.annotation.RoleRequired;
import com.voligov.movieland.service.ReportService;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RoleRequired(role = UserRole.ADMIN)
    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> requestReport() {
        String reportId = reportService.requestAllMoviesReport();
        return new ResponseEntity<>(reportId, HttpStatus.OK);
    }

    @RoleRequired(role = UserRole.ADMIN)
    @RequestMapping(value = "/{reportId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FileSystemResource> getReport(@PathVariable("reportId") String reportId) {
        String filePath = reportService.getReport(reportId);
        if (filePath == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new FileSystemResource(filePath), HttpStatus.OK);
    }
}
