package org.advert.report.task;

import org.advert.report.service.ReportService;
import org.advert.report.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by shiqm on 2018-06-13.
 */

@Component
public class ReportTask {


    @Autowired
    private ReportService reportService;

    private Logger logger = LoggerFactory.getLogger(ReportTask.class);

    @Scheduled(cron = "0 0 1 * * ? ")
    public void saveYesterday() {
        logger.info("报表生成....");
        Date date = DateUtil.addDay(new Date(),-1);
        reportService.saveReport(DateUtil.DateToString(date, "yyyy-MM-dd"));
        reportService.delTempReport(DateUtil.DateToString(date, "yyyyMMdd"));
    }


    @Scheduled(cron = "0 0/10 * * * ? ")
    public void save() {
        logger.info("报表生成....");
        reportService.saveReport();
    }


}
