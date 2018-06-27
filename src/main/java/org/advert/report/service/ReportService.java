package org.advert.report.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.advert.report.bean.AutoLogin;
import org.advert.report.bean.DayReport;
import org.advert.report.bean.Machine;
import org.advert.report.repository.AutoLoginRepository;
import org.advert.report.repository.DayReportRepository;
import org.advert.report.repository.MachineRepository;
import org.advert.report.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by shiqm on 2018-06-13.
 */


@Service
public class ReportService {


    @Autowired
    private DayReportRepository dayReportRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private AutoLoginRepository autoLoginRepository;

    @Autowired
    private DataService dataService;

    private Logger logger = LoggerFactory.getLogger(ReportService.class);


    public void saveReport(String time) {
        try {
            JSONObject obj = getQuery(time);
            int allTimes = obj.getInteger("allTimes");
            double allFee = obj.getDouble("allFee");
            int total = obj.getInteger("total");
            DayReport dayReport = new DayReport();
            dayReport.setAllTimes(allTimes);
            dayReport.setAllFee(allFee);
            dayReport.setTotal(total);
            dayReport.setTime(DateUtil.StringToDate(time, "yyyy-MM-dd").getTime());
            dayReportRepository.save(dayReport);
            JSONArray rows = obj.getJSONArray("rows");
            for (int i = 0; i < rows.size(); i++) {
                JSONObject temp = rows.getJSONObject(i);
                Machine machine = new Machine();
                machine.setNum(temp.getString("id"));
                machine.setImei(temp.getString("imei"));
                machine.setCpm(temp.getInteger("times"));
                machine.setMoney(temp.getDouble("fee"));
                machine.setPagerRowNum(temp.getInteger("_pagerRowNum"));
                machine.setTime(DateUtil.StringToDate(time, "yyyy-MM-dd").getTime());
                machineRepository.save(machine);
            }
            logger.error("保存报表success");
        } catch (Exception e) {
            logger.error("保存报表出错");
            e.printStackTrace();
        }
    }

    public String getLoginCookies() {
        AutoLogin autoLogin = new AutoLogin();
        autoLogin.setName("qiaoshao");
        autoLogin = autoLoginRepository.findOne(autoLogin);
        if (autoLogin == null) {
            String cookies = HttpClientWrapper.login();
            if (cookies == null) {
                throw new RuntimeException("获取用户信息失败");
            }
            autoLogin = new AutoLogin();
            autoLogin.setName("qiaoshao");
            autoLogin.setEncrypt(cookies);
            autoLoginRepository.save(autoLogin);
            return cookies;
        }
        return autoLogin.getEncrypt();
    }


    public String resetLoginCookies() {
        String cookies = HttpClientWrapper.login();
        if (cookies == null) {
            throw new RuntimeException("获取用户信息失败");
        }
        AutoLogin query = new AutoLogin();
        query.setName("qiaoshao");
        AutoLogin update = new AutoLogin();
        update.setEncrypt(cookies);
        autoLoginRepository.update(query, update);
        return cookies;
    }


    public JSONObject getQuery(String time) {
        String cookies = getLoginCookies();
        String result = HttpClientWrapper.query(time, cookies);
        if (result == null) {
            throw new RuntimeException("网络错误");
        }
        JSONObject obj = JSON.parseObject(result);
        if (obj.getInteger("total") > 100) {
            cookies = resetLoginCookies();
            result = HttpClientWrapper.query(time, cookies);
            obj = JSON.parseObject(result);
            if (obj.getInteger("total") > 100) {
                throw new RuntimeException("获取报表数据数量有问题：" + obj.getInteger("total"));
            }
        }
        return obj;
    }


    public boolean saveReport() {
        Date time = new Date();
        List<String> smsList = new ArrayList();
        try {
            JSONObject obj = getQuery(DateUtil.DateToString(time, "yyyy-MM-dd"));
            int allTimes = obj.getInteger("allTimes");
            double allFee = obj.getDouble("allFee");
            int total = obj.getInteger("total");
            DayReport dayReport = new DayReport();
            dayReport.setAllTimes(allTimes);
            dayReport.setAllFee(allFee);
            dayReport.setTotal(total);
            dayReport.setTime(time.getTime());
            DayReport preDayReport = dataService.getReport();
            if (preDayReport != null) {
                Double upFee = ArithUtil.sub(dayReport.getAllFee(), preDayReport.getAllFee());
                dayReport.setUpFee(upFee);
                dayReport.setUpTimes(dayReport.getAllTimes() - preDayReport.getAllTimes());
            } else {
                dayReport.setUpFee(allFee);
                dayReport.setUpTimes(allTimes);
            }
            dayReportRepository.save(dayReport, "DayReport" + DateUtil.DateToString(time, "yyyyMMdd"));
            JSONArray rows = obj.getJSONArray("rows");
            for (int i = 0; i < rows.size(); i++) {
                JSONObject temp = rows.getJSONObject(i);
                Machine machine = new Machine();
                machine.setNum(temp.getString("id"));
                machine.setImei(temp.getString("imei"));
                machine.setCpm(temp.getInteger("times"));
                machine.setMoney(temp.getDouble("fee"));
                machine.setPagerRowNum(temp.getInteger("_pagerRowNum"));
                machine.setTime(time.getTime());
                machineRepository.save(machine, "Machine" + DateUtil.DateToString(time, "yyyyMMdd"));
                Machine query = new Machine();
                query.setImei(machine.getImei());
                Sort.Order[] sorts = {new Sort.Order(Sort.Direction.DESC, "time")};
                List<Machine> list = machineRepository.find(query, 2, 1, sorts, "Machine" + DateUtil.DateToString(time, "yyyyMMdd"));
                if (list.size() == 1) {
                    query = list.get(0);
                    if (Cons.SMS_BTN) {
                        if (query.getCpm().intValue() == machine.getCpm().intValue()) {
                            smsList.add(machine.getPagerRowNum() + "__" + machine.getImei());
                            machine.setStatus(Short.valueOf("1"));
                        }
                    }
                    Double upFee = ArithUtil.sub(machine.getMoney(), query.getMoney());
                    machine.setUpFee(upFee);
                    machine.setUpTimes(machine.getCpm() - query.getCpm());
                    machineRepository.updateById(machine,"Machine" + DateUtil.DateToString(time, "yyyyMMdd"));
                }
            }
            logger.error("保存报表success");
            sendMsg(smsList);
            return true;
        } catch (Exception e) {
            logger.error("保存报表出错");
            e.printStackTrace();
            return false;
        }
    }

    private void sendMsg(List<String> smsList) {
        if (smsList.size() > 0) {
            if (smsList.size() <= 2) {
                for (int i = 0; i < smsList.size(); i++) {
                    DigestSms.SendMsg("13321176503", smsList.get(i));
                    DigestSms.SendMsg("18032880230", smsList.get(i));
                    DigestSms.SendMsg("13313041277", smsList.get(i));
                }
            } else {
                DigestSms.SendMsg("13321176503", smsList.size() + "台机器出现问题");
                DigestSms.SendMsg("18032880230", smsList.size() + "台机器出现问题");
                DigestSms.SendMsg("13313041277", smsList.size() + "台机器出现问题");
            }
        }
    }


    public void delTempReport(String time) {
        dayReportRepository.dropCollection("DayReport" + time);
        machineRepository.dropCollection("Machine" + time);
    }


}
