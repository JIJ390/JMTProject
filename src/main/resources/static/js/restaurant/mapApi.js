const restaurantAddress = document.querySelector(".getRestaurantAddress").value;

var mapContainer1 = document.getElementById('map1'), // 지도를 표시할 div 
  mapOption1 = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3 // 지도의 확대 레벨
  };

  // 지도를 생성합니다    
  var map = new kakao.maps.Map(mapContainer1, mapOption1);
  
  // 주소-좌표 변환 객체를 생성합니다
  var geocoder = new kakao.maps.services.Geocoder();
  
  
  var coords
  // 주소로 좌표를 검색합니다
  geocoder.addressSearch('경기 성남시 분당구 대왕판교로 477', function (result, status) {
    
    // 정상적으로 검색이 완료됐으면 
    if (status === kakao.maps.services.Status.OK) {
      
      coords = new kakao.maps.LatLng(result[0].y, result[0].x);
      
      // 결과값으로 받은 위치를 마커로 표시합니다
      var marker = new kakao.maps.Marker({
        map: map,
        position: coords
      });
      
      // 인포윈도우로 장소에 대한 설명을 표시합니다
      var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="width:150px;text-align:center;padding:6px 0;">' + document.querySelector(".getRestaurantName").value + '</div>'
      });
      infowindow.open(map, marker);
      
      // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
      map.setCenter(coords);
    }



    var staticMapContainer2 = document.getElementById('map2'), // 이미지 지도를 표시할 div  
      staticMapOption2 = {
        center: coords, // 이미지 지도의 중심좌표
        level: 3 // 이미지 지도의 확대 레벨
      };
    
    // 이미지 지도를 표시할 div와 옵션으로 이미지 지도를 생성합니다
    var staticMap = new kakao.maps.StaticMap(staticMapContainer2, staticMapOption2);
    
});    


    