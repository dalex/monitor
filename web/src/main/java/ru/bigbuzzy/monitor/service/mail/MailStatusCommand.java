package ru.bigbuzzy.monitor.service.mail;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import ru.bigbuzzy.monitor.model.config.Resource;
import ru.bigbuzzy.monitor.model.task.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 12:31
 */
public class MailStatusCommand extends MailCommand {

    public enum ErrorCode {
        ResponseCode,
        ResponseSize,
        ResponseTimeOut;
    }

    private static final String TEMPLATE_BODY_NAME = "mailStatusBody.ftl";
    private static final String TEMPLATE_SUBJECT_NAME = "mailStatusSubject.ftl";


    private Multimap<String, String> mapResourceMessages = LinkedListMultimap.create();

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public MailStatusCommand(String email) {
        params.put(TO_KEY, email);
        params.put(TEMPLATE_SUBJECT_KEY, TEMPLATE_SUBJECT_NAME);
        params.put(TEMPLATE_BODY_KEY, TEMPLATE_BODY_NAME);
        params.put("mapResourceMessages", mapResourceMessages.asMap());
    }

    public void addErrorCode(Resource resource, Status status, ErrorCode errorCode, String errorValue) {
        StringBuilder message;
        switch (errorCode) {
            case ResponseCode:
                message = new StringBuilder()
                        .append(" -- Exec time: ")
                        .append(dateFormat.format(status.getCreateTime()))
                        .append(" -- failed code=")
                        .append(errorValue)
                        .append(", accept=")
                        .append(resource.getAccept().getResponseCode());
                mapResourceMessages.put(resource.getUrl().getPath(), message.toString());
                break;
            case ResponseSize:
                message = new StringBuilder()
                        .append(" -- Exec time ")
                        .append(dateFormat.format(status.getCreateTime()))
                        .append(" -- failed size=")
                        .append(errorValue)
                        .append(", accept=")
                        .append(resource.getAccept().getResponseSize());
                mapResourceMessages.put(resource.getUrl().getPath(), message.toString());
                break;
            case ResponseTimeOut:
                message = new StringBuilder()
                        .append(" -- Exec time ")
                        .append(dateFormat.format(status.getCreateTime()))
                        .append(" -- failed time=")
                        .append(errorValue)
                        .append(", accept=")
                        .append(resource.getAccept().getConnectionTimeout());
                mapResourceMessages.put(resource.getUrl().getPath(), message.toString());
                break;
        }
    }

    public void addException(Resource resource, Status status, String shortDescription, String fullDescription) {
        StringBuilder message = new StringBuilder()
                .append(" -- Exec time ")
                .append(dateFormat.format(status.getCreateTime()))
                .append(" -- exception=")
                .append(shortDescription);
        mapResourceMessages.put(resource.getUrl().getPath(), message.toString());
        attachments.put(resource.getUrl().getPath(), fullDescription);
    }

    public void addStatus(Resource resource, Status status) {
        StringBuilder message = new StringBuilder()
                .append(" -- Exec time ")
                .append(dateFormat.format(status.getCreateTime()))
                .append(" -- code=")
                .append(status.getResponseCode())
                .append(" -- size=")
                .append(status.getResponseSize())
                .append(" -- time=")
                .append(status.getResponseTimeOut());
        mapResourceMessages.put(resource.getUrl().getPath(), message.toString());
    }
}
