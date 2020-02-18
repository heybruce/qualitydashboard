package com.solera.apac1.qualitydashboard.repo;

import com.solera.apac1.qualitydashboard.model.TestRun;
import com.solera.apac1.qualitydashboard.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestRunSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public TestRunSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public TestRunSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<TestRun> build() {
        if (params.size() == 0) {
            return null;
        }
        List<Specification> specs = params.stream().map(TestRunSpecification::new).collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
