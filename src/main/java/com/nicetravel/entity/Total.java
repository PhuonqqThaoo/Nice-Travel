package com.nicetravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Total {
	String name ;
	Double phanTram;
	Integer quantity;
	Integer quantity_new;
}
