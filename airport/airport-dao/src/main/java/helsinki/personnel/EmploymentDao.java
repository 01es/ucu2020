package helsinki.personnel;

import static ua.com.fielden.platform.utils.EntityUtils.equalsEx;

import com.google.inject.Inject;

import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.keygen.IKeyNumber;
import ua.com.fielden.platform.keygen.KeyNumber;
/**
 * DAO implementation for companion object {@link EmploymentCo}.
 *
 * @author Developers
 *
 */
@EntityType(Employment.class)
public class EmploymentDao extends CommonEntityDao<Employment> implements EmploymentCo {
	private static final String KEY_FOR_NUMBER_GENERATION = "EMPLOYMENT NO";
	public static final String MSG_DEFAULT_CONTRACT_NO = "THE NUMBER WILL BE GENERATED";
	
    @Inject
    public EmploymentDao(final IFilter filter) {
        super(filter);
    }

	@Override
	public Employment new_() {
		return super.new_().setContractNo(MSG_DEFAULT_CONTRACT_NO);
	}
    
	@Override
	@SessionRequired
	public Employment save(final Employment employment) {
		employment.isValid().ifFailure(Result::throwRuntime);
		var wasPersisted = employment.isPersisted();
		try {
			if (!wasPersisted && equalsEx(employment.getContractNo(), MSG_DEFAULT_CONTRACT_NO)) {
				final IKeyNumber coKeyNumber = co(KeyNumber.class);
				var next = coKeyNumber.nextNumber(KEY_FOR_NUMBER_GENERATION);
				final String keyPattern = "CO-%06d";
				employment.setContractNo(String.format(keyPattern, next));
			}
			
			return super.save(employment);
		} catch (final Exception ex) {
			if (!wasPersisted) {
				employment.beginInitialising();
				employment.setContractNo(MSG_DEFAULT_CONTRACT_NO);
				employment.endInitialising();
			}
			throw ex;
		}
	}
    
    @Override
    protected IFetchProvider<Employment> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}
