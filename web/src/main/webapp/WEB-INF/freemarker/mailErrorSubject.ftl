<#if errorCode = 'ResponseCode'>
Ошибка ответа responseCode ${errorValue}
<#elseif errorCode = 'ResponseSize'>
Ошибка ответа responseSize ${errorValue}
<#elseif errorCode = 'ResponseTimeOut'>
Ошибка ответа responseTimeOut ${errorValue}
</#if>