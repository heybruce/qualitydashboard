package com.solera.apac1.qualitydashboard.repo;

import com.solera.apac1.qualitydashboard.model.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRunRepository extends PagingAndSortingRepository<TestRun, Long>, JpaSpecificationExecutor<TestRun> {

    List<TestRun> findByName(String name);

    TestRun findById(long id);

    List<TestRun> findAll();

    @Query("SELECT tr FROM TestRun tr WHERE tr.country = :country")
    List<TestRun> findByCountry(@Param("country") String country);

}
