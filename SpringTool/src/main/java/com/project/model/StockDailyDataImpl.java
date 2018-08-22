package com.project.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional
	public void insert(StockDailyData data) {
		try {
			getSession().saveOrUpdate(data);
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockDailyData> getBySecurityCode(String securityCode, String startDate, String endDate) {

		//預設三個月
		if(StringUtils.isBlank(startDate)) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -3);
			startDate = MyDateUtils.sdf.format(c.getTime());
		}
		if(StringUtils.isBlank(endDate))
			endDate = MyDateUtils.sdf.format(new Date());
		
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<StockDailyData> cq = cb.createQuery(StockDailyData.class);
		Root<StockDailyData> root = cq.from(StockDailyData.class);
		cq.select(root);

		List<Predicate> tp = new ArrayList<Predicate>();

		tp.add(cb.equal(root.get("id").get("securityCode"), securityCode));
		tp.add(cb.between(root.get("id").get("tradeDate"), startDate, endDate));

		javax.persistence.criteria.Predicate[] pArray = new javax.persistence.criteria.Predicate[tp.size()];
		cq.where(tp.toArray(pArray));
		cq.orderBy(cb.desc(root.get("id").get("tradeDate")));
		TypedQuery<StockDailyData> query = getSession().createQuery(cq);

		List<StockDailyData> list = query.getResultList();
		return list;
	}

}
