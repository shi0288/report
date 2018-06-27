<#-- 分页（Pager对象、链接URL、参数Map、最大页码显示数） -->
<#macro pager pager baseUrl  parameterMap = {}>
    <#local pageNumber = pager.pageNumber />
    <#local pageSize = pager.pageSize />
    <#local pageCount = pager.pageCount />

    <#local parameter = "" />

    <#list parameterMap?keys as key>
        <#if (parameterMap[key])?? && parameterMap[key]?js_string != ''>
            <#local parameter = parameter + "&" + key + "=" + parameterMap[key] />
        </#if>
    </#list>

    <#if baseUrl?contains("?")>
        <#local baseUrl = baseUrl + "&" />
    <#else>
        <#local baseUrl = baseUrl + "?" />
    </#if>

    <#local firstPageUrl = baseUrl + "pageNumber=1" + parameter />
    <#local lastPageUrl = baseUrl + "pageNumber=" + pageCount + parameter />
    <#local prePageUrl = baseUrl + "pageNumber=" + (pageNumber - 1) + parameter />
    <#local nextPageUrl = baseUrl + "pageNumber=" + (pageNumber + 1) + parameter />

    <#local maxShowPageCount = 7>
    <#local segment = (maxShowPageCount - 1) / 2 />
    <#local startPageNumber = pageNumber - segment/>
    <#if (startPageNumber < 1)>
        <#local segment = segment+(-startPageNumber)+1 />
    </#if>
    <#local endPageNumber = pageNumber + segment />
    <#if (endPageNumber > pageCount)>
        <#local startPageNumber = startPageNumber-(endPageNumber-pageCount) />
        <#local endPageNumber = pageCount />
    </#if>
    <#if (startPageNumber < 1)>
        <#local startPageNumber = 1 />
    </#if>
    <div class="alert alert-info page-block">
        <p class="page-info">
             <span class="badge">总记录:${(pager.totalCount)!''}</span>
             <span class="badge">总页数:${(pageCount)!''}</span>
        </p>
    <#if (pageCount > 1)>
        <ul class="pagination pagination-sm">
        <#--首页 -->
            <#if (pageNumber > 1)>
                <li><a waitingLoad href="${firstPageUrl}">首页</a></li>
            <#else>
                <li class="disabled"><a href="javascript:void(0);">首页</a></li>
            </#if>
            <#list startPageNumber .. endPageNumber as index>
                <#if pageNumber != index>
                    <li><a waitingLoad href="${baseUrl + "pageNumber=" + index + parameter}">${index}</a></li>
                <#else>
                    <li class="active"><a href="javascript:void(0);">${index}</a></li>
                </#if>
            </#list>
        <#-- 末页 -->
            <#if (pageNumber < pageCount)>
                <li><a waitingLoad href="${lastPageUrl}">末页</a></li>
            <#else>
                <li class="disabled"><a href="javascript:void(0);">末页</a></li>
            </#if>
        </ul>
    </#if>
    </div>
</#macro>
