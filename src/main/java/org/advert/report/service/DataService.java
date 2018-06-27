package org.advert.report.service;

import org.advert.report.bean.DayReport;
import org.advert.report.bean.Machine;
import org.advert.report.repository.DayReportRepository;
import org.advert.report.repository.MachineRepository;
import org.advert.report.util.DateUtil;
import org.advert.report.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shiqm on 2018-06-14.
 */

@Service
public class DataService {

    @Autowired
    private DayReportRepository dayReportRepository;

    @Autowired
    private MachineRepository machineRepository;

    public DayReport getReport() {
        return getReport(1);
    }


    public DayReport getReport(Integer page) {
        Date date = new Date();
        DayReport query = new DayReport();
        Sort.Order[] sorts = {new Sort.Order(Sort.Direction.DESC, "time")};
        List<DayReport> list = dayReportRepository.find(query, page, 1, sorts, "DayReport" + DateUtil.DateToString(date, "yyyyMMdd"));
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }


    public Pager getReport(String date, Pager pager) {
        DayReport query = new DayReport();
        if (!StringUtils.isEmpty(date)) {
            Date time = DateUtil.StringToDate(date, "yyyy-MM-dd");
            query.setTime(time.getTime());
        }
        Sort.Order[] sorts = {new Sort.Order(Sort.Direction.DESC, "time")};
        List<DayReport> list = dayReportRepository.find(query, pager.getPageNumber(), pager.getPageSize(), sorts);
        pager.setList(list);
        pager.setTotalCount(dayReportRepository.count(query).intValue());
        return pager;
    }


    public List<Machine> getMachines(Long time, String collectionName) {
        Machine query = new Machine();
        query.setTime(time);
        Sort.Order[] sorts = {new Sort.Order(Sort.Direction.ASC, "pagerRowNum")};
        List<Machine> list = null;
        if (StringUtils.isEmpty(collectionName)) {
            list = machineRepository.find(query, 1, 50, sorts);
        } else {
            list = machineRepository.find(query, 1, 50, sorts, collectionName);
        }
        return list;
    }


    public List<Machine> getMachines(Long time) {
        return getMachines(time, null);
    }


    public Map getStatistics() {
        Map<String, Object> map = new HashMap<>();
        Aggregation aggregation =
                Aggregation.newAggregation(
                        Aggregation.group()
                                .sum("allFee").as("allFee")
                                .sum("allTimes").as("allTimes")
                );

        List<Map> list = dayReportRepository.aggregate(aggregation);
        if (list != null && list.size() == 1) {
            map = list.get(0);
        }
        return map;
    }


}
