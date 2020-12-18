package helsinki.personnel;

import com.google.inject.Inject;

import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
/**
 * DAO implementation for companion object {@link EmploymentCo}.
 *
 * @author Developers
 *
 */
@EntityType(Employment.class)
public class EmploymentDao extends CommonEntityDao<Employment> implements EmploymentCo {

    @Inject
    public EmploymentDao(final IFilter filter) {
        super(filter);
    }

    @Override
    protected IFetchProvider<Employment> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
