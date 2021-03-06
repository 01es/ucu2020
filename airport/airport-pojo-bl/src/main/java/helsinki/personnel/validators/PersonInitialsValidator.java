package helsinki.personnel.validators;

import java.lang.annotation.Annotation;
import java.util.Set;

import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.meta.impl.AbstractBeforeChangeEventHandler;
import ua.com.fielden.platform.error.Result;

public class PersonInitialsValidator extends AbstractBeforeChangeEventHandler<String> {
	public static final String ERR_NO_SPACES_PERMITTED = "Spaces are not permitted.";
	public static final String WARN_TOO_SHORT = "Consider providing a longer value.";

	@Override
	public Result handle(MetaProperty<String> property, String newValue, Set<Annotation> mutatorAnnotations) {
		if (newValue.contains(" ")) {
			return Result.failure(ERR_NO_SPACES_PERMITTED);
		} else if (newValue.length() < 2) {
			return Result.warning(WARN_TOO_SHORT);
		}
		return Result.successful(newValue);
	}

}