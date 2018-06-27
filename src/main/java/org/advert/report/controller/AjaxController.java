package org.advert.report.controller;

import org.advert.report.bean.Machine;
import org.advert.report.repository.MachineRepository;
import org.advert.report.service.DataService;
import org.advert.report.service.ReportService;
import org.advert.report.util.Cons;
import org.advert.report.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * Created by shiqm on 2018-06-13.
 */

@RestController
public class AjaxController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private DataService dataService;

    @Autowired
    private MachineRepository machineRepository;


    @RequestMapping("sms/close")
    public Result smsClose() {
        Cons.SMS_BTN = false;
        return new Result("close success");
    }

    @RequestMapping("sms/open")
    public Result smsOpen() {
        Cons.SMS_BTN = true;
        return new Result("open success");
    }


    @RequestMapping("advert/refresh")
    public Result refresh(Long time) {
        Result result = new Result();
        if (time != null) {
            long mul = new Date().getTime() - time;
            if (mul <= 60000) {
                result.setCode(9999);
                result.setMsg("别频繁更新，最少一分钟");
                return result;
            }
        }
        Cons.SMS_BTN = false;
        if (!reportService.saveReport()) {
            result.setCode(9999);
            result.setMsg("服务端网络错误，会有偶尔的情况，可以重试！");
        }
        Cons.SMS_BTN = true;
        return result;
    }





}
