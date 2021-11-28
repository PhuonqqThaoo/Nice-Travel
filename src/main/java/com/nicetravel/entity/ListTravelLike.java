package com.nicetravel.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTravelLike {
	private String img;
	private String name;
	private BigDecimal price;
	private String departurePlace;
}
