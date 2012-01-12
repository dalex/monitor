<html>
<body>
<h3>Hello!</h3>
Во время мониторинга ресурса ${resource.url.path} произошла ошибка.

Заданные параметры ресурса:
Url: ${resource.url.path}
<#if resource.url.login??>
Login: ${resource.url.login}
Password: ${resource.url.password}
</#if>

Http Response Code: ${resource.accept.responseCode}

<#if resource.accept.responseSize??>
Http Response Size: ${resource.accept.responseSize}
</#if>

<#if resource.accept.responseTimeOut??>
Http Response TimeOut: ${resource.accept.responseTimeOut}
</#if>

</body>
</html>