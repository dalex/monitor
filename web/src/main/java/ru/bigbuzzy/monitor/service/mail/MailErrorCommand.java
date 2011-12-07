package ru.bigbuzzy.monitor.service.mail;

import ru.bigbuzzy.monitor.model.config.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 12:31
 */
public class MailErrorCommand extends MailCommand {

    public enum ErrorCode {
        ResponseCode,
        ResponseSize,
        ResponseTimeOut;
    };

    private static final String TEMPLATE_BODY_NAME = "mailErrorBody.flt";
    private static final String TEMPLATE_SUBJECT_NAME = "mailErrorSubject.flt";

    public MailErrorCommand(Resource resource, ErrorCode errorCode, String errorValue) {
        params.put(TO_KEY, resource.getEmail());
        params.put(TEMPLATE_SUBJECT_KEY, TEMPLATE_SUBJECT_NAME);
        params.put(TEMPLATE_BODY_KEY, TEMPLATE_BODY_NAME);
        params.put("errorCode", errorCode);
        params.put("errorValue", errorValue);
        params.put("resource", resource);
    }
}
