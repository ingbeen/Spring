package com.spring.sungjuk2;

// ���� : VO -> Mapper.xml -> Mapper.java -> Service -> Controller

public class SungjukVO {
	private String hakbun;
	private String irum;
	private int kor;
	private int eng;
	private int math;
	private int tot;
	private double avg;
	private String grade;
	
	public String getHakbun() {
		return hakbun;
	}
	public void setHakbun(String hakbun) {
		this.hakbun = hakbun;
	}
	public String getIrum() {
		return irum;
	}
	public void setIrum(String irum) {
		this.irum = irum;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public int getTot() {
		return tot;
	}
	public void setTot(int tot) {
		this.tot = tot;
	}
	public double getAvg() {
		return avg;
	}
	public void setAvg(double avg) {
		this.avg = avg;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public void processc() {
		tot = kor + eng + math;
		avg = (double) tot / 3;

		if (avg >= 90) {
			grade = "��";
		} else if (avg >= 80) {
			grade = "��";
		} else if (avg >= 70) {
			grade = "��";
		} else if (avg >= 60) {
			grade = "��";
		} else {
			grade = "��";
		}
	}
}