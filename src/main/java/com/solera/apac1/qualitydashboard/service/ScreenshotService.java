package com.solera.apac1.qualitydashboard.service;

import com.solera.apac1.qualitydashboard.model.Screenshot;
import com.solera.apac1.qualitydashboard.repo.ScreenshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScreenshotService {

    @Autowired
    private ScreenshotRepository screenshotRepository;

    public Screenshot findById(long id) {
        return screenshotRepository.findById(id);
    }
}
