<div class="alert alert-info" role="alert">
    <div class="row">
        <div class="col-md-3 col-xs-12">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="选择日期" id="select_time" value="${(time)!''}"
                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d-1}'})"  readOnly placeholder="选择日期"/>
                    <span class="input-group-btn">
                        <button class="btn btn-default" id="machine_query_btn" type="button">查询</button>
                     </span>
                </div>
        </div>
    </div>
</div>


<div class="panel panel-primary">
    <div class="panel-heading">
        <#if (show)??>
            <button type="button" onclick="history.go(-1);" class="btn btn-default btn-xs">
                <span class="glyphicon glyphicon-arrow-left">
                </span>
            </button>
        </#if>
        机器收益(${(time)!''})
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th>NO.</th>
                    <th>IMEI</th>
                    <th>STCO</th>
                    <th>CPM</th>
                </tr>
                </thead>
                <tbody>
                <#if (list)??>
                    <#list list as e>
                    <tr class="tr_body">
                        <td>${(e.pagerRowNum)!''}</td>
                        <td>
                        ${(e.imei?substring(8,e.imei?length-1))!''}
                        </td>
                        <td>${(e.money?string('#.####'))!''}</td>
                        <td>${(e.cpm)!''}</td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
</div>
