package sg.nus.iss.team11.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.nus.iss.team11.model.Role;

@Component
public class RoleValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return Role.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    System.out.println(target);
    
    ValidationUtils.rejectIfEmpty(errors, "roleId", "error.role.roleid.empty");
    ValidationUtils.rejectIfEmpty(errors, "name", "error.role.name.empty");
    ValidationUtils.rejectIfEmpty(errors, "description", "error.role.description.empty");
  }
  
}