package com.spring.springajax2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
// @RequiredArgsConstructor : final�̳� @NonNull�� �ʵ� ���� �Ķ���ͷ� �޴� �����ڸ� ����
// @Data = lombok ���̺귯���� ���� ���� ������̼��� �ڵ� �����ȴ�
// get,set�� �ϰ� ������ @Data�� ���� @Getter, @Setter�� �־��ָ� �ȴ�
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeopleVO {
	private String id;
	private String name;
	private String job;
	private String address;
	private String bloodtype;
}