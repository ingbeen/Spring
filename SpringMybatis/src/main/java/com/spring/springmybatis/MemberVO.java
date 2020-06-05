package com.spring.springmybatis;

// ���� : VO -> Mapper.xml -> Mapper.java -> Service -> Controller
/*
mybatis�� Ư¡��
DAO�� �ִ� ���� xml������ �Ű��
�װ��� Mapper.java���Ϸ� �����Ѵ�
 */
import org.springframework.stereotype.Component;

// ������ �����Ǹ� �ڵ����� bean��ü�� �����
@Component
public class MemberVO {
	private String id;
	private String name;
	private String email;
	private String phone;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}