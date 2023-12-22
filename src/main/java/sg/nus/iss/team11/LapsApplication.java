package sg.nus.iss.team11;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import sg.nus.iss.team11.model.ApplicationStatusEnum;
import sg.nus.iss.team11.model.CompensationClaim;
import sg.nus.iss.team11.model.CompensationClaimTimeEnum;
import sg.nus.iss.team11.model.EmployeeTypeEnum;
import sg.nus.iss.team11.model.Holiday;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.Role;
import sg.nus.iss.team11.repository.CompensationClaimRepository;
import sg.nus.iss.team11.repository.HolidayRepository;
import sg.nus.iss.team11.repository.LeaveApplicationRepository;
import sg.nus.iss.team11.repository.RoleRepository;
import sg.nus.iss.team11.repository.UserRepository;

@SpringBootApplication
public class LapsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LapsApplication.class, args);
	}

	@Autowired
	PasswordEncoder encoder;

	@Bean
	CommandLineRunner loadData(UserRepository userRepo, LeaveApplicationRepository leaveRepo, RoleRepository roleRepo,
			CompensationClaimRepository claimRepo, HolidayRepository holidayRepo) {
		return (args) -> {
			// clean start
			claimRepo.deleteAll();
			leaveRepo.deleteAll();
			userRepo.deleteAll();
			roleRepo.deleteAll();

			Role adminRole = roleRepo.save(new Role("admin", "Admin", "System administrator"));
			Role staffRole = roleRepo.save(new Role("staff", "Staff", "Staff members"));
			Role managerRole = roleRepo.save(new Role("manager", "Manager", "Manager"));

			LAPSUser adminUser = userRepo.save(new LAPSUser("adminUser", encoder.encode("password"), adminRole));
			LAPSUser esther = userRepo.save(new LAPSUser("esther", encoder.encode("password"), managerRole));

			// INIT LAPSUser
			final LAPSUser tin = userRepo.save(new LAPSUser("tin", encoder.encode("password"), staffRole));
			final LAPSUser cherwah = userRepo.save(new LAPSUser("cherwah", encoder.encode("password"), staffRole));
			final LAPSUser yuenkwan = userRepo.save(new LAPSUser("yuenkwan", encoder.encode("password"), staffRole));

			Arrays.asList(tin, cherwah, yuenkwan, esther).forEach(user -> {
				user.setAnnualLeaveEntitlement(14);
				user.setCompensationLeaveEntitlement(10);
				user.setMedicalLeaveEntitlement(60);
			});
			tin.setType(EmployeeTypeEnum.Professional);
			cherwah.setType(EmployeeTypeEnum.Professional);
			yuenkwan.setType(EmployeeTypeEnum.Professional);
			esther.setType(EmployeeTypeEnum.Administrative);
			userRepo.flush();

			// Adding Holidays
			holidayRepo.save(new Holiday(LocalDate.of(2022, 1, 1), "New Year's Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 2, 1), "Chinese New Year"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 2, 2), "Chinese New Year"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 4, 15), "Good Friday"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 5, 1), "Labour Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 5, 3), "Hari Raya Puasa"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 5, 15), "Vesak Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 7, 10), "Hari Raya Haji"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 8, 9), "National Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 10, 24), "Deepavali"));
			holidayRepo.save(new Holiday(LocalDate.of(2022, 12, 25), "Christmas Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 1, 1), "New Year's Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 1, 22), "Chinese New Year"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 1, 23), "Chinese New Year"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 4, 7), "Good Friday"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 4, 22), "Hari Raya Puasa"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 5, 1), "Labour Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 6, 2), "Vesak Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 6, 29), "Hari Raya Haji"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 8, 9), "National Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 9, 1), "Polling Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 11, 12), "Deepavali"));
			holidayRepo.save(new Holiday(LocalDate.of(2023, 12, 25), "Christmas Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 1, 1), "New Year's Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 2, 10), "Chinese New Year"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 2, 11), "Chinese New Year"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 3, 29), "Good Friday"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 4, 10), "Hari Raya Puasa"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 5, 1), "Labour Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 5, 22), "Vesak Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 6, 17), "Hari Raya Haji"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 8, 9), "National Day"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 10, 31), "Deepavali"));
			holidayRepo.save(new Holiday(LocalDate.of(2024, 12, 25), "Christmas Day"));

			// SET MANAGERS
			userRepo.save(tin.setManager(esther));
			userRepo.save(cherwah.setManager(esther));
			userRepo.save(yuenkwan.setManager(esther));
			userRepo.save(esther.setManager(esther));

			// cherwah used 3 days this year
			leaveRepo.save(new LeaveApplication(cherwah, "Let me leave", ApplicationStatusEnum.UPDATED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3)));
			leaveRepo.save(new LeaveApplication(cherwah, null, ApplicationStatusEnum.REJECTED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3)));

			// esther used 3 days this year
			leaveRepo.save(new LeaveApplication(esther, "Leave anytime I want", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3)));

			// tin
			leaveRepo.save(new LeaveApplication(tin, "annual leave for tin", ApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave for tin", ApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.CompensationLeave, LocalDate.now(), LocalDate.now().plusDays(3)));
			leaveRepo.save(new LeaveApplication(esther, "annual leave for esther", ApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now(), LocalDate.now().plusDays(3)));
			leaveRepo.save(new LeaveApplication(cherwah, "annual leave for cherwah", ApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.MedicalLeave, LocalDate.now(), LocalDate.now().plusDays(3)));

			// tin used 3 days next year
			leaveRepo.save(new LeaveApplication(tin, "annual leave for rest", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().plusDays(30), LocalDate.now().plusDays(34)));

			leaveRepo.save(new LeaveApplication(tin, "annual leave for rest", ApplicationStatusEnum.APPLIED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().plusDays(40), LocalDate.now().plusDays(44)));
			
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 2 month back", ApplicationStatusEnum.REJECTED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(60),
					LocalDate.now().minusDays(59)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 1 month back", ApplicationStatusEnum.REJECTED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(28),
					LocalDate.now().minusDays(27)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 2 month back", ApplicationStatusEnum.UPDATED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(60),
					LocalDate.now().minusDays(59)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 1 month back", ApplicationStatusEnum.UPDATED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(28),
					LocalDate.now().minusDays(27)));

			// tin used 2 days this year
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 2 month back", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(56),
					LocalDate.now().minusDays(55)));
			leaveRepo.save(new LeaveApplication(tin, "annual leave from 1 month back", ApplicationStatusEnum.APPROVED,
					LeaveApplicationTypeEnum.AnnualLeave, LocalDate.now().minusDays(26),
					LocalDate.now().minusDays(25)));

			// CLAIMS
			claimRepo.save(new CompensationClaim(tin, "cc for tin AM", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.AM, LocalDate.now().plusDays(0)));
			claimRepo.save(new CompensationClaim(tin, "cc for tin PM", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.PM, LocalDate.now().plusDays(1)));
			claimRepo.save(new CompensationClaim(tin, "cc for tin WHOLEDAY", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.WHOLEDAY, LocalDate.now().plusDays(2)));
			claimRepo.save(new CompensationClaim(tin, "cc for tin AM", ApplicationStatusEnum.APPROVED,
					CompensationClaimTimeEnum.AM, LocalDate.now().plusDays(3)));
			IntStream.range(0, 20).forEach(i -> {
				claimRepo.save(new CompensationClaim(tin, "cc for tin No. " + i, ApplicationStatusEnum.APPLIED,
						CompensationClaimTimeEnum.PM, LocalDate.now().plusDays(4)));

			});
			claimRepo.save(new CompensationClaim(cherwah, "cc for cherwah", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.AM, LocalDate.now().plusDays(0)));
			claimRepo.save(new CompensationClaim(cherwah, "cc for cherwah", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.PM, LocalDate.now().plusDays(1)));
			claimRepo.save(new CompensationClaim(cherwah, "cc for cherwah", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.WHOLEDAY, LocalDate.now().plusDays(2)));
			IntStream.range(0, 20).forEach(i -> {
				claimRepo.save(new CompensationClaim(cherwah, "cc for cherwah No. " + i, ApplicationStatusEnum.APPLIED,
						CompensationClaimTimeEnum.PM, LocalDate.now().plusDays(4)));

			});
			claimRepo.save(new CompensationClaim(yuenkwan, "cc for yuenkwan", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.AM, LocalDate.now().plusDays(0)));
			claimRepo.save(new CompensationClaim(yuenkwan, "cc for yuenkwan", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.PM, LocalDate.now().plusDays(1)));
			claimRepo.save(new CompensationClaim(yuenkwan, "cc for yuenkwan", ApplicationStatusEnum.APPLIED,
					CompensationClaimTimeEnum.WHOLEDAY, LocalDate.now().plusDays(2)));
			IntStream.range(0, 20).forEach(i -> {
				claimRepo.save(new CompensationClaim(yuenkwan, "cc for yuenkwan No. " + i,
						ApplicationStatusEnum.APPLIED, CompensationClaimTimeEnum.PM, LocalDate.now().plusDays(4)));

			});

		};
	}

}
