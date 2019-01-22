package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.model.Customer;
import com.project.model.CustomerInterface;

@Service
@Transactional
public class TransService {

	@Autowired
	private CustomerInterface dao;

	@Transactional(propagation = Propagation.REQUIRED)
	public void addCustomer(Customer customer) {
		dao.insert(customer);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateCustomer(Customer customer) {
		dao.update(customer);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCustomer(Integer id) {
		dao.delete(id);
	}
}
