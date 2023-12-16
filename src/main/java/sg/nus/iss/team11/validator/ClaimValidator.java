package sg.nus.iss.team11.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.team11.model.CompensationClaim;

@Component
public class ClaimValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return CompensationClaim.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		target.getClass();
	}

}
