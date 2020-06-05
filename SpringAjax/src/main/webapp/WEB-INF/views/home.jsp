<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!-- 
create table people(
  id varchar2(10) primary key,
  name varchar2(20),
  job varchar2(20),
  address varchar2(40),
  bloodtype varchar2(2)
);
 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>데이터 베이스 연동</title>
<style type="text/css">
#insert_form {
	width: 300px;
	margin: 10px auto;
}

#update_form {
	width: 700px;
	margin: 10px auto;
}

ul {
	padding: 0;
	margin: 0;
	list-style: none;
}

ul li {
	padding: 0;
	margin: 0 0 10px 0;
}

label {
	width: 150px;
	float: left;
}

table {
	border: 1px solid gray;
	width: 700px;
	margin: 0 auto;
	border-collapse: collapse;
}

td {
	border: 1px solid gray;
	width: 100px;
}

input {
	width: 100px;
}
</style>
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
function selectData() {
	$('#output').empty(); // table내부 내용을 제거(초기화)
	$.ajax({ // ajax를 위한 함수 'jQuery.ajax'와 동일하다
		url : 'getPeopleJSON.do',
		type : 'POST',
		dataType : "json", // 서버에 보내줄 데이터 타입
		contentType : 'application/x-www-form-urlencoded; charset=utf-8',
		
		// jquery의 작업이 실행되면은 실행될 함수
		// 컨트롤러의 리턴값의 데이터가 파라미터의 data로 들어간다
		success : function(list) {
			// $.each를 하게 되면 data의 갯수만큼 반복 호출하게 된다
			// data가 가지고 있는 PeopleVO를 item에 할당한다
			// index = 0, 1, 2, 3, 4 이런식으로  item과 같이 자동으로 넘어감
			// item = this로 대체 가능
			$.each(list, function(index, item) {
				var output = '';
				output += '<tr align="center">';
				output += '<td>' + this.id + '</td>';
				output += '<td>' + this.name + '</td>';
				output += '<td>' + this.job + '</td>';
				output += '<td>' + this.address + '</td>';
				output += '<td>' + this.bloodtype + '</td>';
				output += '<td>';
				output += '<a href="updateForm.do" class="updateForm" ';
				output += 'id="' + this.id + '">수정</a>';
				output += '&nbsp;&nbsp;';
				output += '<a href="deletePeople.do" class="delete_data" ';
				output += 'id="' + this.id + '">삭제</a>';
				output += '</td>';
				output += '</tr>';
				console.log(output);
				// id가 output인 태그 안에 output객체의 내용을 추가한다
				$('#output').append(output);
			});
		},
		error : function() {
			alert("ajax통신 실패");
		}
	});
};

$(document).ready(function() {
	
	// 추가
	$('#input_data').click(function(event) {
		var params = $("#insert_form").serialize();
		jQuery.ajax({
			url : 'insertPeople.do',
			type : 'POST',
			data : params,
			dataType : "json",
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			success : function(retVal) {
				if (retVal.res == "OK") {
					// 데이터 성공일때 이벤트 작성
					selectData();
					// 초기화
					$('#id').val('');
					$('#name').val('');
					$('#job').val('');
					$('#address').val('');
					$('#bloodtype').val('');
				} else {
					alert("Insert Fail");
				}
			},
			error : function() {
				alert("ajax통신 실패");
			}
		});
		// 기본 이벤트 제거
		event.preventDefault();
	});
	
	// 삭제
	// click = 클릭했을때 발동
	// .delete_data = class의 이름이 delete_data 인것에 대해
	// function(event) {~~ = 정의된 문구 실행
	$(document).on('click', '.delete_data', function(event) {
		$.ajax({
			// this = 클릭한 태그(여기서는 a태그)
			// href = 해당 태그의 href속성의 값
			url : $(this).attr("href"),
			type : 'POST',
			data : {'id' : $(this).attr("id")},
			dataType : "json",
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			success : function (retVal) {
				if (retVal.res == "OK") {
					selectData();
				} else {
					alert("Delete Fail");
				}
			},
			error : function() {
				alert("ajax통신 실패");
			}
		})
		event.preventDefault();
	})
	
	// 수정 Form으로 변경
	$(document).on('click', '.updateForm', function(event) {
		$.ajax({
			// this = 클릭한 태그(여기서는 a태그)
			// href = 해당 태그의 href속성의 값
			url : $(this).attr("href"),
			type : 'POST',
			data : {'id' : $(this).attr("id")},
			dataType : "json",
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			success : function (peopleVO) {
				
				var output = '<input type="hidden" name="id" class="' + peopleVO.id + '" value="' + peopleVO.id +'">';
				output += '<td>' + peopleVO.id + '</td>';
				output += '<td><input type="text" name="name" class="' + peopleVO.id + '" value="' + peopleVO.name +'"></td>';
				output += '<td><input type="text" name="job" class="' + peopleVO.id + '" value="' + peopleVO.job +'"></td>';
				output += '<td><input type="text" name="address" class="' + peopleVO.id + '" value="' + peopleVO.address +'"></td>';
				output += '<td><input type="text" name="bloodtype" class="' + peopleVO.id + '" value="' + peopleVO.bloodtype +'"></td>';
				output += '<td>';
				output += '<a href="update.do" class="update" ';
				output += 'id="' + peopleVO.id + '">수정</a>';
				output += '&nbsp;&nbsp;';
				output += '<a href="deletePeople.do" class="delete_data" ';
				output += 'id="' + peopleVO.id + '">삭제</a>';
				output += '</td>';
				
				$('#' + peopleVO.id).parent().parent().html(output);
				
			},
			error : function() {
				alert("ajax통신 실패");
			}
		})
		event.preventDefault();
	})
	
	// 수정 실행
	$(document).on('click', '.update', function(event) {
		
		var id = $(this).attr("id")
		var params = 'id=' + $('.' + id + '[name=id]').val();
		params += '&name=' + $('.' + id + '[name=name]').val();
		params += '&job=' + $('.' + id + '[name=job]').val();
		params += '&address=' + $('.' + id + '[name=address]').val();
		params += '&bloodtype=' + $('.' + id + '[name=bloodtype]').val();
		
		$.ajax({
			// this = 클릭한 태그(여기서는 a태그)
			// href = 해당 태그의 href속성의 값
			url : $(this).attr("href"),
			type : 'POST',
			data : params,
			dataType : "json",
			contentType : 'application/x-www-form-urlencoded; charset=utf-8',
			success : function (peopleVO) {
				
				var output = '<td class="id">' + peopleVO.id + '</td>';
				output += '<td class="name">' + peopleVO.name + '</td>';
				output += '<td class="job">' + peopleVO.job + '</td>';
				output += '<td class="address">' + peopleVO.address + '</td>';
				output += '<td class="bloodtype">' + peopleVO.bloodtype + '</td>';
				output += '<td>';
				output += '<a href="updateForm.do" class="updateForm" ';
				output += 'id="' + peopleVO.id + '">수정</a>';
				output += '&nbsp;&nbsp;';
				output += '<a href="deletePeople.do" class="delete_data" ';
				output += 'id="' + peopleVO.id + '">삭제</a>';
				output += '</td>';
				
				$('#' + peopleVO.id).parent().parent().html(output);
				
			},
			error : function() {
				alert("ajax통신 실패");
			}
		})
		event.preventDefault();
	})
	
	// 첫화면 조회
	selectData();
});

</script>
</head>
<body>
	<form id="insert_form" method="POST">
		<fieldset>
			<legend>데이터 추가</legend>
			<ul>
				<li><label for="id">아이디</label> <input type="text" name="id"
					id="id"></li>
				<li><label for="id">이름</label> <input type="text" name="name"
					id="name"></li>
				<li><label for="id">직업</label> <input type="text" name="job"
					id="job"></li>
				<li><label for="id">주소</label> <input type="text"
					name="address" id="address"></li>
				<li><label for="id">형액형</label> <input type="text"
					name="bloodtype" id="bloodtype"></li>
				<li><input type="button" value="추가" id="input_data"></li>
			</ul>
		</fieldset>
	</form>
	<table id="output" align="center"></table>
</body>
</html>