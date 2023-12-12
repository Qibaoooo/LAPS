package sg.nus.iss.team11.validator;

import java.time.Period;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;
import sg.nus.iss.team11.model.LeaveApplication;
import sg.nus.iss.team11.model.LeaveApplicationTypeEnum;
import sg.nus.iss.team11.model.User;

@Component
public class LeaveDateValidator implements Validator{
	
	private HttpSession session;
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
//		User currentUser = (User) session.getAttribute("user");
//		User currentUser = leaveapplication.getUser();
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        User currentUser = (User) session.getAttribute("user");
		Period leaveDuration = Period.between(leaveapplication.getFromDate(), leaveapplication.getToDate());
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
		
		if (leaveDuration.getDays() > leaveEntitlement) {
			errors.rejectValue("toDate","error.dates", "Not enough Leave Entitlement, your remaining leave for this type is " + String.valueOf(leaveEntitlement));
		}
		
		
	}
}
