package com.myproject.MyProject1.service.abstraction;

import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.InsertTemplateMessage;
import com.myproject.MyProject1.dto.TemplateMessageGrid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TemplateMessageService {
    String save(InsertTemplateMessage dto);

   Page<TemplateMessageGrid> getListTemplate(int page, String search);

    InsertTemplateMessage getTemplateByName(String currentTemplateName);

    void delete(String templateName);

    List<DropdownDTO> getTemplates();

    boolean checkTemplate(String name);
}
