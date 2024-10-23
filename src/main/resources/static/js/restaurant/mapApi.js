var mapContainer1 = document.getElementById('map1'), // 지도를 표시할 div 
  mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
  };


var map1 = new kakao.maps.Map(mapContainer1, mapOption); // 지도를 생성합니다

// 마커가 표시될 위치입니다 
var markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);

// 마커를 생성합니다
var marker = new kakao.maps.Marker({
  position: markerPosition
});

// 마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map1);







var staticMapContainer2 = document.getElementById('map2'), // 이미지 지도를 표시할 div  
staticMapOption2 = {
  center: new kakao.maps.LatLng(33.450701, 126.570667), // 이미지 지도의 중심좌표
  level: 3 // 이미지 지도의 확대 레벨
};

// 이미지 지도를 표시할 div와 옵션으로 이미지 지도를 생성합니다
var staticMap = new kakao.maps.StaticMap(staticMapContainer2, staticMapOption2);


// 아래 코드는 지도 위의 마커를 제거하는 코드입니다
// marker.setMap(null);    