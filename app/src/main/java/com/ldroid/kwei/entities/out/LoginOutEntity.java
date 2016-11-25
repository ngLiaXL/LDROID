package com.ldroid.kwei.entities.out;

import com.ldroid.kwei.common.entities.BaseEntity;
import com.ldroid.kwei.common.lib.gson.annotations.Expose;

public class LoginOutEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 415265707009583303L;
	
	
	
	
	@Expose public Long uid  ;
	@Expose public String usex  ;
	@Expose public String uname  ;
	@Expose public String utype  ;
	@Expose public String utype2  ;
	@Expose public String utel  ;
	@Expose public String upwd  ;
	@Expose public String uregedittype  ;
	@Expose public String openid  ;
	@Expose public String accesstoken  ;
	@Expose public String sfzid  ;
	@Expose public String hurl  ;
	@Expose public String celebrity  ;
	@Expose public String bkcardbind  ;
	@Expose public Double contribution  ;
	@Expose public Double point  ;
	@Expose public Double cash  ;
	@Expose public String remark  ;
	@Expose public String status  ;
	@Expose public String updateby  ;
	@Expose public String updatedate  ;
	@Expose public String createby  ;
	@Expose public String createdate  ;
	@Expose public String tname  ;
	@Expose public String sfzname  ;
	@Expose public String newbietask  ; // 新手任务

}
