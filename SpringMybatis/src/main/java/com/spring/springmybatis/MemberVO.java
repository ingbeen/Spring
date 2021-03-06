package com.spring.springmybatis;

// 순서 : VO -> Mapper.xml -> Mapper.java -> Service -> Controller
/*
mybatis의 특징은
DAO에 있는 것을 xml문서로 옮겼고
그것을 Mapper.java파일로 실행한다
 */
import org.springframework.stereotype.Component;

// 서버가 구동되면 자동으로 bean객체를 만든다
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
