package com.seasia.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.seasia.webflux.model.Employee;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
}
