package com.repository;

import com.entity.TimeKeeping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITimekeepingRepository extends JpaRepository<TimeKeeping, Long> {
    @Override
    Page<TimeKeeping> findAll(Pageable pageable);

    //Tìm tất cả yêu cầu chấm công từ nhân viên theo quản lí// Find all attendant report by manager id
    @Query("select t from TimeKeeping t join Staff s on s.staffId = t.staff.staffId where s.manager.staffId = ?1")
    Page<TimeKeeping> findAllRequestForManager(Long managerId, Pageable page);

    Page<TimeKeeping> findAllByStaffStaffId(Long staffId, Pageable page);

    //Tìm tất cả yêu cầu chấm công từ nhân viên theo quản lí vả thời gian// Find all attendant report by manager id and time
    @Query("select t from TimeKeeping t join Staff s on s.staffId = t.staff.staffId where s.manager.staffId = ?1 and t.timeIn >= ?2 and t.timeIn <= ?3")
    Page<TimeKeeping> findAllRequestByDate(Long staffId, Long begin, Long last, Pageable page);

    // ham nay tao ra de xoa nhieu request nhung chua duoc su dung
    @Modifying
    @Query("delete from TimeKeeping t where t.id in ?1")
    void deleteByIdIn(List<Long> ids);

    void deleteAllByStaffStaffId(Long staff_id);
}
