package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.dto.DropdownDTO;
import com.myproject.MyProject1.dto.InsertTemplateMessage;
import com.myproject.MyProject1.dto.TemplateMessageGrid;
import com.myproject.MyProject1.entity.TemplateMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemplateMessageRepository extends JpaRepository<TemplateMessage,String> {
    @Query(value = "SELECT TOP 1(tmp.templateMessageId) FROM TemplateMessage tmp ORDER BY tmp.templateMessageId DESC",nativeQuery = true)
    String getLastId();

    @Query("SELECT count(tmp) FROM TemplateMessage tmp WHERE tmp.templateName = :templateName")
    int checkName(String templateName);

    @Query("SELECT new com.myproject.MyProject1.dto.TemplateMessageGrid(tmp.templateName,tmp.bodyMessage) " +
            "FROM TemplateMessage tmp " +
            "WHERE tmp.templateName LIKE %:search% " +
            "OR tmp.bodyMessage LIKE %:search% ")
    List<TemplateMessageGrid> findAllTmp(@Param("search") String templateName);

    @Query("SELECT new com.myproject.MyProject1.dto.TemplateMessageGrid(tmp.templateName,tmp.bodyMessage) " +
            "FROM TemplateMessage tmp " +
            "WHERE tmp.templateName LIKE %:search% " +
            "OR tmp.bodyMessage LIKE %:search% ")
    Page<TemplateMessageGrid> getALl(Pageable pageable, String search);

    @Query("SELECT tmp FROM TemplateMessage tmp WHERE tmp.templateName = :templateName")
    TemplateMessage findByName(String templateName);

    @Query("SELECT new com.myproject.MyProject1.dto.InsertTemplateMessage(tmp.templateName,tmp.templateName,tmp.bodyMessage,tmp.attachmentType) FROM TemplateMessage tmp WHERE tmp.templateName = :currentTemplateName")
    InsertTemplateMessage getTemplateByName(String currentTemplateName);

    @Query("SELECT new com.myproject.MyProject1.dto.DropdownDTO(tmp.templateName,tmp.templateName) FROM TemplateMessage tmp")
    List<DropdownDTO> dropdownTemplate();

    @Query("SELECT COUNT(tmp) FROM TemplateMessage tmp WHERE tmp.templateName = :name")
    int countByName(String name);


    @Query(value = "select new com.myproject.MyProject1.dto.DropdownDTO(tmp.templateName,tmp.templateName) " +
            "FROM TemplateMessage tmp where tmp.templateName != :templateName" )
    List<DropdownDTO> getListTemplate(String templateName);

    @Query("select tmp from TemplateMessage tmp where tmp.templateName NOT IN (:listString)")
    List<TemplateMessage> getAllTemplate(List<String> listString);


    @Query("select tmp from TemplateMessage tmp JOIN tmp.recipients rcp WHERE rcp.name = :name")
    List<TemplateMessage> getListTemplateByRecipientName(String name);
}
