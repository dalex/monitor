<html>
<body>
Во время мониторинга ресурсов произошла ошибка.

<#list mapResourceMessages?keys as urlKey>
<h3>Ресурс: ${urlKey} </h3>
    <#list mapResourceMessages[urlKey] as resourceMessage>
    ${resourceMessage}</br>
    </#list>
</#list>

</body>
</html>