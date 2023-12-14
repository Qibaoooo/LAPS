package sg.nus.iss.team11.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team11.model.LeaveApplication;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Integer> {
	@Query("SELECT la from LeaveApplication la WHERE la.user.id = :userId")
	List<LeaveApplication> findLeaveApplicationsByUserId(@Param("userId") Integer userId);

	@Query("SELECT la from LeaveApplication la WHERE la.user.id = :userId AND la.status IN ('UPDATED', 'APPLIED')")
	List<LeaveApplication> findLeaveApplicationsToProcess(@Param("userId") Integer userId);
	
	@Query("SELECT la FROM LeaveApplication la WHERE (MONTH(la.fromDate) = :month AND YEAR(la.fromDate) = :year) OR (MONTH(la.toDate) = :month AND YEAR(la.toDate) = :year) OR (la.fromDate < :firststartdate AND :lastenddate < la.toDate)")
	List<LeaveApplication> findLeaveApplicationByYearMonth(@Param("year") Integer year, @Param("month") Integer month, @Param("firststartdate") LocalDate firststartdate, @Param("lastenddate") LocalDate lastenddate);
}
