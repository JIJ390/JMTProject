package edu.kh.jmt.admin.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Restaurant {

	private int restaurantNo;
	private String restaurantName;
	private String restaurantAddress;
	private String restaurantTel;
	private String restaurantImg1;
	private String restaurantImg2;
	
	private int categoryNo;
	private int locationNo;
	
	private String categoryName;
	private String locationName;
	
	private List<Map<String, String>> menuList;
}
