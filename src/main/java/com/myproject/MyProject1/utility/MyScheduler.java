package com.myproject.MyProject1.utility;

import com.myproject.MyProject1.entity.Recipient;
import com.myproject.MyProject1.entity.Report;
import com.myproject.MyProject1.entity.Scheduler;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.repository.RecipientRepository;
import com.myproject.MyProject1.repository.ReportRepository;
import com.myproject.MyProject1.repository.SchedulerRepository;
import com.myproject.MyProject1.repository.TemplateMessageRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@Slf4j
public class MyScheduler {

    @Autowired
    private TemplateMessageRepository templateMessageRepository;

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private Report report;

    @Value("${spring.mail.username}") private String sender;



        // Find your Account Sid and Token at twilio.com/console
        public static final String ACCOUNT_SID = "AC88d93110e70d8c91e6a775a9fb93201c";
        public static final String AUTH_TOKEN = "8cc1fd1e55bd35cf9be5fa71b03785e7";


//    @Scheduled(cron = "10 * 08-17 * * SUN-FRI")
    @Scheduled(fixedDelay = 60000)
    public  void scheduler(){
        List<Scheduler> schedulers = schedulerRepository.findAll();
        LocalTime now = LocalTime.now();
        LocalDate dateNow = LocalDate.now();
        String dayOfMonth = String.valueOf(dateNow.getDayOfMonth());
        for(Scheduler sch : schedulers){
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            if(sch.getPeriod().toLowerCase().equals("daily") && now.getMinute()==sch.getSendTime().getMinute()&& now.getHour()==sch.getSendTime().getHour()){
                List<Recipient> recipients = recipientRepository.findByTemplateId(sch.getTemplateMessageId());
                TemplateMessage templateMessage = templateMessageRepository.findById(sch.getTemplateMessageId()).get();
                simpleMailMessage.setFrom(sender);
                simpleMailMessage.setText(templateMessage.getBodyMessage());
                for(Recipient rcp : recipients){
                    simpleMailMessage.setTo(rcp.getEmail());
                    simpleMailMessage.setSubject(templateMessage.getTemplateName()+" for "+rcp.getName()+" daily");
                    javaMailSender.send(simpleMailMessage);
                    log.info("success send email daily");
                    // send wa
                    Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
                    Message messageWa = Message.creator(
                            new com.twilio.type.PhoneNumber("+6287718281498"),
                            new com.twilio.type.PhoneNumber("+13613016066"),
                            "Hai "+rcp.getName()+" Ini adalah pesan Daily sebagai pengingat "+templateMessage.getBodyMessage()+" Terima kasih.."

                    ).create();

                    //save report
                    report.setReportId("REP"+AutoIncrementHelper.increment(reportRepository.getLastId()));
                    report.setDateSent(dateNow);
                    report.setTimeSent(now);
                    report.setEmail(rcp.getEmail());
                    report.setSentBy(sender);
                    report.setTemplateMessageId(templateMessage.getTemplateMessageId());
                    report.setBodyMessage(templateMessage.getBodyMessage());
                    reportRepository.save(report);
                }

            }if(sch.getPeriod().toLowerCase().equals("weekly")&& now.getHour()==sch.getSendTime().getHour() && sch.getIntervalWeek().toUpperCase().equals(dateNow.getDayOfWeek().toString())&&sch.getSendTime().getMinute()==now.getMinute()){
                List<Recipient> recipients = recipientRepository.findByTemplateId(sch.getTemplateMessageId());
                TemplateMessage templateMessage = templateMessageRepository.findById(sch.getTemplateMessageId()).get();
                simpleMailMessage.setFrom(sender);
                simpleMailMessage.setText(templateMessage.getBodyMessage());
                for(Recipient rcp : recipients){
                    simpleMailMessage.setTo(rcp.getEmail());
                    simpleMailMessage.setSubject(templateMessage.getTemplateName()+" for "+rcp.getName()+" weekly");
                    javaMailSender.send(simpleMailMessage);
                    log.info("success send email weekly");
                    report.setReportId("REP"+AutoIncrementHelper.increment(reportRepository.getLastId()));
                    report.setDateSent(dateNow);
                    report.setTimeSent(now);
                    report.setEmail(rcp.getEmail());
                    report.setSentBy(sender);
                    report.setTemplateMessageId(templateMessage.getTemplateMessageId());
                    report.setBodyMessage(templateMessage.getBodyMessage());
                    reportRepository.save(report);
                }

            } if(sch.getPeriod().toLowerCase().equals("monthly")&&sch.getIntervalMonthly().equals(dayOfMonth) && now.getHour()==sch.getSendTime().getHour() && sch.getSendTime().getMinute()==now.getMinute()){
                List<Recipient> recipients = recipientRepository.findByTemplateId(sch.getTemplateMessageId());
                TemplateMessage templateMessage = templateMessageRepository.findById(sch.getTemplateMessageId()).get();
                simpleMailMessage.setFrom(sender);
                simpleMailMessage.setText(templateMessage.getBodyMessage());
                for(Recipient rcp : recipients){
                    simpleMailMessage.setTo(rcp.getEmail());
                    simpleMailMessage.setSubject(templateMessage.getTemplateName()+" for "+rcp.getName()+" monthly");
                    javaMailSender.send(simpleMailMessage);
                    log.info("success send email monthly");
                    report.setReportId("REP"+AutoIncrementHelper.increment(reportRepository.getLastId()));
                    report.setDateSent(dateNow);
                    report.setTimeSent(now);
                    report.setEmail(rcp.getEmail());
                    report.setSentBy(sender);
                    report.setTemplateMessageId(templateMessage.getTemplateMessageId());
                    report.setBodyMessage(templateMessage.getBodyMessage());
                    reportRepository.save(report);
                }

            }
        }
    }
}
