package edu.kh.jmt.restaurant.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RestaurantDto {
	private int restaurantNo;
	private String restaurantName;
	private String restaurantAddress; 
	private String restaurantTel;
	private String restaurantImg1;
	private String restaurantImg2;
	private String restaurantDelFl;
	private int locationNo;
	private int categoryNo;
	
	// join
	private String locationName;
	private String categoryName;
	
	// resultMap으로 해당 가게 메뉴 조회
	private List<MenuDto> menuList;
	
	
	// 좋아요 백분율 지수
	private String likePercent;
}
