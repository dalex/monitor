package ru.bigbuzzy.monitor.service;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.bigbuzzy.monitor.service.mail.MailCommand;

import javax.annotation.Resource;
import java.util.Map;

/**
 * User: volodko
 * Date: 05.12.11
 * Time: 19:37
 */
@Service
public class MailService {
    @Autowired
    private Configuration configuration;
    @Autowired
    private JavaMailSender mailSender;
    @Resource(name = "defaultParams")
    private Map<String, Object> defaultParams;

    public void send(MailCommand mailCommand) {
        mailCommand.execute(mailSender, configuration, defaultParams);
    }
}
