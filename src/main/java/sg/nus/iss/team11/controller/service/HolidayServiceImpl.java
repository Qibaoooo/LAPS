package sg.nus.iss.team11.controller.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.repository.HolidayRepository;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayRepository holidayrepository;

	public int getHolidayCount(LocalDate startDate, LocalDate toDate) {
		return holidayrepository.getHolidayCount(startDate, toDate);
	}

	public int getEntitlement(LeaveApplication application) {
		int leaveDays = application.countLeaveDays();
		if (leaveDays > 14) {
			return leaveDays;
		} else {
			int holiday = getHolidayCount(application.getFromDate(), application.getToDate());
			int weekend = application.countWeekend();
			return leaveDays - holiday - weekend;
		}
	}
}
