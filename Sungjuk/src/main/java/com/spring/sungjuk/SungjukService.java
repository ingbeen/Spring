package com.spring.sungjuk;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SungjukService {

	@Autowired(required = true)
	private SungjukDAO sungjukDAO = null;

	public ArrayList<SungjukVO> getSungjuklist() {
		ArrayList<SungjukVO> sungjukList = new ArrayList<SungjukVO>();
		sungjukList = sungjukDAO.getSungjuklist();

		return sungjukList;
	}

	public int insertSungjuk(SungjukVO sungjukVO) {
		int res = sungjukDAO.insertSungjuk(sungjukVO);

		return res;
	}

	public SungjukVO selectSungjuk(SungjukVO sungjukVO) {
		SungjukVO vo = sungjukDAO.selectSungjuk(sungjukVO);

		return vo;
	}

	public int updateSungjuk(SungjukVO sungjukVO) {
		int res = sungjukDAO.updateSungjuk(sungjukVO);

		return res;
	}

	public int deleteSungjuk(SungjukVO sungjukVO) {
		int res = sungjukDAO.deleteSungjuk(sungjukVO);

		return res;
	}

}
