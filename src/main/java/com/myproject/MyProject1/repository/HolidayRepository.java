package com.myproject.MyProject1.repository;

import com.myproject.MyProject1.dto.HolidayGrid;
import com.myproject.MyProject1.entity.Holiday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday,String> {
    @Query(value = "select top 1(hol.holidayId) from Holiday hol order by hol.holidayId desc",nativeQuery = true )
    String getLastId();

    @Query("select hol from Holiday hol where hol.holidayDate like %:nowYear%")
    List<Holiday> findByPlusYear(String nowYear);

    @Query("select new com.myproject.MyProject1.dto.HolidayGrid(hol.holidayName,hol.holidayDate) " +
            "from Holiday hol " +
            "where hol.nationalHoliday = true " +
            "and hol.holidayDate LIKE %:nowYear% ")
    Page<HolidayGrid> getListHoliday(Pageable pageable,String nowYear);

    @Query("select hol from Holiday hol where hol.holidayDate =:holidayDate")
    Holiday holidahByName(String holidayDate);
}
