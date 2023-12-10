package sg.nus.iss.team11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team11.model.LeaveApplication;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Integer> {
	@Query("SELECT a from LeaveApplication a WHERE a.user.id = :userId")
	  List<LeaveApplication> findLeaveApplicationsByUserId(@Param("userId") Integer userId);
}
