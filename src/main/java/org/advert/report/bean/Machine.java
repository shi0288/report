package org.advert.report.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by shiqm on 2018-06-13.
 */
public class Machine {

    @Id
    private String id;

    private String num;

    private String imei;

    private Integer cpm;

    private Integer pagerRowNum;

    private Double money;

    private Integer upTimes;

    private Double upFee;

    private Short status;


    @Indexed
    private Long time;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getCpm() {
        return cpm;
    }

    public void setCpm(Integer cpm) {
        this.cpm = cpm;
    }

    public Integer getPagerRowNum() {
        return pagerRowNum;
    }

    public void setPagerRowNum(Integer pagerRowNum) {
        this.pagerRowNum = pagerRowNum;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getUpTimes() {
        return upTimes;
    }

    public void setUpTimes(Integer upTimes) {
        this.upTimes = upTimes;
    }

    public Double getUpFee() {
        return upFee;
    }

    public void setUpFee(Double upFee) {
        this.upFee = upFee;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}
