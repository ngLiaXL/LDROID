package com.ldroid.kwei.common.entities;

import java.util.ArrayList;

import com.ldroid.kwei.common.lib.gson.annotations.Expose;

public  class OutputListEntity<RetData> extends OutputEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8297238643328522856L;

	
	@Expose public ArrayList<RetData> data ;
	
	

}