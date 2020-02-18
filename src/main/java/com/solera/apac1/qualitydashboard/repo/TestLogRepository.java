package com.solera.apac1.qualitydashboard.repo;

import com.solera.apac1.qualitydashboard.model.TestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestLogRepository extends JpaRepository<TestLog, Long> {
}
