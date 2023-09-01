package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.InsertRecipient;
import com.myproject.MyProject1.dto.RecipientAssignTemplate;
import com.myproject.MyProject1.dto.RecipientGrid;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

public interface RecipientService {
    Object save(InsertRecipient dto);

    Page<RecipientGrid> findAll(int page, String name);

    InsertRecipient findByName(String currentName);


    void delete(String name);


    boolean checkExistingName(String email);

    boolean checkUpsert(String valueName, String valueCurrentName);

    void assignTemplate(RecipientAssignTemplate dto);

    void detach(String name, String templateName);

    String saveTest(InsertRecipient test);
}
