<div class="alert alert-info" role="alert">
    <div class="row">
        <div class="col-md-3 col-xs-12">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="选择日期" id="select_time" value="${(time)!''}"
                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d-1}'})" readOnly placeholder="选择日期"/>
                    <span class="input-group-btn">
                        <button class="btn btn-default" id="report_query_btn" type="button">查询</button>
                     </span>
                </div>
        </div>
    </div>
</div>

<#if  (pager.list)?? && (pager.pageCount>1)>
    <#import "/comp/boot_pager.ftl" as p>
    <#assign parameterMap = {"time":(time)!} />
    <@p.pager pager = pager baseUrl = "${base}/page/report" parameterMap = parameterMap />
</#if>


<div class="panel panel-primary">
    <div class="panel-heading">
        每日收益(不含当天)
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>日期</th>
                    <th>收益</th>
                    <th>CPM</th>
                    <th>单次收益</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#if (pager.list)??>
                    <#list pager.list as e>
                    <tr class="tr_body">
                        <td>
                        ${(e.time?number_to_datetime?string("MM-dd"))!''}
                        </td>
                        <td>${(e.allFee?string('#.####'))!''}元</td>
                        <td>${(e.allTimes)!''}次</td>
                        <td>
                            <#if e.allTimes==0>
                                0元
                            <#else>
                            ${((e.allFee/e.allTimes)?string('#.####'))!''}元
                            </#if>
                        </td>
                        <td><a class="btn btn-default btn-xs" href="/page/machine?show=1&time=${(e.time?number_to_datetime?string("yyyy-MM-dd"))!''}" role="button">明细</a></td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
</div>


<#if  (pager.list)??  && (pager.pageCount>1)>
    <#import "/comp/boot_pager.ftl" as p>
    <#assign parameterMap = {"time":(time)!} />
    <@p.pager pager = pager baseUrl = "${base}/page/report" parameterMap = parameterMap />
</#if>