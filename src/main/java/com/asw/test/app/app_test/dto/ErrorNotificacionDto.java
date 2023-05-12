package com.asw.test.app.app_test.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorNotificacionDto implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1105435270240411361L;
	private Integer status;
	private String error;

}
