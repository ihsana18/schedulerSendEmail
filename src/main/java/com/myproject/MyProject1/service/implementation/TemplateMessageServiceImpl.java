package com.myproject.MyProject1.service.implementation;

import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.InsertTemplateMessage;
import com.myproject.MyProject1.dto.TemplateMessageGrid;
import com.myproject.MyProject1.entity.Recipient;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.repository.RecipientRepository;
import com.myproject.MyProject1.repository.TemplateMessageRepository;
import com.myproject.MyProject1.service.abstraction.TemplateMessageService;
import com.myproject.MyProject1.utility.AutoIncrementHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateMessageServiceImpl implements TemplateMessageService {

    @Autowired
    private TemplateMessageRepository templateMessageRepository;

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    TemplateMessage templateMessage;

    @Override
    public String save(InsertTemplateMessage dto) {
        TemplateMessage templateExisting = templateMessageRepository.findByName(dto.getCurrentTemplateName());
        if(templateExisting!=null){
            templateExisting.setTemplateName(dto.getTemplateName());
            templateExisting.setBodyMessage(dto.getBodyMessage());
            templateMessageRepository.save(templateExisting);
        }else{
            templateMessage.setTemplateMessageId("TMP"+AutoIncrementHelper.increment(templateMessageRepository.getLastId()));
            templateMessage.setBodyMessage(dto.getBodyMessage());
            templateMessage.setTemplateName(dto.getTemplateName());
            templateMessageRepository.save(templateMessage);

        }

        return "Success insert template message";
    }

    @Override
    public Page<TemplateMessageGrid> getListTemplate(int page, String search) {
        if(page==0){
         List<TemplateMessageGrid>  page0=templateMessageRepository.findAllTmp(search);
         Page<TemplateMessageGrid> gridPage0 = new PageImpl<>(page0);
         return gridPage0;
        }
        Pageable pageable = PageRequest.of(page-1,5);
        Page<TemplateMessageGrid> pageData = templateMessageRepository.getALl(pageable,search);
        return pageData;
    }

    @Override
    public InsertTemplateMessage getTemplateByName(String currentTemplateName) {
        InsertTemplateMessage templateExisting = templateMessageRepository.getTemplateByName(currentTemplateName);
        return templateExisting;
    }

    @Override
    public void delete(String templateName) {
        templateMessageRepository.deleteById(templateMessageRepository.findByName(templateName).getTemplateMessageId());
    }

    @Override
    public List<DropdownDTO> getTemplates() {
        List<DropdownDTO> dtoList = templateMessageRepository.dropdownTemplate();
        return dtoList;
    }

    @Override
    public boolean checkTemplate(String valueTemplateName,String valueCurrentTemplateName) {
        TemplateMessage tmpExisting = templateMessageRepository.findByName(valueCurrentTemplateName);
        int totalTemplate = templateMessageRepository.countByName(valueTemplateName);
        if(valueTemplateName.toLowerCase().equals(valueCurrentTemplateName.toLowerCase())){
            return false;
        }else if(tmpExisting!=null && totalTemplate==1 && valueTemplateName != valueCurrentTemplateName){
            return true;
        }else if(tmpExisting==null && totalTemplate==1){
            return true;
        }
        return false;
    }

    @Override
    public List<TemplateMessage> dropdownTemplate(String name) {
        Recipient rcp = recipientRepository.getByName(name);
        if (rcp.getTemplateMessages().isEmpty()) {
            List<TemplateMessage> templateMessages = templateMessageRepository.findAll();
            return templateMessages;
        }else {
            List<String> listString = new ArrayList<>();
            for (TemplateMessage tmp : rcp.getTemplateMessages()) {
                listString.add(tmp.getTemplateName());
            }
            List<TemplateMessage> templateMessages = templateMessageRepository.getAllTemplate(listString);
            return templateMessages;
        }
    }

    @Override
    public List<TemplateMessage> listTemplateByName(String name) {
        List<TemplateMessage> templateMessages = templateMessageRepository.getListTemplateByRecipientName(name);
        return templateMessages;
    }
}
