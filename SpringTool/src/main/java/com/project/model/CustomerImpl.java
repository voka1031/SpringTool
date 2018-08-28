package com.project.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomerImpl implements CustomerInterface {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public List<Customer> getByGender(String gender) {

		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> root = cq.from(Customer.class);
		cq.select(root);

		List<javax.persistence.criteria.Predicate> tp = new ArrayList<javax.persistence.criteria.Predicate>();

		tp.add(cb.equal(root.get("sex"), gender));

		javax.persistence.criteria.Predicate[] pArray = new javax.persistence.criteria.Predicate[tp.size()];
		cq.where(tp.toArray(pArray));
		TypedQuery<Customer> query = getSession().createQuery(cq);

		List<Customer> list = query.getResultList();
		return list;
	}

	@Override
	public List<Customer> getCriteriaBuilderList() {

		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> root = cq.from(Customer.class);
		cq.select(root);

		List<javax.persistence.criteria.Predicate> tp = new ArrayList<javax.persistence.criteria.Predicate>();

		tp.add(cb.equal(root.get("sex"), "M"));
		tp.add(cb.between(root.<Integer>get("id"), 1043, 1054));

		javax.persistence.criteria.Predicate[] pArray = new javax.persistence.criteria.Predicate[tp.size()];
		// tp.toArray(pArray);
		cq.where(tp.toArray(pArray));
		TypedQuery<Customer> query = getSession().createQuery(cq);

		List<Customer> list = query.getResultList();
		return list;
	}

	@Override
	public Customer findByPrimaryKey(Integer id) {
		Customer pVO = null;
		try {
			pVO = (Customer) getSession().get(Customer.class, id);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return pVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getAll() {
		List<Customer> list = null;
		try {
			list = getSession().createQuery("from Customer").list();
		} catch (RuntimeException ex) {
			throw ex;
		}
		return list;
	}

	@Override
	public List<Customer> getLambdaList() {
		List<Customer> list = getAll();

		//Predicate<Customer> predicate_Sex = pVO -> pVO.getSex().equals("M");
		//Comparator<Customer> comparator_Id = (pVO1, pVO2) -> pVO1.getId().compareTo(pVO2.getId());
		
		Function<Customer, Customer> function_Tel_Format = pVO -> {
			String format_tel = pVO.getTel();
			format_tel = format_tel.substring(0, 4) + "-" + format_tel.substring(4, 7) + "-"
					+ format_tel.substring(7, 10);
			pVO.setTel(format_tel);
			return pVO;
		};

		List<Customer> lambdaList = list.stream()
				// .filter(predicate_Sex)
				.map(function_Tel_Format)
				// .sorted(comparator_Id)
				.collect(Collectors.toList());
		return lambdaList;
	}

	@Override
	public List<Customer> getPaging(Integer nthPage, Integer maxPerPage) {

		Integer first = nthPage * maxPerPage + 1;
		Integer last = (nthPage == 0) ? maxPerPage : (nthPage + 1) * maxPerPage;

		String sql = "SELECT id, sex, name, tel FROM (SELECT id, sex, name, tel ,ROW_NUMBER() "
				+ "OVER(ORDER BY id DESC) As RowNum FROM Customer ) As A WHERE A.RowNum " + "BETWEEN " + first + " AND "
				+ last + " ;";

		List<Customer> list = null;
		try {
			list = getSession().createNativeQuery(sql, Customer.class).getResultList();
		} catch (RuntimeException ex) {
			throw ex;
		}
		return list;
	}

	@Override
	public Integer getListSize() {
		BigInteger listSize;
		try {
			listSize = (BigInteger) getSession().createNativeQuery("select count(*) from customer;").getResultList()
					.get(0);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return listSize.intValue();
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void insert(Customer pVO) {
		try {
			getSession().saveOrUpdate(pVO);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void update(Customer pVO) {
		try {
			getSession().saveOrUpdate(pVO);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void delete(Integer id) {
		Session session = getSession();
		try {
			Customer pVO = session.get(Customer.class, id);
			getSession().delete(pVO);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

}