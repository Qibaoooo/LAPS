package sg.nus.iss.team11.controller.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

public interface HolidayService {
	int getHolidayCount(LocalDate startDate, LocalDate toDate);
}
