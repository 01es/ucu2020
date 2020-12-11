package helsinki.personnel;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;
/**
 * DAO implementation for companion object {@link SupervisorCo}.
 *
 * @author Developers
 *
 */
@EntityType(Supervisor.class)
public class SupervisorDao extends CommonEntityDao<Supervisor> implements SupervisorCo {

    @Inject
    public SupervisorDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<Supervisor> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
