package com.ldroid.kwei.common.entities;

import com.ldroid.kwei.common.lib.gson.annotations.Expose;

public  class OutputDataEntity<RetData> extends OutputEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8297238643328522856L;

	
	@Expose public RetData data ;

}