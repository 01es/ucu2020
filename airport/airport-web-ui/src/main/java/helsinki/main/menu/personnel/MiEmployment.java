package helsinki.main.menu.personnel;

import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.ui.menu.MiWithConfigurationSupport;
import helsinki.personnel.Employment;
/**
 * Main menu item representing an entity centre for {@link Employment}.
 *
 * @author Developers
 *
 */
@EntityType(Employment.class)
public class MiEmployment extends MiWithConfigurationSupport<Employment> {

}
