package com.project.model.stock;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class StockInfoImpl implements StockInfoInterface {
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	@Transactional(readOnly = true)
	public String getSecurityType(String securityCode) {
		StockInfo info = getSession().get(StockInfo.class, securityCode);
		return info == null ? null : info.getSecurityType();
	}
	
	@Override
	public Class<?> getTypeClass(String securityCode) {
		return getClass(getSecurityType(securityCode));
	}

	@Override
	public Class<?> getClass(String securityType) {
		Class<?> c = null;
		if(StringUtils.equals(securityType, "01"))
			c = Stock_01.class;
		else if(StringUtils.equals(securityType, "02"))
			c = Stock_02.class;
		else if(StringUtils.equals(securityType, "03"))
			c = Stock_03.class;
		else if(StringUtils.equals(securityType, "04"))
			c = Stock_04.class;
		else if(StringUtils.equals(securityType, "05"))
			c = Stock_05.class;
		else if(StringUtils.equals(securityType, "06"))
			c = Stock_06.class;
		else if(StringUtils.equals(securityType, "07"))
			c = Stock_07.class;
		else if(StringUtils.equals(securityType, "08"))
			c = Stock_08.class;
		else if(StringUtils.equals(securityType, "09"))
			c = Stock_09.class;
		else if(StringUtils.equals(securityType, "10"))
			c = Stock_10.class;
		else if(StringUtils.equals(securityType, "11"))
			c = Stock_11.class;
		else if(StringUtils.equals(securityType, "12"))
			c = Stock_12.class;
		else if(StringUtils.equals(securityType, "13"))
			c = Stock_13.class;
		else if(StringUtils.equals(securityType, "14"))
			c = Stock_14.class;
		else if(StringUtils.equals(securityType, "15"))
			c = Stock_15.class;
		else if(StringUtils.equals(securityType, "16"))
			c = Stock_16.class;
		else if(StringUtils.equals(securityType, "17"))
			c = Stock_17.class;
		else if(StringUtils.equals(securityType, "18"))
			c = Stock_18.class;
		else if(StringUtils.equals(securityType, "20"))
			c = Stock_20.class;
		else if(StringUtils.equals(securityType, "23"))
			c = Stock_23.class;
		else if(StringUtils.equals(securityType, "0099P"))
			c = Stock_0099P.class;
		return c;
	}

	@Override
	@Transactional(readOnly = true)
	public StockInfo getStockInfo(String securityCode) {
		return getSession().get(StockInfo.class, securityCode);
	}
}
