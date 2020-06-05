package com.spring.springajax;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mapper.PeopleMapper;

@Service
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
	}

	@Override
	public void deletePeople(String id) {
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		memberMapper.deletePeople(id);
	}

	@Override
	public PeopleVO updateForm(String id) {
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		PeopleVO peopleVO = memberMapper.updateForm(id);
		
		return peopleVO;
	}

	@Override
	public void update(PeopleVO vo) {
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		memberMapper.update(vo);
	}

	@Override
	public PeopleVO getPeople(PeopleVO vo) {
		PeopleMapper memberMapper = sqlSession.getMapper(PeopleMapper.class);
		PeopleVO peopleVO = memberMapper.getPeople(vo);
		return peopleVO;
	}

}
