package helsinki.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import helsinki.personnel.Person;
import helsinki.personnel.Supervisor;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class PersonSupervisorValidator extends AbstractBeforeChangeEventHandler<Supervisor> {
	public static final String ERR_SELF_SUPERVISION = "No one should be able to supervise themselves.";
	public static final String ERR_SUPERVISION_OF_NON_EMPLOYEE = "Supervision of non-employees is not permitted.";
	public static final String ERR_PERSON_NOT_SUPERVISOR = "Only supervisors can be assigned.";

	@Override
	public Result handle(final MetaProperty<Supervisor> property, final Supervisor supervisor, final Set<Annotation> mutatorAnnotations) {
		final Person person = property.getEntity();
		
		if (supervisor != null && StringUtils.isEmpty(person.getEmployeeNo())) {
			return Result.failure(ERR_SUPERVISION_OF_NON_EMPLOYEE);
		}

		if (supervisor != null && person.equals(supervisor.getPerson())) {
			return Result.failure(ERR_SELF_SUPERVISION);
		}
		
		return Result.successful(supervisor);
	}

}