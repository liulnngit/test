package com.springmvc.pojo;

public class LabelInfo {
    private Integer id;

    private String labelCode;

    private String labelName;

    private String tableCode;

    private String rowVal;

    private String columnVal;

    private String version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode == null ? null : labelCode.trim();
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode == null ? null : tableCode.trim();
    }

    public String getRowVal() {
        return rowVal;
    }

    public void setRowVal(String rowVal) {
        this.rowVal = rowVal == null ? null : rowVal.trim();
    }

    public String getColumnVal() {
        return columnVal;
    }

    public void setColumnVal(String columnVal) {
        this.columnVal = columnVal == null ? null : columnVal.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }
}