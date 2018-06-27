package org.advert.report.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by shiqm on 2018-06-13.
 */
public class DayReport {

    @Id
    private String id;

    private Integer allTimes;

    private Double allFee;

    private Integer total;

    private Integer upTimes;

    private Double upFee;

    @Indexed
    private Long time;

    @Override
    public String toString() {
        return "DayReport{" +
                "id='" + id + '\'' +
                ", allTimes=" + allTimes +
                ", allFee=" + allFee +
                ", total=" + total +
                ", time=" + time +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAllTimes() {
        return allTimes;
    }

    public void setAllTimes(Integer allTimes) {
        this.allTimes = allTimes;
    }

    public Double getAllFee() {
        return allFee;
    }

    public void setAllFee(Double allFee) {
        this.allFee = allFee;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
}
