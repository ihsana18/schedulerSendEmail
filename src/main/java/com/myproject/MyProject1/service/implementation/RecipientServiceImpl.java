package com.myproject.MyProject1.service.implementation;

import com.myproject.MyProject1.dto.InsertRecipient;
import com.myproject.MyProject1.dto.RecipientGrid;
import com.myproject.MyProject1.dto.TemplateMessageGrid;
import com.myproject.MyProject1.entity.Recipient;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.repository.RecipientRepository;
import com.myproject.MyProject1.repository.TemplateMessageRepository;
import com.myproject.MyProject1.service.abstraction.RecipientService;
import com.myproject.MyProject1.utility.AutoIncrementHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipientServiceImpl implements RecipientService {

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private TemplateMessageRepository templateMessageRepository;

    @Autowired
    private Recipient recipient;

    @Override
    public Object save(InsertRecipient dto) {
        TemplateMessage templateMessage = templateMessageRepository.findByName(dto.getTemplateName());
        if (templateMessage == null) {
            throw new RuntimeException("templateName doesn't exist");
        }
        recipient.setRecipientId("RCP"+ AutoIncrementHelper.increment(recipientRepository.getLastId()));
        recipient.setName(dto.getName());
        recipient.setEmail(dto.getEmail());
        recipient.setTemplateMessageId(templateMessage.getTemplateMessageId());
        recipientRepository.save(recipient);
        return "Success Insert Recipient";
    }

    @Override
    public Object findAll(int page, String name) {
        if(page==0){
            List<RecipientGrid> page0=recipientRepository.getAll(name);
            return page0;
        }
        Pageable pageable = PageRequest.of(page-1,5);
        Page<RecipientGrid> pageData=recipientRepository.getAllData(pageable,name);
        return pageData;
    }
}
