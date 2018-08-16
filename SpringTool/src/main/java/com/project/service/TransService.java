package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.project.model.*;

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

	@Transactional(propagation = Propagation.REQUIRED)
	public void addPractice(PracticeVO practiceVO) {
		dao.insert(practiceVO);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void transRequriedTest1() {
		addPractice1();
		addPracticeREQUIRED();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addPractice1() {
		PracticeVO vo = new PracticeVO();
		vo.setName("一一一");
		vo.setSex("F");
		vo.setTel("0912111111");
		dao.insert(vo);
		// addPracticeREQUIRED();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addPracticeREQUIRED() {
		PracticeVO vo = new PracticeVO();
		vo.setName("Required");
		vo.setSex("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
		vo.setTel("0912333333");
		dao.insert(vo);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void transRequriedTest2() {
		addPractice2();
		//addPracticeREQUIRES_NEW();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void addPractice2() {
		PracticeVO vo = new PracticeVO();
		vo.setName("二二二");
		vo.setSex("M");
		vo.setTel("0221222222");
		dao.insert(vo);
		// addPracticeREQUIRES_NEW();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void addPracticeREQUIRES_NEW() {
		PracticeVO vo = new PracticeVO();
		vo.setName("New");
		vo.setSex("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
		vo.setTel("0912444444");
		dao.insert(vo);
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
