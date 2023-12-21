package sg.nus.iss.team11.controller.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.repository.CompensationClaimRepository;

@Service
public class CompensationClaimServiceImpl implements CompensationClaimService {
	
	@Autowired
	CompensationClaimRepository claimRepo;

	@Override
	public List<CompensationClaim> findAllCompensationClaims() {
		return claimRepo.findAll();
	}

	@Override
	public CompensationClaim findCompensationClaimById(Integer id) {
		return claimRepo.findById(id).orElse(null);
	}

	@Override
	public CompensationClaim createCompensationClaim(CompensationClaim compensationClaim) {
		return claimRepo.saveAndFlush(compensationClaim);
	}

	@Override
	public CompensationClaim updateCompensationClaim(CompensationClaim compensationClaim) {
		return claimRepo.saveAndFlush(compensationClaim);
	}

	@Override
	public void removeCompensationClaim(CompensationClaim compensationClaim) {
		claimRepo.delete(compensationClaim);
	}

	@Override
	public List<CompensationClaim> findCompensationClaimsToProcess(Integer userId) {
		return claimRepo.findCompensationClaimsToProcess(userId);
	}

	@Override
	public List<CompensationClaim> findCompensationClaimsByUserId(Integer userId) {
		return claimRepo.findCompensationClaimsByUserId(userId);
	}
	
	@Override
	public List<CompensationClaim> filterForYear(List<CompensationClaim> claims, List<Integer> years) {
		claims.removeIf(c->{
			return !years.contains(c.getOverTimeDate().getYear());
		});
		return claims;
	}
	
	@Override
	public List<CompensationClaim> onlyBeforeToday(List<CompensationClaim> claims) {
		claims.removeIf(c->{
			return c.getOverTimeDate().isAfter(LocalDate.now());
		});
		return claims;
	}
	
}
