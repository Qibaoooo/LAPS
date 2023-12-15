package sg.nus.iss.team11.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team11.model.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, LocalDate> {
	@Query("SELECT COUNT(h) FROM Holiday h WHERE :startDate < h.date AND :toDate > h.date")
	public int getHolidayCount(@Param("startDate") LocalDate startDate, @Param("toDate") LocalDate toDate);
}
