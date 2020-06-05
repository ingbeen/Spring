<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>HTML5</title>
    <meta charset="utf-8">
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script>
		// 아이디 체크
	    function IDCheck (form1) {
			var id = form1.id.value;
			
			// 자릿수 검사
			if (id.length < 6 || 10 < id.length) {
				alert("아이디는 최소 6 ~ 최대 10자리를 입력해주십시오")
				form1.id.focus();
				return false;
			} else {
				// 유효값 검사
				for(var i = 0; i < id.length; i++){
					var getIDTexts = id.substr(i, 1);
					if (!(("0" <= getIDTexts && getIDTexts <= "9")
							|| ("A" <= getIDTexts && getIDTexts <= "Z")
							|| ("a" <= getIDTexts && getIDTexts <= "z"))) {
						alert("아이디는 숫자와 알파벳만 사용해주십시오")
						form1.id.focus();
						return false;
					};
				};
			};
		};
		
		
		// 비밀번호 체크
	    function passwordCheck (form1) {
			
	    	// '비밀번호' 자릿수 검사
			var password1 = form1.password1.value;
			if (password1.length < 6 || 10 < password1.length) {
				alert("비밀번호는 최소 6 ~ 최대 10자리를 입력해주십시오")
				form1.password1.focus();
				return false;
			} else {
				// '비밀번호' 유효값 검사
				for(var i = 0; i < password1.length; i++){
					var getPasswordTexts = password1.substr(i, 1);
					if (!(("0" <= getPasswordTexts && getPasswordTexts <= "9")
							|| ("A" <= getPasswordTexts && getPasswordTexts <= "Z")
							|| ("a" <= getPasswordTexts && getPasswordTexts <= "z"))) {
						alert("비밀번호는 숫자와 알파벳만 사용해주십시오")
						form1.password1.focus();
						return false;
					};
				};
			};
			
			// '비밀번호 확인' 자릿수 검사
			var password2 = form1.password2.value;
			if (password2.length < 6 || 10 < password2.length) {
				alert("비밀번호는 최소 6 ~ 최대 10자리를 입력해주십시오")
				form1.password2.focus();
				return false;
			} else {
				// '비밀번호 확인' 유효값 검사
				for(var i = 0; i < password2.length; i++){
					var getPasswordTexts = password2.substr(i, 1);
					if (!(("0" <= getPasswordTexts && getPasswordTexts <= "9")
							|| ("A" <= getPasswordTexts && getPasswordTexts <= "Z")
							|| ("a" <= getPasswordTexts && getPasswordTexts <= "z"))) {
						alert("비밀번호는 숫자와 알파벳만 사용해주십시오")
						form1.password2.focus();
						return false;
					};
				};
			};
			
			// 비밀번호 일치하는지 체크
			if (!(password1 == password2)) {
				alert("비밀번호가 일치하지 않습니다")
				form1.password1.focus();
				return false;
			}
		};
    
		
    	// 주민등록번호 체크
	    function juminCheck (form1) {
			// 앞번호, 뒷번호, 전체번호, 뒷번호 첫번째, 뒷번호 마지막
			var front_text = form1.jumin1.value;
			var back_text = form1.jumin2.value;
			var all_text = front_text + back_text;
			var back_text_0 = back_text.substr(0, 1);
			var back_text_6 = back_text.substr(6, 1);
			
			if (!(front_text.length == 6)) {
				// 앞번호 자리수 체크
				alert("주민등록번호 앞 6자리를 입력해주시기 바랍니다")
				form1.jumin1.focus();
				return false;
			} else if (!(back_text.length == 7)) {
				// 뒷번호 자리수 체크
				alert("주민등록번호 뒤 7자리를 입력해주시기 바랍니다")
				form1.jumin2.focus();
				return false;
			} else if (!(back_text_0 == 1 || back_text_0 == 2 || back_text_0 == 3 || back_text_0 == 4)) {
				// 성별값 체크
				alert("주민등록번호 뒤 첫번째 숫자는 (1, 2, 3, 4)만 유효합니다")
				form1.jumin2.focus();
				return false;
			} else {
				// (1~9)값 체크
				var i = 0;
	    		for(i = 0; i < 13; i++) {
	    			var number = all_text.substr(i, 1);
	    			if (number < "0" || "9" < number) {
	    				alert("숫자만 입력해주시기 바랍니다")
	    				form1.jumin1.focus();
	    				return false;
	    			}
	    		}
			}
			
			// 합계, 곱셈값
			var tot = 0;
			var value = 2;
			
			// 뒷자리 마지막번호 검증
			// 합계
			for(var i = 0; i < 12; i++) {
				var number = all_text.substr(i, 1);
				tot += number * value;
				value++;
				if (value == 10) {
					value = 2;
				}
			}
			
			// 나머지
			var reult = 11 - (tot % 11);
			if (reult == 11) {
				reult = 1;
			} else if (reult == 10) {
				reult = 0;
			}
			
			// 검사
			if (!(back_text_6 == reult)){
				alert("입력하신 번호를 다시 한번 확인해주시기 바랍니다")
				form1.jumin2.focus();
				return false;
			}
		}
    	
    	
    	// 성별 체크
    	function sexCheck(form1) {
    		if (!(form1.sex[0].checked == true || form1.sex[1].checked == true)) {
    			alert("성별을 선택해주세요")
    			return false;
    		}
    	}
    	
    	
    	// 전화번호 체크
	    function telCheck (form1) {
	    	// '앞자리' 자릿수 검사
			var tel1 = form1.tel1.value;
			if (tel1.length < 2 || 3 < tel1.length) {
				alert("전화번호 앞자리는 2~3자리 값으로 입력해주십시오")
				form1.tel1.focus();
				return false;
			} else {
				// '앞자리' 유효값 검사
				for(var i = 0; i < tel1.length; i++){
					var getTelTexts = tel1.substr(i, 1);
					if (getTelTexts < "0" || "9" < getTelTexts) {
						alert("전화번호는 숫자만 사용가능합니다")
						form1.tel1.focus();
						return false;
					};
				};
			};
			
			// '중간자리' 자릿수 검사
			var tel2 = form1.tel2.value;
			if (tel2.length < 3 || 4 < tel2.length) {
				alert("전화번호 중간자리는 3~4자리 값으로 입력해주십시오")
				form1.tel2.focus();
				return false;
			} else {
				// '중간자리' 유효값 검사
				for(var i = 0; i < tel2.length; i++){
					var getTelTexts = tel2.substr(i, 1);
					if (getTelTexts < "0" || "9" < getTelTexts) {
						alert("전화번호는 숫자만 사용가능합니다")
						form1.tel2.focus();
						return false;
					};
				};
			};
			
			// '끝자리' 자릿수 검사
			var tel3 = form1.tel3.value;
			if (tel3.length != 4) {
				alert("전화번호 끝자리는 4자리 값으로 입력해주십시오")
				form1.tel3.focus();
				return false;
			} else {
				// '끝자리' 유효값 검사
				for(var i = 0; i < tel3.length; i++){
					var getTelTexts = tel3.substr(i, 1);
					if (getTelTexts < "0" || "9" < getTelTexts) {
						alert("전화번호는 숫자만 사용가능합니다")
						form1.tel3.focus();
						return false;
					};
				};
			};
		};
		
		
		// 이메일 체크
	    function emailCheck (form1) {
			// '앞자리' 자릿수 검사
			var emailFront = form1.emailFront.value;
			if (emailFront.length <= 0) {
				alert("이메일주소를 입력해주십시오")
				form1.emailFront.focus();
				return false;
			} else {
				// '앞자리' 유효값 검사
				for(var i = 0; i < emailFront.length; i++){
					var getEmailTexts = emailFront.substr(i, 1);
					if (!(("0" <= getEmailTexts && getEmailTexts <= "9")
							|| ("A" <= getEmailTexts && getEmailTexts <= "Z")
							|| ("a" <= getEmailTexts && getEmailTexts <= "z"))) {
						alert("이메일주소는 숫자와 알파벳만 사용해주십시오")
						form1.emailFront.focus();
						return false;
					};
				};
			};
			
			// '뒷자리' 선택여부 검사
			var emailBack = form1.emailBack.value;
			if (emailBack == "메일주소선택") {
				alert("이메일주소를 선택해주십시오")
				form1.emailBack.focus();
				return false;
			}
		};
		
		
		// 취미 체크
	    function hobbyCheck (form1) {
    		if (!(form1.hobby[0].checked == true || form1.hobby[1].checked == true
    				|| form1.hobby[2].checked == true || form1.hobby[3].checked == true
    				|| form1.hobby[4].checked == true)) {
    			alert("취미를 선택해주세요")
    			return false;
    		}
		};
		
		
		// 자기소개서 체크
	    function introCheck (form1) {
			var intro = form1.intro.value;
    		if (intro.length == 0) {
    			alert("자기소개서를 입력해주세요")
    			return false;
    		}
		};
	    
		
    	// 버튼 클릭시 호출하는 함수
	   	function check(from1) {
    		var checkList = IDCheck(form1);
    		if (checkList == false) {
				return false;
			}
    		
    		checkList = passwordCheck(form1);
    		if (checkList == false) {
				return false;
			}
    		
    		checkList = juminCheck(form1);
    		if (checkList == false) {
				return false;
			}
    		
    		checkList = sexCheck(form1);
    		if (checkList == false) {
				return false;
			}
    		
    		checkList = telCheck(form1);
    		if (checkList == false) {
				return false;
			}
    		
    		checkList = emailCheck(form1);
    		if (checkList == false) {
				return false;
			}
    		
    		checkList = hobbyCheck(form1);
    		if (checkList == false) {
				return false;
			}
    		
    		checkList = introCheck(form1);
    		if (checkList == false) {
				return false;
			}
    		
    		// 성별값 추출
    		var strSex;
    		if (form1.sex[0].checked) {
    			strSex = form1.sex[0].value;
    		} else {
    			strSex = form1.sex[1].value;
    		}
    		
    		// 취미값 추출
    		var strHobby = "";
    		for (var i = 0; i < form1.hobby.length; i++) {
    			if (form1.hobby[i].checked) {
    				strHobby += form1.hobby[i].value + " "
    			}
    		}
    		
    		// 출력
	   		alert("아이디 : " + form1.id.value + "\n"
	   				+ "비밀번호 : " + form1.password1.value + "\n"
	   				+ "주민번호 : " + form1.jumin1.value + "-" + form1.jumin2.value + "\n"
	   				+ "성별 : " + strSex + "\n"
	   				+ "전화번호 : " + form1.tel1.value + "-" + form1.tel2.value + "-" + form1.tel3.value + "\n"
	   				+ "이메일 : " + form1.emailFront.value + "@" + form1.emailBack.value + "\n"
	   				+ "취미 : " + strHobby + "\n"
	   				+ "자기소개서 : " + form1.intro.value + "\n");
    		
		}
    </script>
</head>
<body>
	<form name="form1" action="inputChk.me" method="post" onsubmit="return check(this.form)">
		<table border="1" align="center">
			<!-- 아이디 -->
			<tr>
				<th width="130px">아이디</th>
				<td width="350px">
					<input type="text" name="id" maxlength="10" required
						placeholder="최소 6 ~ 최대 10, 숫자와 알파벳만 사용" style="width:250px">
				</td>
			</tr>
			
			<!-- 비밀번호 -->
			<tr>
				<th>비밀번호</th>
				<td>
					<input name="password1" type="password" maxlength="10" placeholder="최소 6 ~ 최대 10, 숫자와 알파벳만 사용"
						style="width:250px">
				</td>
			</tr>
			
			<!-- 비밀번호 확인 -->
			<tr>
				<th>비밀번호 확인</th>
				<td>
					<input name="password2" type="password" maxlength="10" placeholder="최소 6 ~ 최대 10, 숫자와 알파벳만 사용"
						style="width:250px">
				</td>
			</tr>
			
			<!-- 주민번호 -->
			<tr>
				<th>주민번호</th>
				<td>
					<input type="text" name="jumin1" size="6" maxlength="6" style="width:100px"> - 
					<input type="text" name="jumin2" size="7" maxlength="7" style="width:100px">
				</td>
			</tr>
			
			<!-- 성별 -->
			<tr>
				<th>성별</th>
				<td>
					<input type="radio" name="sex" value="남자">남자
					<input type="radio" name="sex" value="남자">여자
				</td>
			</tr>
			
			<!-- 전화번호 -->
			<tr>
				<th>전화번호</th>
				<td>
					<input type="tel" name="tel1" maxlength="3" style="width:40px"> - 
					<input type="tel" name="tel2" maxlength="4" style="width:50px"> - 
					<input type="tel" name="tel3" maxlength="4" style="width:50px">
				</td>
			</tr>
			
			<!-- 이메일 -->
			<tr>
				<th>이메일</th>
				<td>
					<input type="text" name="emailFront" style="width:100px"> @ 
					<select name="emailBack">
						<option value="메일주소선택">메일주소선택</option>
						<option value="naver.com">naver.com</option>
						<option value="gmail.com">gmail.com</option>
						<option value="daum.net">daum.net</option>
					</select>
				</td>
			</tr>
			
			<!-- 취미 -->
			<tr>
				<th>취미</th>
				<td>
					<input type="checkbox" name="hobby" value="등산">등산
					<input type="checkbox" name="hobby" value="독서">독서
					<input type="checkbox" name="hobby" value="스키">스키
					<input type="checkbox" name="hobby" value="음악">음악
					<input type="checkbox" name="hobby" value="영화">영화
				</td>
			</tr>
			
			<!-- 자기소개 -->
			<tr>
				<th>자기소개서</th>
				<td>
					<textarea name="intro" cols="40" rows="7"></textarea>
				</td>
			</tr>
			
			<!-- 버튼 -->
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="입력">
					<input type="reset" value="다시 입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>