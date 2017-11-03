package com.voka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voka.model.*;

@Service
@Transactional(readOnly = false)
public class TransService {

	@Autowired
	private PracticeDAO_interface dao;
	
	@Transactional
	public PracticeVO addPractice(Integer id, String sex, String name, String tel) {
		
		PracticeVO practiceVO = new PracticeVO();
		practiceVO.setId(id);
		practiceVO.setSex(sex);
		practiceVO.setName(name);
		practiceVO.setTel(tel);
		
		dao.insert(practiceVO);
		
		return practiceVO;
	}
	
	@Transactional
	public void addPractice(PracticeVO practiceVO) {
		dao.insert(practiceVO);
	}
	
	@Transactional
	public PracticeVO updatePractice(Integer id, String sex, String name, String tel) {

		PracticeVO practiceVO = new PracticeVO();
		practiceVO.setId(id);
		practiceVO.setSex(sex);
		practiceVO.setName(name);
		practiceVO.setTel(tel);
		
		dao.update(practiceVO);
		
		return practiceVO;
	}
	
	@Transactional
	public void updatePractice(PracticeVO practiceVO) {
		dao.update(practiceVO);
	}
	
	@Transactional
	public void deletePractice(Integer id) {
		dao.delete(id);
	}
}
