package ru.bigbuzzy.monitor.task;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.bigbuzzy.monitor.model.config.Accept;
import ru.bigbuzzy.monitor.model.config.Resource;
import ru.bigbuzzy.monitor.model.config.Subscriber;
import ru.bigbuzzy.monitor.model.task.AssocResourceStatus;
import ru.bigbuzzy.monitor.model.task.Status;
import ru.bigbuzzy.monitor.service.MailService;
import ru.bigbuzzy.monitor.service.ResourceStatusService;
import ru.bigbuzzy.monitor.service.mail.MailStatusCommand;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User: volodko
 * Date: 12.01.12
 * Time: 11:59
 */
public class MailNotifyMonitor {
    private static final Logger logger = LoggerFactory.getLogger(MailNotifyMonitor.class);

    @Autowired
    private MailService mailService;
    @Autowired
    private ResourceStatusService resourceStatusService;

    private int searchMillis;

    public MailNotifyMonitor(int searchMillis) {
        this.searchMillis = searchMillis;
    }

    public void execute() {
        if (logger.isTraceEnabled()) {
            logger.trace("Method execute has started>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }

        Date activeDate = new DateTime().minusMillis(searchMillis).toDate();

        // find subscribers with errors resources by history for searchMillis
        Set<Subscriber> subscribers = new HashSet<Subscriber>();
        Collection<AssocResourceStatus> assocResourceStatuses = resourceStatusService.getAssocResourceStatuses();
        for (AssocResourceStatus assocResourceStatus : assocResourceStatuses) {
            Accept accept = assocResourceStatus.getResource().getAccept();
            for (Status status : assocResourceStatus.getStatuses()) {
                if (status.getCreateTime().after(activeDate)) {
                    if (status.isExceptionable()) {
                        subscribers.addAll(assocResourceStatus.getResource().getSubscribers());
                    } else if (status.getResponseTimeOut() > accept.getConnectionTimeout()) {
                        subscribers.addAll(assocResourceStatus.getResource().getSubscribers());
                    } else if (accept.getResponseCode() != -1 && status.getResponseCode() != accept.getResponseCode()) {
                        subscribers.addAll(assocResourceStatus.getResource().getSubscribers());
                    } else if (status.getResponseSize() <= accept.getResponseSize()) {
                        subscribers.addAll(assocResourceStatus.getResource().getSubscribers());
                    }
                } else {
                    break;
                }
                logger.trace("Method execute has started>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {}", subscribers);
            }
        }

        logger.trace("Method execute has started>>1111 {}", subscribers);
        if (subscribers.isEmpty()) {
            return;
        }

        for(Subscriber subscriber : subscribers) {
            MailStatusCommand mailStatusCommand = new MailStatusCommand(subscriber.getEmail());
            for(AssocResourceStatus assocResourceStatus : assocResourceStatuses) {
                Resource resource = assocResourceStatus.getResource();
                if(resource.getSubscribers().contains(subscriber)) {
                    Accept accept = assocResourceStatus.getResource().getAccept();
                    for (Status status : assocResourceStatus.getStatuses()) {
                        if (status.getCreateTime().after(activeDate)) {
                            if (status.isExceptionable()) {
                                mailStatusCommand.addException(resource, status, status.getExceptionShortMessage(),
                                        status.getExceptionFullMessage());
                            } else if (status.getResponseTimeOut() > accept.getConnectionTimeout()) {
                                mailStatusCommand.addErrorCode(resource, status, MailStatusCommand.ErrorCode.ResponseTimeOut,
                                        String.valueOf(status.getResponseTimeOut()));
                            } else if (accept.getResponseCode() != -1 && status.getResponseCode() != accept.getResponseCode()) {
                                mailStatusCommand.addErrorCode(resource, status, MailStatusCommand.ErrorCode.ResponseCode,
                                        String.valueOf(status.getResponseCode()));
                            } else if (status.getResponseSize() <= accept.getResponseSize()) {
                                mailStatusCommand.addErrorCode(resource, status, MailStatusCommand.ErrorCode.ResponseSize,
                                        String.valueOf(status.getResponseSize()));
                            } else {
                                mailStatusCommand.addStatus(resource, status);
                            }
                        }
                    }
                }
            }
            mailService.send(mailStatusCommand);
        }
    }
}
