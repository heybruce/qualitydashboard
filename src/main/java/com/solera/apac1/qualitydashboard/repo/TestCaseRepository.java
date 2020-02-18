package com.solera.apac1.qualitydashboard.repo;

import com.solera.apac1.qualitydashboard.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

    List<TestCase> findByName(String name);

    TestCase findById(long id);

    List<TestCase> findAll();

}
