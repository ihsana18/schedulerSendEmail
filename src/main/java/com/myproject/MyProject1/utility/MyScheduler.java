package com.myproject.MyProject1.utility;

import com.myproject.MyProject1.dto.DataGrid;
import com.myproject.MyProject1.entity.*;
import com.myproject.MyProject1.repository.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.time.LocalDate;
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



//    @Scheduled(cron = "10 * 08-17 * * SUN-FRI")
    @Scheduled(fixedDelay = 60000)
    public  void scheduler(){
        List<Scheduler> schedulers = schedulerRepository.findAll();
        LocalTime now = LocalTime.now();
        LocalDate dateNow = LocalDate.now();
//        List<DataGrid> holidays = dataService.getHolday(String.valueOf(dateNow.getYear()),"");
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

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Autowired
    private Holiday holiday;

    @Autowired
    private HolidayRepository holidayRepository;


    @Scheduled(fixedDelay = 30000)
    public void index(){
        String urlTemplate = UriComponentsBuilder.fromHttpUrl("https://api-harilibur.vercel.app/api")
                .queryParam("year","")
                .queryParam("month","").encode().toUriString();
        HttpEntity<List<DataGrid>> listRole = restTemplate.exchange(urlTemplate, HttpMethod.GET, null, new ParameterizedTypeReference<List<DataGrid>>() {});
        List<DataGrid> dataGrids = listRole.getBody();
        List<Holiday> holidayList = holidayRepository.findAll();
        for (Holiday hol : holidayList){
            for(DataGrid dg:dataGrids){
                if(hol.getHolidayDate()!=dg.getHolidayName()){
                    holiday.setHolidayId("HOL"+AutoIncrementHelper.increment(holidayRepository.getLastId()));
                    holiday.setHolidayName(dg.getHolidayName());
                    holiday.setHolidayDate(dg.getHolidayDate());
                    holiday.setNationalHoliday(dg.isNationalHoliday());
                    holidayRepository.save(holiday);
                    log.info("success save holiday");
                }
            }
        }
    }
}
