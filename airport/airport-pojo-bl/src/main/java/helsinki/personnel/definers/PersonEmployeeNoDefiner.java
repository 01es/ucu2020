package helsinki.personnel.definers;

import org.apache.commons.lang3.StringUtils;

import helsinki.personnel.Person;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractAfterChangeEventHandler;

public class PersonEmployeeNoDefiner extends AbstractAfterChangeEventHandler<String> {

	@Override
	public void handle(final MetaProperty<String> property, final String value) {
		final Person person = property.getEntity();
		person.getProperty("aSupervisor").setRequired(!StringUtils.isEmpty(value));
	}

}
