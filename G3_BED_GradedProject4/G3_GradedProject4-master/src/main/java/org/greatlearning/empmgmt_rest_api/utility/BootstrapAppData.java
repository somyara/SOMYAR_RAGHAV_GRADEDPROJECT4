package org.greatlearning.empmgmt_rest_api.utility;

import javax.transaction.Transactional;

import org.greatlearning.empmgmt_rest_api.entity.Employee;
import org.greatlearning.empmgmt_rest_api.entity.Role;
import org.greatlearning.empmgmt_rest_api.entity.User;
import org.greatlearning.empmgmt_rest_api.repo.EmployeeRepo;
import org.greatlearning.empmgmt_rest_api.repo.RoleRepo;
import org.greatlearning.empmgmt_rest_api.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BootstrapAppData {
	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private RoleRepo roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public BootstrapAppData(EmployeeRepo employeeRepo, UserRepo userRepository, PasswordEncoder passwordEncoder) {
		this.employeeRepo = employeeRepo;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	//Dummy employees
	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void usersData(ApplicationReadyEvent readyEvent) {

		Employee Lingu = new Employee("Mr", "Lingu", "lingu@gmail.com");
		Employee Samrin = new Employee("Samrin", "m", "samrin.m@gmail.com");
		Employee Reddy = new Employee("Reddy", "Kumar", "reddy@gmail.com");
		Employee varsha = new Employee("varsha", "kumari", "varsha@gmail.com");
		Employee kiran = new Employee("kiran", "naik", "kiran@gmail.com");
	

		this.employeeRepo.save(Lingu);
		this.employeeRepo.save(Samrin);
		this.employeeRepo.save(Reddy);
		this.employeeRepo.save(varsha);
		this.employeeRepo.save(kiran);

	}

	//Dummy users
	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void insertAllData(ApplicationReadyEvent event) {
		User lingu = new User("lingu", passwordEncoder.encode("lingu"));
		User admin = new User("admin", passwordEncoder.encode("admin"));


		Role userRole = new Role("ROLE_USER");
		Role adminRole = new Role("ROLE_ADMIN");

		roleRepository.saveAndFlush(userRole);
		roleRepository.saveAndFlush(adminRole);

		admin.addRole(adminRole);
		lingu.addRole(userRole);

		userRepository.saveAndFlush(lingu);
		userRepository.saveAndFlush(admin);

	}

}
