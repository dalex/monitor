package ru.bigbuzzy.monitor.service.mail;

import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    protected Map<String, String> attachments = new LinkedHashMap<String, String>();

    public void execute(final JavaMailSender mailSender, final Configuration configuration, final Map<String, Object> defaultParams) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.addHeader("Content-Transfer-Encoding", "quoted-printable");

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                
                String to = getParam(TO_KEY);
                message.setTo(to);

                String from = getParam(FROM_KEY);
                if (!StringUtils.hasText(from)) {
                    from = (String) defaultParams.get(DEFAULT_FROM_KEY);
                }
                message.setFrom(from);

                String subject = getParam(SUBJECT_KEY);
                if (!StringUtils.hasText(subject)) {
                    subject = FreeMarkerTemplateUtils.processTemplateIntoString(
                            configuration.getTemplate(getParam(TEMPLATE_SUBJECT_KEY)), params);
                }
                message.setSubject(subject);

                String text = getParam(BODY_KEY);
                if (!StringUtils.hasText(text)) {
                    text = FreeMarkerTemplateUtils.processTemplateIntoString(
                            configuration.getTemplate(getParam(TEMPLATE_BODY_KEY)), params);
                }
                message.setText(text, hasHtml(text));

                Assert.hasText(to);
                Assert.hasText(from);
                Assert.hasText(subject);
                Assert.hasText(text);

                if (logger.isTraceEnabled()) {
                    logger.trace("Send mail from:{}, to:{}, subject:{}, text:{}",
                            new Object[]{from, to, subject, text});
                }

                if (!CollectionUtils.isEmpty(attachments)) {
                    for (Map.Entry<String, String> attachment : attachments.entrySet()) {
                        message.addAttachment(attachment.getKey(), new StringInputStreamSource(attachment.getValue()));
                    }
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

    protected class StringInputStreamSource implements InputStreamSource {
        private String string;

        public StringInputStreamSource(String string) {
            this.string = string;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(string.getBytes());
        }

    }
}
