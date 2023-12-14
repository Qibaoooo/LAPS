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
	
	@Query("SELECT la FROM leave_application la WHERE (MONTH(la.fromdate) = 12 AND YEAR(la.fromdate) = 2023) OR (MONTH(la.todate) = 12 AND YEAR(la.todate) = 2023) OR (la.fromdate < firststartdate AND lastenddate < la.todate)")
	List<LeaveApplication> findLeaveApplicationByYearMonth(@Param("year") Integer year, @Param("month") Integer month, @Param("firststartdate") LocalDate firststartdate, @Param("lastenddate") LocalDate lastenddate);
}
