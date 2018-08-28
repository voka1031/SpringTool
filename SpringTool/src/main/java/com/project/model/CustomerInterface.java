package com.project.model;
import java.util.*;

public interface CustomerInterface {
	public void insert(Customer pVO);

	public void update(Customer pVO);

	public void delete(Integer id);

	public List<Customer> getPaging(Integer nthPage, Integer maxPerPage);

	public List<Customer> getAll();

	public Integer getListSize();

	public List<Customer> getCriteriaBuilderList();

	public List<Customer> getLambdaList();

	public Customer findByPrimaryKey(Integer id);

	public List<Customer> getByGender(String gender);
}