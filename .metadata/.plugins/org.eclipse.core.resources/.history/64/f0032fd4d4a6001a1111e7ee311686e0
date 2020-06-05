<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
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
 form{
  width:500px;
  margin:10px auto;
 }
 ul{
  padding:0;
  margin:0;
  list-style:none;
 }
 ul li{
  padding:0;
  margin:0 0 10px 0;
 }
 label{
  width:150px;
  float:left;
 }
 table {
  border:1px solid gray;
  width:500px;
  margin:0 auto;
  border-collapse: collapse;
 }
 td{
  border:1px solid gray;
 }
</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
//목록
function selectData(){
	$('#output').empty(); //table내부 내용을 제거(초기화)

	$.ajax({
        url:'/springajax1/getPeopleJSON.do',
        type:'POST',
        dataType : "json", // 서버에서 보내줄 데이터 타입
        contentType : 'application/x-www-form-urlencoded; charset=utf-8',
        success:function(data){
        	$.each(data, function(index, item){
       	       	var output = '';
       	       	output += '<tr align="center">';
       	       	output += '<td width="80">' + item.id + '</td>';
       	       	output += '<td width="80">' + item.name + '</td>';
       	       	output += '<td width="80">' + item.job + '</td>';
       	       	output += '<td width="80">' + item.address + '</td>';
       	       	output += '<td width="70">' + item.bloodtype + '</td>';
       	     	output += '<td width="110"><a href="/springajax1/getPeopleJSON.do" class="updateform_data" ';
	     		output += 'id=' + item.id + '>수정</a> ';
       	    	output += '<a href="/springajax1/deletePeople.do" class="delete_data" ';
   	     		output += 'id=' + item.id + '>삭제</a></td>';
       	       	output += '</tr>';
       	       	$('#output').append(output);
       	  	});
    	},
    	error:function(){
        alert("ajax통신 실패!!!");
    	}
	}); 
}
	
$(document).ready(function(){ 
	
 	$('#input_data').click(function(event){
	  	var params = $("#insert_form").serialize();
	  	jQuery.ajax({
	       	url : '/springajax1/insertPeople.do',
	       	type : 'POST',
	       	data : params, // 서버로 보낼 데이터
	       	contentType : 'application/x-www-form-urlencoded; charset=utf-8', 
	       	dataType : "json", // 서버에서 보내줄 데이터 타입
	       	success: function (retVal) {
	           	if (retVal.res == "OK"){
	              	// 데이타 성공일때 이벤트 작성
	           		selectData();
	           		//초기화
	              	$('#id').val('');
	              	$('#name').val('');
	              	$('#job').val('');
	              	$('#address').val('');
	              	$('#bloodtype').val('');
	           	}
	           	else
        	 	{
        	 		alert("Insert Fail!!!");
        	 	}
	       	},
	       	error:function(){
          		alert("ajax통신 실패!!!");
	       	}
	  	});
	  	//기본 이벤트 제거
	  	event.preventDefault();
	});

 	$(document).on('click', '.updateform_data', function(event){
 		$('#output').empty();
  		var id = $(this).attr("id");
	  	jQuery.ajax({
	       	url : $(this).attr("href"),
	       	type : 'GET',
	       	data : {'id' : $(this).attr("id")},
	       	contentType : 'application/x-www-form-urlencoded; charset=utf-8', 
	       	dataType : 'json',
	       	success: function (data) {	
	       		$.each(data, function(index, item){
	       	       	var output = '';
	       	       	if (id != item.id) {
		       	       	output += '<tr align="center">';
		       	       	output += '<td width="80">' + item.id + '</td>';
		       	       	output += '<td width="80">' + item.name + '</td>';
		       	       	output += '<td width="80">' + item.job + '</td>';
		       	       	output += '<td width="80">' + item.address + '</td>';
		       	       	output += '<td width="70">' + item.bloodtype + '</td>';
		       	     	output += '<td width="110"><a href="/springajax1/getPeopleJSON.do" class="updateform_data" ';
			     		output += 'id=' + item.id + '>수정</a> ';
		       	    	output += '<a href="/springajax1/deletePeople.do" class="delete_data" ';
		   	     		output += 'id=' + item.id + '>삭제</a></td>';
		       	       	output += '</tr>';	
	       	       	}
	       	       	else{
		       	       	output += '<tr align="center">'
		       	       	output += '<td width="80"><input type="hidden" size="4" name="id" value="' + item.id + '">' + item.id + '</td>';
		       	     	output += '<td width="80"><input type="text" size="4" name="name" value="' + item.name + '"></td>';
			       	  	output += '<td width="80"><input type="text" size="3" name="job" value="' + item.job + '"></td>';
				       	output += '<td width="80"><input type="text" size="2" name="address" value="' + item.address + '"></td>';
				       	output += '<td width="70"><input type="text" size="2" name="bloodtype" value="' + item.bloodtype + '"></td>';
				       	output += '<td width="110"><a href="/springajax1/updatePeople.do" class="update_data" ';
			       		output += 'id=' + item.id + '>수정</a> ';
			       		output +='<a href="#" onclick="selectData()">목록</a></td>';
		       			output += '</tr>';
	       	       	}
	       	     	$('#output').append(output);
	       	       		
	       	  	});
	       		
	       	},
	       	error:function(){
	          	alert("ajax통신 실패!!!");
	       	}
	  	});

	  	//기본 이벤트 제거
	  	event.preventDefault();
	});
 	
 	$(document).on('click', '.update_data', function(event){
 		var params = $("#update_form").serialize();
 		jQuery.ajax({
	       	url : $(this).attr("href"),
	       	type : 'POST',
	       	data : params,
	       	contentType : 'application/x-www-form-urlencoded; charset=utf-8', 
	       	dataType : 'json',
	       	success: function (retVal) {
	           	if (retVal.res == "OK"){
	              	// 데이타 성공일때 이벤트 작성
	           		selectData();
	           	}
	           	else
        	 	{
        	 		alert("Update Fail!!!");
        	 	}
	       	},
	       	error:function(){
	          	alert("ajax통신 실패!!!");
	       	}
	  	});

	  	//기본 이벤트 제거
	  	event.preventDefault();
	});

 	$(document).on('click', '.delete_data', function(event){
	  	jQuery.ajax({
	       	url : $(this).attr("href"),
	       	type : 'GET',
	       	data : {'id' : $(this).attr("id")},
	       	contentType : 'application/x-www-form-urlencoded; charset=utf-8', 
	       	dataType : 'json',
	       	success: function (retVal) {
	           	if (retVal.res == "OK"){ // 데이타 성공일때 이벤트 작성
	           		selectData(); 
	           	}
	           	else
        	 	{
        	 		alert("Insert Fail!!!");
	        	}
	       	},
	       	error:function(){
	          	alert("ajax통신 실패!!!");
	       	}
	  	});

	  	//기본 이벤트 제거
	  	event.preventDefault();
	});

  	selectData();
});
 
</script>
</head>
<body>
 <form id ="insert_form" method="post">
  <fieldset>
   <legend>데이터 추가</legend>
   <ul>
    <li>
     <label for="id">아이디</label>
     <input type="text" name="id" id="id">
    </li>
    <li>
     <label for="name">이름</label>
     <input type="text" name="name" id="name">
    </li>
    <li>
     <label for="job">직업</label>
     <input type="text" name="job" id="job">
    </li>
    <li>
     <label for="address">주소</label>
     <input type="text" name="address" id="address">
    </li>
    <li>
     <label for="bloodtype">혈액형</label>
     <input type="text" name="bloodtype" id="bloodtype">
    </li>
    <li>
     <input type="button" value="추가" id="input_data">
    </li>
   </ul>
  </fieldset>
 </form>
 <form id ="update_form" method="post">
 	<table id="output"></table>
 </form>
</body>
</html>