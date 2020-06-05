package com.spring.sungjuk2;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.mapper.SungjukMapper;

@Service
public class SungjukService {

	@Autowired(required = true)
	private SqlSession sqlSession;
	
	// ��ü ����Ʈ
	public ArrayList<SungjukVO> getSungjuklist() {
		ArrayList<SungjukVO> sungjukList = new ArrayList<SungjukVO>();
		SungjukMapper sungjukMapper = sqlSession.getMapper(SungjukMapper.class);
		sungjukList = sungjukMapper.getSungjuklist();

		return sungjukList;
	}

	// �� ����
	public SungjukVO selectSungjuk(SungjukVO sungjukVO) {
		SungjukMapper sungjukMapper = sqlSession.getMapper(SungjukMapper.class);
		SungjukVO vo = sungjukMapper.selectSungjuk(sungjukVO);

		return vo;
	}
	
	// ���� �Է�
	public int insertSungjuk(SungjukVO sungjukVO) {
		SungjukMapper sungjukMapper = sqlSession.getMapper(SungjukMapper.class);
		sungjukVO.processc();
		int res = sungjukMapper.insertSungjuk(sungjukVO);
		
		return res;
	}

	// ���� ����
	public int updateSungjuk(SungjukVO sungjukVO) {
		SungjukMapper sungjukMapper = sqlSession.getMapper(SungjukMapper.class);
		sungjukVO.processc();
		int res = sungjukMapper.updateSungjuk(sungjukVO);
		return res;
	}

	//���� ����
	public int deleteSungjuk(SungjukVO sungjukVO) {
		SungjukMapper sungjukMapper = sqlSession.getMapper(SungjukMapper.class);
		int res = sungjukMapper.deleteSungjuk(sungjukVO);

		return res;
	}
}