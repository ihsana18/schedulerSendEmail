package com.myproject.MyProject1.service.implementation;

import com.myproject.MyProject1.dto.InsertScheduler;
import com.myproject.MyProject1.dto.SchedulerGrid;
import com.myproject.MyProject1.entity.Scheduler;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.repository.SchedulerRepository;
import com.myproject.MyProject1.repository.TemplateMessageRepository;
import com.myproject.MyProject1.service.abstraction.SchedulerService;
import com.myproject.MyProject1.utility.AutoIncrementHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private TemplateMessageRepository templateMessageRepository;

    @Autowired
    private Scheduler scheduler;

    @Override
    public Object save(InsertScheduler dto) {
        Scheduler schedExisting = schedulerRepository.findByName(dto.getCurrentSchedulerName());
        LocalTime sendTime = LocalTime.parse(dto.getSendTime());
        TemplateMessage templateMessage = templateMessageRepository.findByName(dto.getTemplateName());
        if(schedExisting==null){
            scheduler.setSchedulerId("SCH"+ AutoIncrementHelper.increment(schedulerRepository.getLastId()));
            scheduler.setPeriod(dto.getPeriod());
            scheduler.setSchedulerName(dto.getSchedulerName());
            scheduler.setSendTime(sendTime);
            scheduler.setTemplateMessageId(templateMessage.getTemplateMessageId());
            scheduler.setIntervalMonthly(dto.getIntervalMonth());
            scheduler.setIntervalWeek(dto.getIntervalWeek());
            schedulerRepository.save(scheduler);
        }else {
            schedExisting.setSchedulerName(dto.getSchedulerName());
            schedExisting.setPeriod(dto.getPeriod());
            schedExisting.setSendTime(sendTime);
            schedExisting.setIntervalMonthly(dto.getIntervalMonth());
            schedExisting.setIntervalWeek(dto.getIntervalWeek());
            schedExisting.setTemplateMessageId(templateMessage.getTemplateMessageId());
            schedulerRepository.save(schedExisting);
        }
        return "success insert scheduler";
    }

    @Override
    public Page<SchedulerGrid> getListScheduler(int page, String search) {
        Pageable pageable = PageRequest.of(page-1,5);
        Page<SchedulerGrid> schedulerGrids = schedulerRepository.getAll(pageable,search);
        return schedulerGrids;
    }

    @Override
    public InsertScheduler getSchedulerByName(String currentSchedulerName) {
        InsertScheduler dto = schedulerRepository.getByName(currentSchedulerName);
        InsertScheduler insertScheduler = new InsertScheduler(dto.getCurrentSchedulerName(),dto.getCurrentSchedulerName(),
                dto.getPeriod(),dto.getIntervalWeek(),dto.getIntervalMonth(),dto.getTemplateName(),dto.getSend().toString(),dto.getSend());
        return insertScheduler;
    }

    @Override
    public void delete(String schedulerName) {
        Scheduler schedulerDelete =schedulerRepository.findByName(schedulerName);
        schedulerRepository.deleteById(schedulerDelete.getSchedulerId());
    }
}
