package sg.nus.iss.team11.repository;

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
	
	@Query("SELECT la from LeaveApplication la WHERE la.status = 'APPROVED' AND la.type = 'AnnualLeave'")
	List<LeaveApplication> findLeaveApplicationsApprovedByType(@Param("type") String type);
}
