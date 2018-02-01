package com.project.model;

import java.util.*;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.*;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class PracticeDAO implements PracticeDAO_interface {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public List<PracticeVO> getByGender(String gender) {

		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<PracticeVO> cq = cb.createQuery(PracticeVO.class);
		Root<PracticeVO> root = cq.from(PracticeVO.class);
		cq.select(root);

		List<javax.persistence.criteria.Predicate> tp = new ArrayList<javax.persistence.criteria.Predicate>();

		tp.add(cb.equal(root.get("sex"), gender));

		javax.persistence.criteria.Predicate[] pArray = new javax.persistence.criteria.Predicate[tp.size()];
		cq.where(tp.toArray(pArray));
		TypedQuery<PracticeVO> query = getSession().createQuery(cq);

		List<PracticeVO> list = query.getResultList();
		return list;
	}

	@Override
	public List<PracticeVO> getCriteriaBuilderList() {

		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<PracticeVO> cq = cb.createQuery(PracticeVO.class);
		Root<PracticeVO> root = cq.from(PracticeVO.class);
		cq.select(root);

		List<javax.persistence.criteria.Predicate> tp = new ArrayList<javax.persistence.criteria.Predicate>();

		tp.add(cb.equal(root.get("sex"), "M"));
		tp.add(cb.between(root.<Integer>get("id"), 1043, 1054));

		javax.persistence.criteria.Predicate[] pArray = new javax.persistence.criteria.Predicate[tp.size()];
		// tp.toArray(pArray);
		cq.where(tp.toArray(pArray));
		TypedQuery<PracticeVO> query = getSession().createQuery(cq);

		List<PracticeVO> list = query.getResultList();
		return list;
	}

	@Override
	public PracticeVO findByPrimaryKey(Integer id) {
		PracticeVO pVO = null;
		try {
			pVO = (PracticeVO) getSession().get(PracticeVO.class, id);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return pVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PracticeVO> getAll() {
		List<PracticeVO> list = null;
		try {
			list = getSession().createQuery("from PracticeVO").list();
		} catch (RuntimeException ex) {
			throw ex;
		}
		return list;
	}

	@SuppressWarnings("unused")
	@Override
	public List<PracticeVO> getLambdaList() {
		List<PracticeVO> list = getAll();

		Predicate<PracticeVO> predicate_Sex = pVO -> pVO.getSex().equals("M");
		Comparator<PracticeVO> comparator_Id = (pVO1, pVO2) -> pVO1.getId().compareTo(pVO2.getId());
		Function<PracticeVO, PracticeVO> function_Tel_Format = pVO -> {
			String format_tel = pVO.getTel();
			format_tel = format_tel.substring(0, 4) + "-" + format_tel.substring(4, 7) + "-"
					+ format_tel.substring(7, 10);
			pVO.setTel(format_tel);
			return pVO;
		};

		List<PracticeVO> lambdaList = list.stream()
				// .filter(predicate_Sex)
				.map(function_Tel_Format)
				// .sorted(comparator_Id)
				.collect(Collectors.toList());
		return lambdaList;
	}

	@Override
	public List<PracticeVO> getPaging(Integer nthPage, Integer maxPerPage) {

		Integer first = nthPage * maxPerPage + 1;
		Integer last = (nthPage == 0) ? maxPerPage : (nthPage + 1) * maxPerPage;

		String sql = "SELECT id, sex, name, tel FROM (SELECT id, sex, name, tel ,ROW_NUMBER() "
				+ "OVER(ORDER BY id DESC) As RowNum FROM Practice ) As A WHERE A.RowNum " + "BETWEEN " + first + " AND "
				+ last + " ;";

		List<PracticeVO> list = null;
		try {
			list = getSession().createNativeQuery(sql, PracticeVO.class).getResultList();
		} catch (RuntimeException ex) {
			throw ex;
		}
		return list;
	}

	@Override
	public Integer getListSize() {
		Integer listSize;
		try {
			listSize = (Integer) getSession().createNativeQuery("select count(*) from practice;").getResultList()
					.get(0);
		} catch (RuntimeException ex) {
			throw ex;
		}
		return listSize;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void insert(PracticeVO pVO) {
		try {
			getSession().saveOrUpdate(pVO);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public void update(PracticeVO pVO) {
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
			PracticeVO pVO = session.get(PracticeVO.class, id);
			getSession().delete(pVO);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

}