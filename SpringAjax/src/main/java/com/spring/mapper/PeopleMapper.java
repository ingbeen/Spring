package com.spring.mapper;

import java.util.List;

import com.spring.springajax.PeopleVO;

public interface PeopleMapper {
	List<PeopleVO> getPeopleList();
	int insertPeople(PeopleVO vo);
	void deletePeople(String id);
	PeopleVO updateForm(String id);
	void update(PeopleVO vo);
	PeopleVO getPeople(PeopleVO vo);
}
