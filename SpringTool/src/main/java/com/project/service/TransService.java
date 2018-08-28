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
	public void addPractice(Customer practiceVO) {
		dao.insert(practiceVO);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updatePractice(Customer practiceVO) {
		dao.update(practiceVO);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deletePractice(Integer id) {
		dao.delete(id);
	}
}
