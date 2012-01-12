<html>
<style type="text/css">
    .errorField {
        border: 1px solid #f00
    }
</style>
<body>
<h2>Статистика мониторинга ресурсов</h2>

<#if (resourceStatusMap?size == 0)>
Статистика отсутствует
</#if>

<#list resourceStatusMap?keys as urlKey>
<h3>Ресурс: ${urlKey} </h3>
<table border="1" cellpadding="0" cellspacing="0">
    <thead>
    <tr>
        <td>Время</td>
        <td>Код ответа</td>
        <td>Время ответа, мс</td>
        <td>Размре ответа, байт</td>
        <td>Сообщение</td>
    </tr>
    </thead>
    <#assign responseTimeOut=-1>
    <#list resourceStatusMap[urlKey] as resourceStatus>
        <tr>
            <#if resourceStatus.statusException>
                <td class="errorField">${resourceStatus.createTime?string("dd.MM.yyyy HH:mm:ss SSS")}</td>
                <td class="errorField">-</td>
                <td class="errorField">-</td>
                <td class="errorField">-</td>
                <td class="errorField">${resourceStatus.statusMessage}</td>
                <#else>
                    <td>${resourceStatus.createTime?string("dd.MM.yyyy HH:mm:ss SSS")}</td>
                    <td>${resourceStatus.responseCode}</td>
                    <td>
                    ${resourceStatus.responseTimeOut}
                        <#if responseTimeOut == -1>
                            <#elseif (resourceStatus.responseTimeOut > responseTimeOut)>
                                <span style="color: red;">&uarr;</span>
                            <#elseif (resourceStatus.responseTimeOut == responseTimeOut)>
                                <span>=</span>
                            <#else>
                                <span style="color: green;">&darr;</span>
                        </#if>
                    </td>
                    <td>${resourceStatus.responseSize}</td>
                    <td>-</td>
                    <#assign responseTimeOut=resourceStatus.responseTimeOut>
            </#if>
        </tr>
    </#list>
</table>
</#list>

</body>
</html>