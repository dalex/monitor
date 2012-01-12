package ru.bigbuzzy.monitor.service.mail;

import ru.bigbuzzy.monitor.model.config.Resource;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by IntelliJ IDEA.
 * User: volodko
 * Date: 06.12.11
 * Time: 12:47
 */
public class MailExceptionCommand extends MailCommand {
    private static final String TEMPLATE_BODY_NAME = "mailExceptionBody.ftl";
    private static final String TEMPLATE_SUBJECT_NAME = "mailExceptionSubject.ftl";

    public MailExceptionCommand(Resource resource, Exception exception) {
        params.put(TO_KEY, resource.getEmail());
        params.put(TEMPLATE_SUBJECT_KEY, TEMPLATE_SUBJECT_NAME);
        params.put(TEMPLATE_BODY_KEY, TEMPLATE_BODY_NAME);
        params.put("message", exception.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        params.put("exception", sw.toString());

        params.put("resource", resource);
    }
}
