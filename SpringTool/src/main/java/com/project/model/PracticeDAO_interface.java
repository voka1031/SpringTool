package com.project.model;
import java.util.*;

public interface PracticeDAO_interface {
          public void insert(PracticeVO pVO);
          public void update(PracticeVO pVO);
          public void delete(Integer id);
          public List<PracticeVO> getPaging(Integer nthPage, Integer maxPerPage);
          public List<PracticeVO> getAll();
          public Integer getListSize();
          public List<PracticeVO> getCriteriaBuilderList();
          public List<PracticeVO> getLambdaList();
          public PracticeVO findByPrimaryKey(Integer id);
          public List<PracticeVO> getByGender(String gender);
}