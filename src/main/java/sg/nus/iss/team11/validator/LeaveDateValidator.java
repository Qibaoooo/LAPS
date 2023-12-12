package sg.nus.iss.team11.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.nus.iss.team11.model.LeaveApplication;

@Component
public class LeaveDateValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
		return LeaveApplication.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "fromDate","error.fromDate", "Start date is required.");
		ValidationUtils.rejectIfEmpty(errors, "toDate","error.toDate", "End date is required.");
		
		LeaveApplication leaveapplication = (LeaveApplication) obj;
		if((leaveapplication.getFromDate() != null && leaveapplication.getToDate() != null) && 
				(leaveapplication.getFromDate().compareTo(leaveapplication.getToDate()) > 0)){
			errors.rejectValue("toDate","error.dates", "End date must be later than the Start Date");
		}
	}
}
