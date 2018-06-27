<input type="hidden" id="cur_time" value="${(report.time)!''}"/>
<span class="badge">当前更新时间：${(report.time?number_to_datetime)!''}</span>
<div class="alert alert-info" role="alert">
    <button style="float: right;" type="button" id="refreshData" class="btn btn-warning">
        <span class="glyphicon glyphicon-refresh">
        </span>
    </button>
    <div class="row">
        <div class="col-xs-5 col-md-2">
            <button type="button" class="btn btn-default" style="width: 128px;text-align: left;">
                有效:<strong>${(report.allTimes)!'0'}次</strong>
            </button>

        </div>
        <div class="col-xs-5 col-md-2">
            <button type="button" class="btn btn-default" style="width: 128px;text-align: left;">
                机器:<strong>${(report.total)!'0'}台</strong>
            </button>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-5 col-md-2">
            <button type="button" class="btn btn-default" style="width: 128px;text-align: left;">
                收益:<strong>${(report.allFee)!'0'}元</strong>
            </button>
        </div>
        <div class="col-xs-5 col-md-2">
            <button type="button" class="btn btn-default" style="width: 128px;text-align: left;">
            <#if report.allTimes==0>
                平均:<strong>0元</strong>
            <#else>
                平均:<strong>${((report.allFee/report.allTimes)?string('#.####'))!'0'}元</strong>
            </#if>
            </button>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-5 col-md-2">
            <button type="button" class="btn btn-default" style="width: 128px;text-align: left;">
                增长收益:<strong>${(report.upFee)!'0'}元</strong>
            </button>
        </div>
        <div class="col-xs-5 col-md-2">
            <button type="button" class="btn btn-default" style="width: 128px;text-align: left;">
                增长次数:<strong>${(report.upTimes)!'0'}次</strong>
            </button>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-5 col-md-2">
            <button type="button" class="btn btn-default" style="width: 128px;text-align: left;">
            <#if report.total==0>
                平均每台:<strong>0元</strong>
            <#else>
                平均每台:<strong>${((report.allFee/report.total)?string('#.####'))!'0'}元</strong>
            </#if>
            </button>
        </div>
        <div class="col-xs-5 col-md-2">
            <button type="button" class="btn btn-default" style="width: 128px;text-align: left;">
            <#if report.total==0>
                平均每台:<strong>0次</strong>
            <#else>
                平均每台:<strong>${((report.allTimes/report.total)?string('#'))!'0'}次</strong>
            </#if>
            </button>
        </div>
    </div>
</div>
<div class="panel panel-primary">
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>NO.</th>
                    <th>IMEI</th>
                    <th>STCO</th>
                    <th>CPM</th>
                    <th>UP</th>
                </tr>
                </thead>
                <tbody>
                <#if (list)??>
                    <#list list as e>
                    <tr class="tr_body <#if (e.status)?? && e.status==1>danger</#if>">
                        <td>${(e.pagerRowNum)!'0'}</td>
                        <td style="cursor: pointer;"
                            data-clipboard-text="序号:${(e.pagerRowNum)!'0'},串码:${(e.imei)!'0'},cpm:${(e.cpm)!'0'}">
                        ${(e.imei?substring(9,e.imei?length))!''}
                        </td>
                        <td>${(e.money?string('#.####'))!''}</td>
                        <td>${(e.cpm)!'0'}</td>
                        <td>
                            ${(e.upTimes)!'0'}
                                <span class="glyphicon glyphicon-arrow-up">
                            </span>
                        </td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
</div>

