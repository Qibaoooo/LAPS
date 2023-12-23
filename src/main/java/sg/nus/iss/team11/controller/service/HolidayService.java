package sg.nus.iss.team11.controller.service;

import java.time.LocalDate;
import java.util.List;

import sg.nus.iss.team11.model.Holiday;
import sg.nus.iss.team11.model.LeaveApplication;

public interface HolidayService {
	int getHolidayCount(LocalDate startDate, LocalDate toDate);
	
	int getEntitlement(LeaveApplication application);
	
	List<Holiday> getAllHolidays();
	
	void removeHoliday(LocalDate day);

	Holiday createHoliday(LocalDate day, String description);

}
