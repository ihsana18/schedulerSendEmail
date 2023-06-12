package com.myproject.MyProject1.service.implementation;

import com.myproject.MyProject1.dto.InsertRecipient;
import com.myproject.MyProject1.dto.RecipientGrid;
import com.myproject.MyProject1.entity.Recipient;
import com.myproject.MyProject1.entity.TemplateMessage;
import com.myproject.MyProject1.repository.RecipientRepository;
import com.myproject.MyProject1.repository.TemplateMessageRepository;
import com.myproject.MyProject1.service.abstraction.RecipientService;
import com.myproject.MyProject1.utility.AutoIncrementHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

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
        Recipient recipientExist= recipientRepository.getByName(dto.getCurrentName());
//        int check =recipientRepository.countByName(dto.getName());
        if(recipientExist!=null){
//            if(check==0 || check==1 && dto.getCurrentName().toLowerCase().equals(dto.getName().toLowerCase())){
                recipientExist.setName(dto.getName());
                recipientExist.setEmail(dto.getEmail());
                recipientExist.setTemplateMessageId(templateMessage.getTemplateMessageId());
                recipientRepository.save(recipientExist);
//            }else if(check==1&& dto.getCurrentName().toLowerCase()!=dto.getName().toLowerCase()){
//                bindingResult.addError(new FieldError("recipient","name","name already exist"));
//            }

        }else{
//            if(check==1){
//                bindingResult.addError(new FieldError("recipient","name","name already exist"));

//            }
            recipient.setRecipientId("RCP"+ AutoIncrementHelper.increment(recipientRepository.getLastId()));
            recipient.setName(dto.getName());
            recipient.setEmail(dto.getEmail());
            recipient.setTemplateMessageId(templateMessage.getTemplateMessageId());
            recipientRepository.save(recipient);

        }
        return "Success Insert Recipient";
    }

    @Override
    public Page<RecipientGrid> findAll(int page, String search) {
        if(page==0){
            List<RecipientGrid> recipients=recipientRepository.getAll(search);
            Page<RecipientGrid> pageReturn = new PageImpl<>(recipients);
            return pageReturn;
        }
        Pageable pageable = PageRequest.of(page-1,5);
        Page<RecipientGrid> pageData=recipientRepository.getAllData(pageable,search);
        return pageData;
    }

    @Override
    public InsertRecipient findByName(String currentName) {
        InsertRecipient insertRecipient = recipientRepository.findByName(currentName);
        return insertRecipient;
    }

    @Override
    public void delete(String name) {
        List<Recipient>  recipientDelete = recipientRepository.getListByName(name);
        for(Recipient rcp : recipientDelete){
            recipientRepository.delete(rcp);
        }

    }

    @Override
    public boolean checkExistingName(String email) {
        long checkName= recipientRepository.countByEmail(email);
        return (checkName > 0) ? true:false;
    }

    @Override
    public boolean checkUpsert(String valueName, String valueCurrentName) {
        Recipient rcpExist = recipientRepository.getByName(valueCurrentName);
        int checkName = recipientRepository.countByName(valueName);
        if(valueName.toLowerCase().equals(valueCurrentName.toLowerCase())&& checkName==1){
            return true;
        }else if(rcpExist!=null && checkName==1 && valueName != valueCurrentName){
            return true;
        }else if(rcpExist==null && checkName==1){
            return true;
        }
        return false;
    }


}
