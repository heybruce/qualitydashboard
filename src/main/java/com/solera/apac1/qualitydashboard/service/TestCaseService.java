package com.solera.apac1.qualitydashboard.service;

import com.solera.apac1.qualitydashboard.model.TestCase;
import com.solera.apac1.qualitydashboard.model.TestLog;
import com.solera.apac1.qualitydashboard.repo.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    public Iterable<TestCase> findAll() {
        return testCaseRepository.findAll();
    }

    public TestCase findById(long id) {
        return testCaseRepository.findById(id);
    }

    public List<TestCase> findByName(String name) {
        return testCaseRepository.findByName(name);
    }

    public void addNewCase(TestCase newCase) {
        for(TestLog testLog : newCase.getTestLogs()) {
            testLog.setTestCase(newCase);
        }
        testCaseRepository.save(newCase);
    }

    public void addLog(long caseId, TestLog testLog) {
        TestCase testCase = testCaseRepository.findById(caseId);
        testCase.addTestLog(testLog);
        testCaseRepository.save(testCase);
    }
}
