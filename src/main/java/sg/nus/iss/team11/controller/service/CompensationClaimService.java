package sg.nus.iss.team11.controller.service;

import java.util.List;

import sg.nus.iss.team11.model.CompensationClaim;

public interface CompensationClaimService {
	List<CompensationClaim> findAllCompensationClaims();

	CompensationClaim findCompensationClaimById(Integer id);

	CompensationClaim createCompensationClaim(CompensationClaim compensationClaim);

	CompensationClaim updateCompensationClaim(CompensationClaim compensationClaim);

	void removeCompensationClaim(CompensationClaim compensationClaim);

	List<CompensationClaim> findCompensationClaimsByUserId(Integer userId);

	List<CompensationClaim> findCompensationClaimsToProcess(Integer userId);

}
