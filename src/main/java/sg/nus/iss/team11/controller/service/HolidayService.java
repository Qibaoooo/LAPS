package sg.nus.iss.team11.controller.service;

import java.time.LocalDate;

public interface HolidayService {
	int getHolidayCount(LocalDate startDate, LocalDate toDate);
}
