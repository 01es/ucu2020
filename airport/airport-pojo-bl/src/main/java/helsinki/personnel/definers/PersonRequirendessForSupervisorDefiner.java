package helsinki.personnel.definers;

import org.apache.commons.lang3.StringUtils;

import helsinki.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class PersonRequirendessForSupervisorDefiner extends AbstractAfterChangeEventHandler<Object> {

	@Override
	public void handle(final MetaProperty<Object> property, final Object value) {
		final Person person = property.getEntity();
		person.getProperty("aSupervisor").setRequired(!StringUtils.isEmpty(person.getEmployeeNo()) && !person.isSupervisor());
	}

}
