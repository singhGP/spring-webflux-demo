package com.seasia.webflux.controller;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.seasia.webflux.model.Employee;
import com.seasia.webflux.model.EmployeeEvent;
import com.seasia.webflux.repository.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/rest/employee")
public class EmployeeController {

	private EmployeeRepository employeeRepository;

	public EmployeeController(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@GetMapping("/all")
	public Flux<Employee> getAll() {
		return employeeRepository.findAll();
	}

	@GetMapping("/{id}")
	public Mono<Employee> getId(@PathVariable("id") final String empId) {
		return employeeRepository.findById(empId);
	}

	@GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<EmployeeEvent> getEvents(@PathVariable("id") final String empId) {
		return employeeRepository.findById(empId).flatMapMany(employee -> {

			Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
			Flux<EmployeeEvent> employeeEventFlux = Flux
					.fromStream(Stream.generate(() -> new EmployeeEvent(employee, new Date())));

			return Flux.zip(interval, employeeEventFlux).map(Tuple2::getT2);

		});

	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public Flux<Employee> findAll() {
		Flux<Employee> emps = employeeRepository.findAll();
		return emps;
	}

	@GetMapping("/loadData")
	@ResponseBody
	public String loadData() {
		System.out.println("In side........");
		Stream.of(new Employee(UUID.randomUUID().toString(), "Peter", 23000L),
				new Employee(UUID.randomUUID().toString(), "Sam", 13000L),
				new Employee(UUID.randomUUID().toString(), "Ryan", 20000L),
				new Employee(UUID.randomUUID().toString(), "Chris", 53000L),
				new Employee(UUID.randomUUID().toString(), "aman", 28000L),
				new Employee(UUID.randomUUID().toString(), "ajay", 26000L),
				new Employee(UUID.randomUUID().toString(), "rahul", 25000L)).forEach(employee -> {
					employeeRepository.save(employee).subscribe(System.out::println);

				});

		return "<h1>Data created</h1>";
	}

}
