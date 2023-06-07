package com.myproject.MyProject1.utility;

import com.myproject.MyProject1.entity.Recipient;
import com.myproject.MyProject1.entity.Scheduler;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.repository.RecipientRepository;
import com.myproject.MyProject1.repository.SchedulerRepository;
import com.myproject.MyProject1.repository.TemplateMessageRepository;
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

    @Value("${spring.mail.username}") private String sender;

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
                }
            }
        }
    }
}
