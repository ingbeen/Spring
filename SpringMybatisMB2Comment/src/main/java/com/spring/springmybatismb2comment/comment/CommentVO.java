/*
 * CREATE TABLE table_comment (
    cno number not null primary key, // �Ϸù�ȣ
    bno number not null, // board���̺��� ��ȣ(�ܷ�Ű����)
    content varchar2(2000) not null,
    writer varchar2(20) not null,
    reg_date date not null
	);
 */

package com.spring.springmybatismb2comment.comment;

import java.util.Date;

public class CommentVO {
	private int cno;
	private int bno;
	private String content;
	private String writer;
	private Date reg_Date;
	
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getReg_Date() {
		return reg_Date;
	}
	public void setReg_Date(Date reg_Date) {
		this.reg_Date = reg_Date;
	}
	
	
}
