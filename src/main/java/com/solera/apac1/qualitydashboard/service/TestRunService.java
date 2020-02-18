package com.solera.apac1.qualitydashboard.service;

import com.solera.apac1.qualitydashboard.model.Screenshot;
import com.solera.apac1.qualitydashboard.model.TestCase;
import com.solera.apac1.qualitydashboard.model.TestLog;
import com.solera.apac1.qualitydashboard.model.TestRun;
import com.solera.apac1.qualitydashboard.repo.ScreenshotRepository;
import com.solera.apac1.qualitydashboard.repo.TestRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class TestRunService {

    @Autowired
    private TestRunRepository testRunRepository;

    @Autowired
    private ScreenshotRepository screenshotRepository;

    public Iterable<TestRun> filterAll(Specification specification) {
        return testRunRepository.findAll(specification);
    }

    public Iterable<TestRun> filterAllPaging(Specification specification, int offset, int limit, String sortBy) {
        Pageable paging = PageRequest.of(offset, limit, Sort.by(sortBy).descending());
        return testRunRepository.findAll(specification, paging);
    }

    public Iterable<TestRun> findAll() {
        return testRunRepository.findAll();
    }

    public Page<TestRun> findAllPaging(int offset, int limit, String sortBy) {
        Pageable paging = PageRequest.of(offset, limit, Sort.by(sortBy).ascending());
        Page<TestRun> testRuns = testRunRepository.findAll(paging);

        return testRuns;
    }

    public List<TestRun> findByName(String name) {
        return testRunRepository.findByName(name);
    }

    public TestRun findById(long id) {
        return testRunRepository.findById(id);
    }

    public Iterable<TestRun> findByCountry(String country) {
        return testRunRepository.findByCountry(country);
    }

    public void addTestRun(TestRun testRun) {
        for (TestCase testCase : testRun.getTestCases()) {
            testCase.setTestRun(testRun);
            for (TestLog testLog : testCase.getTestLogs()) {
                testLog.setTestCase(testCase);
                if(testLog.getImage() != null) {
                    Screenshot screenshot = new Screenshot();;
                    screenshot.setImage(testLog.getImage());
                    screenshotRepository.save(screenshot);
                    testLog.setScreenshot(screenshot);
                }
            }
        }
        testRunRepository.save(testRun);
    }
}
