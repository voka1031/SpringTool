package com.project.model.stock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.util.MyDateUtils;

@Repository
public class StockDailyDataImpl implements StockDailyDataInterface {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private StockInfoInterface stockInfoRepo;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional
	public void insert(String entityName, TemplateStockData data) {
		try {
			getSession().saveOrUpdate(entityName, data);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly = true)
	public List<? extends TemplateStockData> getStock(String securityCode, String startDate, String endDate) {
		Class<?> classType = stockInfoRepo.getTypeClass(securityCode);

		startDate = getStartDate(startDate);
		endDate = getEndDate(endDate);

		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(classType);
		Root root = cq.from(classType);
		cq.select(root);

		List<Predicate> tp = new ArrayList<Predicate>();
		tp.add(cb.equal(root.get("securityCode"), securityCode));
		tp.add(cb.between(root.get("tradeDate"), startDate, endDate));

		Predicate[] pArray = new Predicate[tp.size()];
		cq.where(tp.toArray(pArray));
		cq.orderBy(cb.desc(root.get("tradeDate")));

		TypedQuery query = getSession().createQuery(cq);

		return query.getResultList();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly = true)
	public List<? extends TemplateStockData> getMA(String securityCode, String startDate, String endDate, int countdays) {

		Class<?> classType = stockInfoRepo.getTypeClass(securityCode);

		startDate = getStartDate(startDate);
		endDate = getEndDate(endDate);

		// step1 count(between days)
		CriteriaBuilder countBuilder = getSession().getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
		Root countRoot = countQuery.from(classType);
		countQuery.select(countBuilder.count(countRoot));

		List<Predicate> countPList = new ArrayList<Predicate>();
		countPList.add(countBuilder.equal(countRoot.get("securityCode"), securityCode));
		countPList.add(countBuilder.between(countRoot.get("tradeDate"), startDate, endDate));

		Predicate[] countPArray = new Predicate[countPList.size()];
		countQuery.where(countPList.toArray(countPArray));
		countQuery.orderBy(countBuilder.desc(countRoot.get("tradeDate")));
		Long dataCount = getSession().createQuery(countQuery).getSingleResult();

		// step2 rslt
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(classType);
		Root root = cq.from(classType);
		cq.select(root);

		List<Predicate> tp = new ArrayList<Predicate>();
		tp.add(cb.equal(root.get("securityCode"), securityCode));
		tp.add(cb.lessThanOrEqualTo(root.get("tradeDate"), endDate));

		Predicate[] pArray = new Predicate[tp.size()];
		cq.where(tp.toArray(pArray));
		cq.orderBy(cb.desc(root.get("tradeDate")));

		TypedQuery query = getSession().createQuery(cq).setMaxResults(dataCount.intValue() + countdays);

		return query.getResultList();
	}

	private String getEndDate(String endDate) {
		if (StringUtils.isBlank(endDate))
			endDate = MyDateUtils.sdf.format(new Date());
		return endDate;
	}

	private String getStartDate(String startDate) {
		// 預設查詢範圍:近6個月
		if (StringUtils.isBlank(startDate)) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -6);
			startDate = MyDateUtils.sdf.format(c.getTime());
		}
		return startDate;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional(readOnly = true)
	public boolean isDataExistByTradeDate(String type, String tradeDate) {

		Class<?> classType = stockInfoRepo.getClass(type);

		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery(classType);
		Root root = cq.from(classType);
		cq.select(root.get("tradeDate")).distinct(true);

		List<Predicate> tp = new ArrayList<Predicate>();
		tp.add(cb.equal(root.get("tradeDate"), tradeDate));

		Predicate[] pArray = new Predicate[tp.size()];
		cq.where(tp.toArray(pArray));

		TypedQuery query = getSession().createQuery(cq);
		String rslt;
		try {
			rslt = (String) query.getSingleResult();
			System.out.printf("isDataExistByTradeDate date : %s, type : %s rslt : %s", tradeDate, type, rslt).println();
			return StringUtils.isNotBlank(rslt);
		} catch (NoResultException e) {
			System.out.printf("NoResultException date : %s, type : %s", tradeDate, type).println();
			return false;
		}

	}

}
