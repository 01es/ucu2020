package helsinki.security.tokens;

import helsinki.config.Modules;
import ua.com.fielden.platform.security.ISecurityToken;

/**
 * Top level security token for all security tokens that belong to module {@link Modules#ASSETS};
 *
 * @author Generated
 */

public class AssetModuleToken implements ISecurityToken {
    public static final String TITLE = Modules.ASSETS.title;
    public static final String DESC = Modules.ASSETS.desc;
}
