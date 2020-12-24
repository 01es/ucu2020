package helsinki.personnel;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Employment}.
 *
 * @author Developers
 *
 */
public interface EmploymentCo extends IEntityDao<Employment> {
    static final IFetchProvider<Employment> FETCH_PROVIDER = EntityUtils.fetch(Employment.class)
    		.with("contractNo", "desc", "employee", "startDate", "finishDate", "contractDocument", "salary");
}
