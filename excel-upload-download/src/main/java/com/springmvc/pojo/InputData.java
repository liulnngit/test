package com.springmvc.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class InputData {
    private Long id;

    private String labelCode;

    private BigDecimal value;

    private String version;

    private Date inputDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode == null ? null : labelCode.trim();
    }

   

    public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

	@Override
	public String toString() {
		return "InputData [id=" + id + ", labelCode=" + labelCode + ", value=" + value + ", version=" + version
				+ ", inputDate=" + inputDate + "]";
	}
    
}