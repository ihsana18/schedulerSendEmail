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
        scheduler.setSchedulerId("SCH"+ AutoIncrementHelper.increment(schedulerRepository.getLastId()));
        scheduler.setPeriod(dto.getPeriod());
        LocalTime sendTime = LocalTime.parse(dto.getSendTime());
        scheduler.setSendTime(sendTime);
        TemplateMessage templateMessage = templateMessageRepository.findByName(dto.getTemplateName());
        scheduler.setTemplateMessageId(templateMessage.getTemplateMessageId());
        if (dto.getPeriod().toLowerCase().equals("daily")){
            scheduler.setIntervalWeek(null);
            scheduler.setIntervalMonthly(null);
            schedulerRepository.save(scheduler);
        }else if(dto.getPeriod().toLowerCase().equals("weekly")){
            scheduler.setIntervalWeek(dto.getIntervalWeek());
            schedulerRepository.save(scheduler);
        }else{
            scheduler.setIntervalMonthly(dto.getIntervalMonth());
            schedulerRepository.save(scheduler);
        }
        return "success insert scheduler";
    }

    @Override
    public Page<SchedulerGrid> getListScheduler(int page, String search) {
        Pageable pageable = PageRequest.of(page-1,5);
        Page<SchedulerGrid> schedulerGrids = schedulerRepository.getAll(pageable,search);
        return schedulerGrids;
    }
}
