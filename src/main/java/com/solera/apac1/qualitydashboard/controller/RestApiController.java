package com.solera.apac1.qualitydashboard.controller;

import com.solera.apac1.qualitydashboard.model.TestCase;
import com.solera.apac1.qualitydashboard.model.TestRun;
import com.solera.apac1.qualitydashboard.repo.TestRunSpecificationsBuilder;
import com.solera.apac1.qualitydashboard.service.ScreenshotService;
import com.solera.apac1.qualitydashboard.service.TestCaseService;
import com.solera.apac1.qualitydashboard.service.TestRunService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private ScreenshotService screenshotService;

    // ---------------------Test Run----------------------------------------------------------------------------
//    @GetMapping(path = "/testruns/all")
//    @ResponseStatus(HttpStatus.OK)
//    public @ResponseBody Iterable<TestRun> findAllTestRuns() {
//        return testRunService.findAll();
//    }

    @GetMapping(path = "/testruns/list")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Iterable<TestRun> findTestRuns(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                        @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                        @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        return testRunService.findAllPaging(offset, limit, sortBy);
    }

//    @GetMapping(path = "/testruns")
//    @ResponseStatus(HttpStatus.OK)
//    public @ResponseBody Iterable<TestRun> findTestRunsByCountry(@RequestParam String country) {
//        if (country == null) {
//            return findAllTestRuns();
//        }
//        else {
//            return testRunService.findByCountry(country);
//        }
//    }

    @RequestMapping(path = "/testruns")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Iterable<TestRun> search(@RequestParam(value = "search") String search,
                                                  @RequestParam(value = "offset", defaultValue = "0") int offset,
                                                  @RequestParam(value = "limit", defaultValue = "10") int limit,
                                                  @RequestParam(value = "sortBy", defaultValue = "id") String sortBy) {
        TestRunSpecificationsBuilder builder = new TestRunSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<TestRun> spec = builder.build();
        return testRunService.filterAllPaging(spec, offset, limit, sortBy);
    }

    @GetMapping(path = "/testruns/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TestRun findTestRunById(@PathVariable long id) {
        return testRunService.findById(id);
    }

//    @GetMapping(path = "/testruns")
//    @ResponseStatus(HttpStatus.OK)
//    public @ResponseBody List findTestRunsByName(@RequestParam String name) {
//            return testRunService.findByName(name);
//    }

    @PostMapping(path = "/testruns/add")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void addTestRun(@RequestBody TestRun testRun) {
        testRunService.addTestRun(testRun);
    }

    // ---------------------Test Case----------------------------------------------------------------------------
    @GetMapping(path = "/testcases/all")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Iterable<TestCase> findAllTestCases() {
        return testCaseService.findAll();
    }

    @GetMapping(path = "/testcases/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody TestCase findTestCaseById(@PathVariable long id) {
        return testCaseService.findById(id);
    }


    // ---------------------Screenshot----------------------------------------------------------------------------
    @GetMapping(path = "/screenshots/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody byte[] getScreenshotById(@PathVariable long id) {
        return screenshotService.findById(id).getImage();
    }
}
