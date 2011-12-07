package ru.bigbuzzy.monitor.service.mail;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * User: volodkoaa
 * Date: 14.11.11
 */
public abstract class MailCommand {

    private static final Logger logger = LoggerFactory.getLogger(MailCommand.class);

    protected static final String TEMPLATE_BODY_KEY = "template_body";
    protected static final String TEMPLATE_SUBJECT_KEY = "template_subject";

    protected static final String FROM_KEY = "from";
    protected static final String TO_KEY = "to";
    protected static final String SUBJECT_KEY = "subject";
    protected static final String BODY_KEY = "text";

    private static final String DEFAULT_FROM_KEY = "mail.default.from";

    protected Map<String, Object> params = new HashMap<String, Object>();

    public void execute(final JavaMailSender mailSender, final Configuration configuration, final Map<String, Object> defaultParams) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.addHeader("Content-Transfer-Encoding", "quoted-printable");

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(getParam(TO_KEY));

                String from = getParam(FROM_KEY);
                if (from == null) {
                    from = (String) defaultParams.get(DEFAULT_FROM_KEY);
                }
                message.setFrom(from);

                String subject = getParam(SUBJECT_KEY);
                if (subject == null) {
                    subject = FreeMarkerTemplateUtils.processTemplateIntoString(
                            configuration.getTemplate(getParam(TEMPLATE_SUBJECT_KEY)), params);
                }
                message.setSubject(subject);

                String text = getParam(BODY_KEY);
                if (text == null) {
                    text = FreeMarkerTemplateUtils.processTemplateIntoString(
                            configuration.getTemplate(getParam(TEMPLATE_BODY_KEY)), params);
                }
                message.setText(text, hasHtml(text));
                if (logger.isTraceEnabled()) {
                    logger.trace("Send mail from:{}, to:{}, subject:{}, text:{}",
                            new Object[]{getParam(FROM_KEY), getParam(TO_KEY), subject, text});
                }
            }
        };
        mailSender.send(preparator);
    }

    private String getParam(String key) {
        Object object = params.get(key);
        if (object != null) {
            return String.valueOf(object);
        }
        return null;
    }

    private boolean hasHtml(String text) {
        if (text.length() < 6) {
            return false;
        }
        String prefix = text.substring(0, 6);
        return prefix.equalsIgnoreCase("<html>");
    }
}
