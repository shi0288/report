package org.advert.report.controller;


import org.advert.report.bean.DayReport;
import org.advert.report.service.DataService;
import org.advert.report.util.DateStyle;
import org.advert.report.util.DateUtil;
import org.advert.report.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.rmi.CORBA.Util;
import java.util.Date;
import java.util.List;


/**
 * Created by shiqm on 2018-06-13.
 */

@Controller
@RequestMapping("page")
public class PageController {

    @Autowired
    private DataService dataService;


    @RequestMapping("advert")
    public void advert(ModelMap modelMap) {
        DayReport dayReport = dataService.getReport();
        if (dayReport != null) {
            modelMap.put("report", dayReport);
            modelMap.put("list", dataService.getMachines(dayReport.getTime(), "Machine" + DateUtil.DateToString(new Date(), DateStyle.YYYYMMDD)));
        }
    }


    @RequestMapping("report")
    public void report(
            String time,
            Pager pager,
            ModelMap modelMap
    ) {
        dataService.getReport(time, pager);
        modelMap.put("pager", pager);
        modelMap.put("time", time);
    }


    @RequestMapping("machine")
    public void machine(
            String time,
            String show,
            ModelMap modelMap
    ) {
        if (StringUtils.isEmpty(time)) {
            time = DateUtil.DateToString(DateUtil.addDay(new Date(), -1), DateStyle.YYYY_MM_DD);
        }
        modelMap.put("list", dataService.getMachines(DateUtil.StringToDate(time, DateStyle.YYYY_MM_DD).getTime()));
        modelMap.put("time", time);
        modelMap.put("show", show);
    }

    @RequestMapping("statistics")
    public void statistics(
            ModelMap modelMap
    ) {
        modelMap.put("data", dataService.getStatistics());
    }


}
