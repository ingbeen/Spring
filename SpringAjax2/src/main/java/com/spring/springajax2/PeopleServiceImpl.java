package com.spring.springajax2;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mapper.PeopleMapper;

@Service("peopleService") 
public class PeopleServiceImpl implements PeopleService {
	
	@Autowired
	private SqlSession sqlSession; 
	
	@Override
	public List<PeopleVO> getPeoplejson() {
		List<PeopleVO> peopleList = null;
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		peopleList = memberMapper.getPeopleList();
		
		return peopleList;
	}

	@Override
	public void insertPeople(PeopleVO vo) {
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		memberMapper.insertPeople(vo);
		return;
	}
	
	@Override
	public PeopleVO getPeople(String id) {
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		PeopleVO vo = memberMapper.getPeople(id);
		
		return vo;
	}

	@Override
	public void updatePeople(PeopleVO vo) {
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		memberMapper.updatePeople(vo);

		return;
	}
	
	@Override
	public void deletePeople(String id) {
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		memberMapper.deletePeople(id);
		return;
	}
}
