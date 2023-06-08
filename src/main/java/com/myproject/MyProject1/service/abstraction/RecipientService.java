package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.InsertRecipient;
import com.myproject.MyProject1.dto.RecipientGrid;
import org.springframework.data.domain.Page;

public interface RecipientService {
    Object save(InsertRecipient dto);

    Page<RecipientGrid> findAll(int page, String name);

    InsertRecipient findByName(String currentName);


    void delete(String name);
}
