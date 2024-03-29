package com.myproject.MyProject1.service.implementation;

import com.myproject.MyProject1.dto.HolidayGrid;
import com.myproject.MyProject1.dto.InsertScheduler;
import com.myproject.MyProject1.dto.SchedulerGrid;
import com.myproject.MyProject1.entity.Holiday;
import com.myproject.MyProject1.entity.Scheduler;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.repository.HolidayRepository;
import com.myproject.MyProject1.repository.SchedulerRepository;
import com.myproject.MyProject1.repository.TemplateMessageRepository;
import com.myproject.MyProject1.service.abstraction.SchedulerService;
import com.myproject.MyProject1.utility.AutoIncrementHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private TemplateMessageRepository templateMessageRepository;

    @Autowired
    private Scheduler scheduler;

    @Override
    public Object save(InsertScheduler dto) {
        LocalTime sendTime = LocalTime.parse(dto.getSendTime());
        TemplateMessage templateMessage = templateMessageRepository.findByName(dto.getTemplateName());
        Scheduler schedExisting = schedulerRepository.findByName(dto.getCurrentSchedulerName());
        if(schedExisting!=null){
            schedExisting.setSchedulerName(dto.getSchedulerName());
            schedExisting.setPeriod(dto.getPeriod());
            schedExisting.setSendTime(sendTime);
            schedExisting.setIntervalMonthly(dto.getIntervalMonth());
            schedExisting.setIntervalWeek(dto.getIntervalWeek());
            schedExisting.setTemplateMessageId(templateMessage.getTemplateMessageId());
            schedulerRepository.save(schedExisting);
        }else {
            scheduler.setSchedulerId("SCH"+ AutoIncrementHelper.increment(schedulerRepository.getLastId()));
            scheduler.setPeriod(dto.getPeriod());
            scheduler.setSchedulerName(dto.getSchedulerName());
            scheduler.setSendTime(sendTime);
            scheduler.setTemplateMessageId(templateMessage.getTemplateMessageId());
            scheduler.setIntervalMonthly(dto.getIntervalMonth());
            scheduler.setIntervalWeek(dto.getIntervalWeek());
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

    @Override
    public InsertScheduler getSchedulerByName(String currentSchedulerName) {
        InsertScheduler dto = schedulerRepository.getByName(currentSchedulerName);
        InsertScheduler insertScheduler = new InsertScheduler(dto.getCurrentSchedulerName(),dto.getCurrentSchedulerName(),
                dto.getPeriod(),dto.getIntervalWeek(),dto.getIntervalMonth(),dto.getTemplateName(),dto.getSend().toString(),dto.getSend());
        return insertScheduler;
    }

    @Override
    public void delete(String schedulerName) {
        List<Scheduler> schedulerDelete =schedulerRepository.findBySchName(schedulerName);
        for(Scheduler sch : schedulerDelete){
            schedulerRepository.deleteById(sch.getSchedulerId());
        }
    }

    @Override
    public boolean checkSchedlerName(String valueSchedulerName, String valueCurrentSchedName) {
        Scheduler sch = schedulerRepository.findByName(valueCurrentSchedName);
        int countSch = schedulerRepository.countByName(valueSchedulerName);
        if(valueSchedulerName.toLowerCase().equals(valueCurrentSchedName.toLowerCase())){
            return false;
        }else if(sch!=null && countSch==1 && valueSchedulerName != valueCurrentSchedName){
            return true;
        }else if(sch==null && countSch==1){
            return true;
        }
        return false;
    }

    @Override
    public Page<HolidayGrid> getLIstHoliday(int page) {
        Pageable pageable =PageRequest.of(page-1,10, Sort.by("holidayDate"));
        LocalDate now = LocalDate.now();
        String nowYear = String.valueOf(now.getYear());
        Page<HolidayGrid> holidayGridPage = holidayRepository.getListHoliday(pageable,nowYear);
        return holidayGridPage;
    }
}
