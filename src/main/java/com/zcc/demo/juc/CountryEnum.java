package com.zcc.demo.juc;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CountryEnum {

	ONE(1, "韩"), TWO(2, "赵"), THREE(3, "魏"), FOUR(4, "楚"), FIVE(5, "燕"), SIX(6, "齐");

	private Integer code;
	private String country;

	public static CountryEnum forEach(Integer index){
		CountryEnum[] enums = CountryEnum.values();
		for (CountryEnum element:enums) {
			if (index == element.getCode()){
				return element;
			}
		}
		return null;
	}
}
