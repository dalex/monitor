<html>
<style type="text/css">
    .errorField {
        border: 1px solid #f00
    }
</style>
<body>
<h2>Статистика мониторинга ресурсов</h2>

<#if (assocResourceStatuses?size == 0)>
Статистика отсутствует
</#if>

<#list assocResourceStatuses as assocResourceStatus>
<h3>Ресурс: ${assocResourceStatus.resource.url.path} </h3>
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
    <#list assocResourceStatus.statuses as status>
        <tr>
            <#if status.exceptionable>
                <td class="errorField">${status.createTime?string("dd.MM.yyyy HH:mm:ss SSS")}</td>
                <td class="errorField">-</td>
                <td class="errorField">-</td>
                <td class="errorField">-</td>
                <td class="errorField">${status.exceptionShortMessage}</td>
                <#else>
                    <td>${status.createTime?string("dd.MM.yyyy HH:mm:ss SSS")}</td>
                    <td>${status.responseCode}</td>
                    <td>
                    ${status.responseTimeOut}
                        <#if responseTimeOut == -1>
                            <#elseif (status.responseTimeOut > responseTimeOut)>
                                <span style="color: red;">&uarr;</span>
                            <#elseif (status.responseTimeOut == responseTimeOut)>
                                <span>=</span>
                            <#else>
                                <span style="color: green;">&darr;</span>
                        </#if>
                    </td>
                    <td>${status.responseSize}</td>
                    <td>-</td>
                    <#assign responseTimeOut=status.responseTimeOut>
            </#if>
        </tr>
    </#list>
</table>
</#list>

</body>
</html>