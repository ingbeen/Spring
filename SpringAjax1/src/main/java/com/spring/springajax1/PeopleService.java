package com.spring.springajax1;

import java.util.List;

public interface PeopleService {
	List<PeopleVO> getPeoplejson();
	void insertPeople(PeopleVO vo);
	PeopleVO getPeople(String id);
	void updatePeople(PeopleVO vo); 
	void deletePeople(String id);
}
