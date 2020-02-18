package com.solera.apac1.qualitydashboard.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "test_run")
public class TestRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private String browser;

    private String browserVersion;

    private String env;

    private String type;

    private String endPoint;

    private String device;

    private long passedCount;

    private long failedCount;

    private long otherCount;

    private double successRate;

    private Date timeStarted;

    private Date timeFinished;

    private Date lastUpdated;

    private long timeElapsed;

    private String app;

    @OneToMany(mappedBy = "testRun", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestCase> testCases = new LinkedHashSet<>();

    public TestRun() {
    }

    public TestRun(String name, TestCase... testCases) {
        this.name = name;
        this.testCases = Stream.of(testCases).collect(Collectors.toSet());
        this.testCases.forEach(x -> x.setTestRun(this));
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public void setTimeFinished(Date timeFinished) {
        this.timeFinished = timeFinished;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setPassedCount(long passedCount) {
        this.passedCount = passedCount;
    }

    public void setFailedCount(long failedCount) {
        this.failedCount = failedCount;
    }

    public void setOtherCount(long otherCount) {
        this.otherCount = otherCount;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getBrowser() {
        return browser;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public Date getTimeFinished() {
        return timeFinished;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public String getEnv() {
        return env;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public String getType() {
        return type;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public String getDevice() {
        return device;
    }

    public long getPassedCount() {
        return passedCount;
    }

    public long getFailedCount() {
        return failedCount;
    }

    public long getOtherCount() {
        return otherCount;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public Set<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(Set<TestCase> testCases) {
        this.testCases = testCases;
    }

    public void addTestCase(TestCase testCase) {
        testCases.add(testCase);
        testCase.setTestRun(this);
    }

    public void removeTestCase(TestCase testCase) {
        testCases.remove(testCase);
        testCase.setTestRun(null);
    }
}
