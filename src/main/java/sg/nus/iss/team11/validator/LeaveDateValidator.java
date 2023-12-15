package sg.nus.iss.team11.validator;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.controller.service.HolidayService;
import sg.nus.iss.team11.model.LAPSUser;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;

@Component
public class LeaveDateValidator implements Validator{
	@Autowired
	HolidayService holidayservice;
	@Override
	public boolean supports(Class<?> clazz) {
		return LeaveApplication.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		// Checks if the dates are left empty
		ValidationUtils.rejectIfEmpty(errors, "fromDate","error.fromDate", "Start date is required.");
		ValidationUtils.rejectIfEmpty(errors, "toDate","error.toDate", "End date is required.");
		
		// Checks if the start date is after the end date
		LeaveApplication leaveapplication = (LeaveApplication) obj;
		if((leaveapplication.getFromDate() != null && leaveapplication.getToDate() != null) && 
				(leaveapplication.getFromDate().compareTo(leaveapplication.getToDate()) > 0)){
			errors.rejectValue("toDate","error.dates", "End date must be later than the Start Date");
		}
		
		// Checks if the User has enough leave entitlement to take
		LocalDate startDate = leaveapplication.getFromDate();
		LocalDate toDate = leaveapplication.getToDate();
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
		LAPSUser currentUser = (LAPSUser) session.getAttribute("user");
		Period leaveDuration = Period.between(startDate, toDate);
		
		// Adding 1 to the leaveDurationDays to ensure the end date is counted into the number of leaves
		int leaveDurationDays = leaveDuration.getDays() + 1;
		
		// If leave duration is less than 14 days, check for holidays and weekends
		int numberOfHolidays = 0;
		int weekends = 0;
		if (leaveDurationDays < 14) {
			// Using repository to find the number of holidays
			numberOfHolidays = holidayservice.getHolidayCount(startDate, toDate);
			weekends = leaveapplication.countWeekends(startDate, toDate);
		}
		
		// Calculating the net days of leave taken
		leaveDurationDays -= (numberOfHolidays + weekends);
		
		
		LeaveApplicationTypeEnum leavetype = leaveapplication.getType();
		int leaveEntitlement = 0;
		
		switch(leavetype) {
		case MedicalLeave:
			leaveEntitlement = currentUser.getMedicalLeaveEntitlement();
			break;
			
		case AnnualLeave:
			leaveEntitlement = currentUser.getAnnualLeaveEntitlement();
			break;
			
		case CompensationLeave:
			leaveEntitlement = currentUser.getCompensationLeaveEntitlement();
		}
		
				
		if (leaveDurationDays > leaveEntitlement) {
			errors.rejectValue("toDate","error.dates", "Not enough Leave Entitlement, your remaining leave for this type is " + String.valueOf(leaveEntitlement));
		}
		
		
	}
	
	
}
