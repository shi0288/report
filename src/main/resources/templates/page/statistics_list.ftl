<div class="panel panel-primary statistics">
    <div class="panel-heading">数据统计（不包含当天）</div>
    <table class="table table-bordered table-condensed">
        <tbody>
        <tr class="tr_body">
            <td>总收益</td>
            <td><strong>${(data.allFee)!''}元</strong></td>
        </tr>
        <tr class="tr_body">
            <td>总CPM</td>
            <td><strong>${(data.allTimes)!''}次</strong></td>
        </tr>
        <tr class="tr_body">
            <td>平均一次CPM收益</td>
            <td>
            <#if data.allTimes==0>
                <strong>0元</strong>
            <#else>
                <strong>${((data.allFee/data.allTimes)?string('#.####'))!''}元</strong>
            </#if>
            </td>
        </tr>
        </tbody>
    </table>
</div>

