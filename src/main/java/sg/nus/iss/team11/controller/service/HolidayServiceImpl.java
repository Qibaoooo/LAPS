package sg.nus.iss.team11.controller.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.team11.repository.HolidayRepository;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayRepository holidayrepository;

	public int getHolidayCount(LocalDate startDate, LocalDate toDate) {
		return holidayrepository.getHolidayCount(startDate, toDate);
	}
}
