package sg.nus.iss.team11.controller.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.LeaveApplication;

public interface HolidayService {
	int getHolidayCount(LocalDate startDate, LocalDate toDate);
	
	int getEntitlement(LeaveApplication application);
}
