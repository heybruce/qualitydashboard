package com.solera.apac1.qualitydashboard.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "test_case")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    @Enumerated(EnumType.STRING)
    private TestResultType resultType;

    private Date timeStarted;

    private Date timeEnded;

    private long timeTaken;

    @OneToMany(mappedBy = "testCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestLog> testLogs = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    private TestRun testRun;

    public TestCase() {}

    public TestCase(String name, TestLog... testLogs) {
        this.name = name;
        this.testLogs = Stream.of(testLogs).collect(Collectors.toList());
        this.testLogs.forEach(x -> x.setTestCase(this));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public TestResultType getTestResultType() {
        return resultType;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public Date getTimeEnded() {
        return timeEnded;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTestResultType(TestResultType resultType) {
        this.resultType = resultType;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public void setTimeEnded(Date timeEnded) {
        this.timeEnded = timeEnded;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public List<TestLog> getTestLogs() {
        return testLogs;
    }

    public void setTestLogs(List<TestLog> testLogs) {
        this.testLogs = testLogs;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }

    public void addTestLog(TestLog testLog) {
        testLogs.add(testLog);
        testLog.setTestCase(this);
    }

    public void removeTestLog(TestLog testLog) {
        testLogs.remove(testLog);
        testLog.setTestCase(null);
    }

}
