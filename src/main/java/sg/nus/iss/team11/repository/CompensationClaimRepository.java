package sg.nus.iss.team11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.nus.iss.team11.model.CompensationClaim;

public interface CompensationClaimRepository extends JpaRepository<CompensationClaim, Integer> {
	@Query("SELECT cc from CompensationClaim cc WHERE cc.user.id = :userId AND cc.status IN ('UPDATED', 'APPLIED')")
	List<CompensationClaim> findCompensationClaimsToProcess(@Param("userId") Integer userId);

	@Query("SELECT cc from CompensationClaim cc WHERE cc.user.id = :userId")
	List<CompensationClaim> findCompensationClaimsByUserId(@Param("userId") Integer userId);
}
