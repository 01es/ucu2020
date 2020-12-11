package helsinki.personnel;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Supervisor}.
 *
 * @author Developers
 *
 */
public interface SupervisorCo extends IEntityDao<Supervisor> {
    static final IFetchProvider<Supervisor> FETCH_PROVIDER = EntityUtils.fetch(Supervisor.class).with("key", "desc");
}
