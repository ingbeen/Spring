<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta charset="utf-8">
<title>여러개 마커 제어하기</title>
    <style>
	    .map_wrap {position:relative;width:100%;height:350px;}
	    .title {font-weight:bold;display:block;}
	    .bAddr {padding:5px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
	</style>
</head>
<body>
	<div class="map_wrap">
   	 	<div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
	</div>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=71275c7c87dc8cadfea7b2a96e742869&libraries=services"></script>
	<script>
		// 기본이미지
		var outImage = new kakao.maps.MarkerImage(
			'./resources/image/makericon_black.png', new kakao.maps.Size(
					31, 35));
		
		// 셀렉 이미지
		var overImage = new kakao.maps.MarkerImage(
				'./resources/image/makericon_red.png', new kakao.maps.Size(31,
						35));
		
		// 초기 위치값
		var firstposition = new kakao.maps.LatLng(33.450701, 126.570667);
		
		// 지도를 표시할 div
		var mapContainer = document.getElementById('map'), 
		mapOption = {
			center : firstposition, // 지도의 중심좌표
			level : 3
		};
		
		// 지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption);
		
		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();

		// 지도를 클릭했을때 클릭한 위치에 마커를 추가하도록 지도에 클릭이벤트를 등록합니다
		kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
			searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
		        if (status === kakao.maps.services.Status.OK) {
		        	addMarker(mouseEvent.latLng, result);
		        }   
		    });
		});
		
		// 기본 마커 생성
		searchDetailAddrFromCoords(firstposition, function(result, status) {
	        if (status === kakao.maps.services.Status.OK) {
	        	addMarker(firstposition, result);
	        }   
	    });
		
		// 좌표로 법정동 상세 주소 정보를 요청합니다
		function searchDetailAddrFromCoords(coords, callback) {
		    // 좌표로 법정동 상세 주소 정보를 요청합니다
		    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
		};
		
		// 마커생성
		function addMarker(coords, result) {
			// 마커를 생성합니다
			var marker = new kakao.maps.Marker({
				position : coords,
				image : outImage
			});
        	
			// 마커를 클릭한 위치에 표시합니다 
            marker.setPosition(coords);
            marker.setMap(map);
        	
            // 인포윈도우에 들어갈 내용
        	var detailAddr = !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
            detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';
            
            var content = '<div class="bAddr">' +
                            '<span class="title">법정동 주소정보</span>' + 
                            detailAddr + 
                        '</div>';
			
            // 인포윈도우에 내용 할당
            var infowindow = new kakao.maps.InfoWindow({
			    content : content,
			    zindex : 1
			});
            
         	// 삭제 이벤트를 등록한다
			kakao.maps.event.addListener(marker, 'click', function(mouseEvent) {
				marker.setMap(null);
				infowindow.close();
			});

			// 마우스 오버 이미지 변경 이벤트
			kakao.maps.event.addListener(marker, 'mouseover', function() {
				marker.setImage(overImage);
				infowindow.open(map, marker); 
			});
			
			// 마우스 아웃 이미지 변경 이벤트
			kakao.maps.event.addListener(marker, 'mouseout', function() {
				marker.setImage(outImage);
				infowindow.close();
			});
		};
	</script>
</body>
</html>
