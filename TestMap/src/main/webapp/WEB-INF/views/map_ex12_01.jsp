<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>지도에 마우스 클릭시 마커 표시 및 인포 윈도우 출력</title>
    
<style>
.customoverlay {
	position:relative;
	bottom:0px; // 인포윈도우와 위치 중첩시킴
	border-radius:6px;
	border: 1px solid #ccc;
	border-bottom:2px solid #ddd;
	float:left;
}
.customoverlay {
	display:block;
	text-align:left;
	background:#FFFFFF;
	padding:10px 10px;
	font-size:14px;
	font-weight:bold;
}
</style>
</head>
<body>
<div class="map_wrap">
<div id="map" style="width:100%;height:400px;"></div>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=724eaf673dfe78c5856697c52da552f3&libraries=services"></script>
<script>
var SPRITE_MARKER_URL = './resources/images/makericon_red.png';// 스프라이트 마커 이미지 URL
var NORMAL_MARKER_URL = './resources/images/makericon_black.png';// 노말 마커 이미지 URL    

//지도에 표시된 마커 객체를 가지고 있을 배열입니다(추가 편집)
var markers = [];
var positions = [];
var infowindow = new kakao.maps.InfoWindow({zindex:1}); //클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

//지도를 클릭했을때 클릭한 위치에 마커를 추가하도록 지도에 클릭이벤트를 등록합니다(추가편집)
kakao.maps.event.addListener(map, 'click', function(e) {   
	for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }   

    // 클릭한 위치에 마커를 생성하고 지도위에 표시합니다
    addMarker(e.latLng);        
});


// 마커를 생성하고 지도 위에 표시하고, 마커에 mouseover, mouseout, click 이벤트를 등록하는 함수입니다
function addMarker(position) {

    // 기본 마커이미지, 오버 마커이미지
    var normalImage = createMarkerImage(NORMAL_MARKER_URL);
    var mouseoverImage = createMarkerImage(SPRITE_MARKER_URL);
    
    // 마커를 생성하고 이미지는 기본 마커 이미지를 사용합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: position,
        image: normalImage
    });
    
    markers.push(marker);
	positions.push(position); // marker의 위도 경도 저장
	
    // 마커에 mouseover 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'mouseover', function() {
        // 마커의 이미지를 오버 이미지로 변경합니다
    	marker.setImage(mouseoverImage);
        
        // markers배열에서 marker의 위치 구함
        var index = markers.indexOf(marker);
        searchDetailAddrFromCoords(positions[index], function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
            	// 주소구조 : https://developers.kakao.com/docs/latest/ko/local/dev-guide#coord-to-address
                var detailAddr = '';
                	detailAddr += !!result[0].road_address ? '<div>도로명주소 : ' + result[0].road_address.address_name + '</div>' : '';
                    detailAddr += '<div>지번 주소 : ' + result[0].address.address_name + '</div>';
                
                var content = '<div class="customoverlay">' +
                                '<span class="title">법정동 주소정보</span>' + 
                                detailAddr + '</div>';
                            
                // 마커를 클릭한 위치에 표시합니다 
                marker.setPosition(positions[index]); // positions[index] : 마커의 위도와 경도
                marker.setMap(map);

                // 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
                infowindow.setContent(content);
                infowindow.open(map, marker);
            }   
        });
    });

    // 마커에 mouseout 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'mouseout', function() {
        // 마커의 이미지를 기본 이미지로 변경합니다
    	marker.setImage(normalImage);

        infowindow.close();
    });

    // 마커에 click 이벤트를 등록합니다. 마커클릭시 마커 삭제하기(추가 작업)
    kakao.maps.event.addListener(marker, 'click', function() {  
    	infowindow.close();
    	marker.setMap(null); 
    	markers.splice(marker, 1);  //markers배열에서 maker 삭제하기 
    	var index = markers.indexOf(marker);
    	positions.splice(positions[index], 1);
    });
}

function createMarkerImage(imageSrc) {
	imageSize = new kakao.maps.Size(32, 34), // 마커이미지의 크기입니다
	// 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
    imageOption = {offset: new kakao.maps.Point(16, 34)}; 
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption)
        
    return markerImage;
}

function searchDetailAddrFromCoords(coords, callback_addr) {
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback_addr);
}
    
</script>
</body>
</html>