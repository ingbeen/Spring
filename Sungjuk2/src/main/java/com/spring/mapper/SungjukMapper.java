package com.spring.mapper;

import java.util.ArrayList;

import com.spring.sungjuk2.SungjukVO;


public interface SungjukMapper {
	ArrayList<SungjukVO> getSungjuklist();
	SungjukVO selectSungjuk(SungjukVO sungjukVO);
	int insertSungjuk(SungjukVO sungjukVO);
	int updateSungjuk(SungjukVO sungjukVO);
	int deleteSungjuk(SungjukVO sungjukVO);
}
