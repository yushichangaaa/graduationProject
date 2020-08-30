package com.ysc.util;

import java.io.Serializable;

/**
 * 
 * @author LiteY
 *
 */
public class BaseEntity implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    
    private Integer page;

   
    private Integer rows;


	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public Integer getRows() {
		return rows;
	}


	public void setRows(Integer rows) {
		this.rows = rows;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

   
}