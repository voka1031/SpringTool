package com.project.model;

import java.io.Serializable;
import javax.persistence.*;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;


/**
 * The persistent class for the Coordinate database table.
 * 
 */
@Entity
@NamedQuery(name="Coordinate.findAll", query="SELECT c FROM Coordinate c")
public class Coordinate implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="Id")
	private String id;

	@Column(name="address")
	private String address;

	@Column(name="age_y")
	private BigDecimal ageY;

	@Column(name="Bdarea")
	private BigDecimal bdarea;

	@Column(name="Bdfndate")
	private Timestamp bdfndate;

	@Column(name="Bdtype")
	private String bdtype;

	@Column(name="County")
	private String county;

	@Column(name="createDate", insertable=false, updatable=false)
	private Timestamp createDate;

	@Column(name="Hall_f")
	private BigDecimal hall_f;
	
	@Column(name="IsActive")
	private String isActive;

	@Column(name="IsEloanData")
	private String isEloanData;

	@Column(name="Landarea")
	private BigDecimal landarea;

	@Column(name="lat")
	private BigDecimal lat;

	@Column(name="Location")
	private String location;

	@Column(name="location_no")
	private String locationNo;

	@Column(name="lon")
	private BigDecimal lon;

	@Column(name="Mainmt")
	private String mainmt;

	@Column(name="Mainuse")
	private String mainuse;

	@Column(name="Other")
	private String other;

	@Column(name="park")
	private BigDecimal park;

	@Column(name="Parkarea")
	private BigDecimal parkarea;

	@Column(name="Parktype")
	private String parktype;

	@Column(name="price")
	private BigDecimal price;

	@Column(name="Room_f")
	private BigDecimal room_f;

	@Column(name="Ruraltype")
	private String ruraltype;

	@Column(name="Ruraltype_Dt")
	private String ruraltype_Dt;

	@Column(name="Structure")
	private String structure;

	@Column(name="Toilet_f")
	private BigDecimal toilet_f;

	@Column(name="Tolfloor")
	private String tolfloor;

	@Column(name="Tolparkprice")
	private BigDecimal tolparkprice;

	@Column(name="Tolprice")
	private BigDecimal tolprice;

	@Column(name="Tradedate")
	private Timestamp tradedate;

	@Column(name="Transfloor")
	private String transfloor;

	@Column(name="updateDate", insertable=false)
	private Timestamp updateDate;

	@Column(name="Usetype")
	private String usetype;
	
	@Transient
	private double distance; 
	
	public Coordinate() {
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Coordinate(Row row) {
		isEloanData = getCellString(row.getCell(0));
		address = getCellString(row.getCell(1));
		id = getCellString(row.getCell(2));
		tradedate = getCellTimestamp(row.getCell(3));
		bdfndate = getCellTimestamp(row.getCell(4));
		ageY = getCellBigDecimal(row.getCell(5));
		county = getCellString(row.getCell(6));
		locationNo = getCellString(row.getCell(7));
		location = getCellString(row.getCell(8));
		bdtype = getCellString(row.getCell(9));
		mainuse = getCellString(row.getCell(10));
		usetype = getCellString(row.getCell(11));
		mainmt = getCellString(row.getCell(12));
		tolfloor = getCellString(row.getCell(13));
		transfloor = getCellString(row.getCell(14));
		park = getCellBigDecimal(row.getCell(15));
		room_f = getCellBigDecimal(row.getCell(16));
		hall_f = getCellBigDecimal(row.getCell(17));
		toilet_f = getCellBigDecimal(row.getCell(18));
		structure = getCellString(row.getCell(19));
		parktype = getCellString(row.getCell(20));
		ruraltype = getCellString(row.getCell(21));
		ruraltype_Dt = getCellString(row.getCell(22));
		landarea = convertArea(getCellBigDecimal(row.getCell(23)));
		bdarea = convertArea(getCellBigDecimal(row.getCell(24)));
		parkarea = convertArea(getCellBigDecimal(row.getCell(25)));
		tolprice = getCellBigDecimal(row.getCell(26));
		tolparkprice = getCellBigDecimal(row.getCell(27));
		price = convertPrice(getCellBigDecimal(row.getCell(28)));
		other = getCellString(row.getCell(29));
	}
	private static BigDecimal getCellBigDecimal(Cell cell) {
		return null == cell ? null : new BigDecimal(cell.toString());
	}
	private static String getCellString(Cell cell) {
		return null == cell ? null : cell.toString();
	}
	private static Timestamp getCellTimestamp(Cell cell) {
		try {
			return null == cell ? null : new Timestamp(DateUtils.parseDate(cell.toString(), "yyyy/MM/dd").getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	private static BigDecimal convertArea(BigDecimal bd) {
		// 1平方公尺 = 0.3025 坪； 1坪 = 3.30579 平方公尺
		if (null != bd) {
			bd = bd.multiply(new BigDecimal("0.3025"));
		}
		return bd;
	}
	
	private static BigDecimal convertPrice(BigDecimal price) {
		// 1平方公尺 = 0.3025 坪； 1坪 = 3.30579 平方公尺
		//坪數*0.3025  則價格/0.3025
		if (null != price) {
			price = price.divide(new BigDecimal("0.3025"),0,BigDecimal.ROUND_HALF_UP);
		}
		return price;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAgeY() {
		return this.ageY;
	}

	public void setAgeY(BigDecimal ageY) {
		this.ageY = ageY;
	}

	public BigDecimal getBdarea() {
		return this.bdarea;
	}

	public void setBdarea(BigDecimal bdarea) {
		this.bdarea = bdarea;
	}

	public Timestamp getBdfndate() {
		return this.bdfndate;
	}

	public void setBdfndate(Timestamp bdfndate) {
		this.bdfndate = bdfndate;
	}

	public String getBdtype() {
		return this.bdtype;
	}

	public void setBdtype(String bdtype) {
		this.bdtype = bdtype;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public BigDecimal getHall_f() {
		return this.hall_f;
	}

	public void setHall_f(BigDecimal hall_f) {
		this.hall_f = hall_f;
	}
	
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getIsEloanData() {
		return this.isEloanData;
	}

	public void setIsEloanData(String isEloanData) {
		this.isEloanData = isEloanData;
	}

	public BigDecimal getLandarea() {
		return this.landarea;
	}

	public void setLandarea(BigDecimal landarea) {
		this.landarea = landarea;
	}

	public BigDecimal getLat() {
		return this.lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationNo() {
		return this.locationNo;
	}

	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	public BigDecimal getLon() {
		return this.lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	public String getMainmt() {
		return this.mainmt;
	}

	public void setMainmt(String mainmt) {
		this.mainmt = mainmt;
	}

	public String getMainuse() {
		return this.mainuse;
	}

	public void setMainuse(String mainuse) {
		this.mainuse = mainuse;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public BigDecimal getPark() {
		return this.park;
	}

	public void setPark(BigDecimal park) {
		this.park = park;
	}

	public BigDecimal getParkarea() {
		return this.parkarea;
	}

	public void setParkarea(BigDecimal parkarea) {
		this.parkarea = parkarea;
	}

	public String getParktype() {
		return this.parktype;
	}

	public void setParktype(String parktype) {
		this.parktype = parktype;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getRoom_f() {
		return this.room_f;
	}

	public void setRoom_f(BigDecimal room_f) {
		this.room_f = room_f;
	}

	public String getRuraltype() {
		return this.ruraltype;
	}

	public void setRuraltype(String ruraltype) {
		this.ruraltype = ruraltype;
	}

	public String getRuraltype_Dt() {
		return this.ruraltype_Dt;
	}

	public void setRuraltype_Dt(String ruraltype_Dt) {
		this.ruraltype_Dt = ruraltype_Dt;
	}

	public String getStructure() {
		return this.structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public BigDecimal getToilet_f() {
		return this.toilet_f;
	}

	public void setToilet_f(BigDecimal toilet_f) {
		this.toilet_f = toilet_f;
	}

	public String getTolfloor() {
		return this.tolfloor;
	}

	public void setTolfloor(String tolfloor) {
		this.tolfloor = tolfloor;
	}

	public BigDecimal getTolparkprice() {
		return this.tolparkprice;
	}

	public void setTolparkprice(BigDecimal tolparkprice) {
		this.tolparkprice = tolparkprice;
	}

	public BigDecimal getTolprice() {
		return this.tolprice;
	}

	public void setTolprice(BigDecimal tolprice) {
		this.tolprice = tolprice;
	}

	public Timestamp getTradedate() {
		return this.tradedate;
	}

	public void setTradedate(Timestamp tradedate) {
		this.tradedate = tradedate;
	}

	public String getTransfloor() {
		return this.transfloor;
	}

	public void setTransfloor(String transfloor) {
		this.transfloor = transfloor;
	}

	public Timestamp getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUsetype() {
		return this.usetype;
	}

	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}	
}