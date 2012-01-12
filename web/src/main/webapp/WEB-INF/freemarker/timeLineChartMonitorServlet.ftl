<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Графики мониторинга ресурсов</title>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script type="text/javascript">
        google.load('visualization', '1', {packages:['annotatedtimeline']});
    </script>

    <script type="text/javascript">
        function drawVisualization() {
        <#list resourceStatusMap?keys as urlKey>
            var data = new google.visualization.DataTable();
            data.addColumn('datetime', 'Период');
            data.addColumn('number', 'Время ответа, мс');
            data.addColumn('string', 'Title');
            data.addColumn('string', 'Description');
            data.addRows(${resourceStatusMap[urlKey]?size});

            <#list resourceStatusMap[urlKey] as resourceStatus>
                data.setValue(${resourceStatus_index}, 0, new Date(${resourceStatus.createTime?long?c}));
                data.setValue(${resourceStatus_index}, 1, ${resourceStatus.responseTimeOut?c});
                <#if resourceStatus.statusException>
                    data.setValue(${resourceStatus_index}, 2, 'Status exception');
                    data.setValue(${resourceStatus_index}, 3, '${resourceStatus.statusMessage}');
                </#if>
            </#list>

            var annotatedTimeLine = new google.visualization.AnnotatedTimeLine(
                    document.getElementById('visualization_${urlKey_index}'));
            annotatedTimeLine.draw(data, {'displayAnnotations':true});
        </#list>
        }
        google.setOnLoadCallback(drawVisualization);
    </script>
</head>
<body style="font-family: Arial;border: 0 none;">
<form>
    <div>Параметры:
    <#if autorefresh??>
        <input id="autorefresh" type="checkbox" checked="true" onclick="submit()">
        <script language="javascript">
            setTimeout('document.location=document.location', ${autorefresh});
        </script>
    <#else>
        <input id="autorefresh" name="autorefresh" type="checkbox" value="15000" onclick="submit()">
    </#if>
        <label for="autorefresh">Aвтообновление</label>
    </div>

<#if (resourceStatusMap?size == 0)>
    Данные по мониторингу отсутствуют
<#else>
    <#list resourceStatusMap?keys as urlKey>
        <h3>Ресурс: ${urlKey} </h3>

        <div id="visualization_${urlKey_index}" style="width: 800px; height: 400px;"></div>
    </#list>
</#if>
</form>
</body>
</html>