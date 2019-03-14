package com.springmvc.pojo;

public class Cell {

	//表格名称
	private String name;
	
	//表格行
	private String row;
	
	//表格列
	private String column;
	
	public Cell(){
		
	}
	
	public Cell(String name, String row, String column) {
		super();
		this.name = name;
		this.row = row;
		this.column = column;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}
	
	
}
