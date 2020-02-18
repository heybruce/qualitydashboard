package com.solera.apac1.qualitydashboard.repo;

import com.solera.apac1.qualitydashboard.model.Screenshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {

    Screenshot findById(long id);
}
