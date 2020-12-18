package helsinki.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.cond;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.expr;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAggregates;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAll;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllInclCalc;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAllInclCalcAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchIdOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchKeyAndDescOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchKeyAndDescOnlyAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchOnly;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetchOnlyAndInstrument;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.orderBy;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;
import static ua.com.fielden.platform.utils.EntityUtils.fetch;

import java.util.List;
import java.util.stream.Stream;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import helsinki.personnel.Employment;
import helsinki.personnel.Person;
import helsinki.personnel.PersonCo;
import helsinki.personnel.validators.PersonInitialsValidator;
import helsinki.test_config.AbstractDaoTestCase;
import ua.com.fielden.platform.dao.QueryExecutionModel;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.query.fluent.fetch;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.entity.query.model.OrderingModel;
import ua.com.fielden.platform.test.ioc.UniversalConstantsForTesting;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.IUniversalConstants;

/**
 * A test case for the employment concept.
 * 
 * @author Generated
 *
 */
public class EmploymentTest extends AbstractDaoTestCase {
	final DateTime now = dateTime("2019-10-01 11:30:00");

	
	@Before
	public void setUp() {
    	final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
		constants.setNow(now);
	}
	
    @Test
    public void interactive_testing() {
    	final EntityResultQueryModel<Employment> query = select(Employment.class)
    			.where().prop("employee.initials").eq().val("RMD")
    			.and().prop("startDate").le().now()
    			.and()
    			.begin()
    				.prop("finishDate").ge().now()
    				.or()
    				.prop("finishDate").isNull()
    			.end()    				
    			.model();
		final fetch<Employment> fetch = fetch(Employment.class).with("contractNo", "employee", "startDate", "finishDate", "salary").fetchModel();
		final OrderingModel orderBy = orderBy().prop("employee.initials").asc().prop("startDate").desc().model();
		final QueryExecutionModel<Employment, EntityResultQueryModel<Employment>> qem = from(query).with(fetch).with(orderBy).model();

    	final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
    	constants.setNow(dateTime("2019-01-01 11:30:00"));

    	// stream the data
    	try (final Stream<Employment> employments = co(Employment.class).stream(qem)) {
			employments.forEach(em -> System.out.printf("Person: %s, Contract No: %s, Start date: %s, Finish date: %s, Salary: %s%n", 
					em.getEmployee(), em.getContractNo(), em.getStartDate(), em.getFinishDate(), em.getSalary()));
		}
    	
    	// get all data
    	final List<Employment> list = co(Employment.class).getAllEntities(from(select(Employment.class).model()).with(fetch).with(orderBy).model());
    	list.forEach(em -> System.out.printf("Person: %s, Contract No: %s, Start date: %s, Finish date: %s, Salary: %s%n", 
				em.getEmployee(), em.getContractNo(), em.getStartDate(), em.getFinishDate(), em.getSalary()));
    	assertEquals(3, list.size());
    }

    
    @Test
    public void there_are_2_current_employment_contracts() {
    	final EntityResultQueryModel<Employment> query = select(Employment.class)
    			.where().prop("startDate").le().now()
    			.and()
    			.begin()
    				.prop("finishDate").ge().now()
    				.or()
    				.prop("finishDate").isNull()
    			.end()    				
    			.model();
		final fetch<Employment> fetch = fetch(Employment.class).with("contractNo", "employee", "startDate", "finishDate", "salary").fetchModel();
		final OrderingModel orderBy = orderBy().prop("employee.initials").asc().prop("startDate").desc().model();
		final QueryExecutionModel<Employment, EntityResultQueryModel<Employment>> qem = from(query).with(fetch).with(orderBy).model();

    	// the wrong way of counting
    	try (final Stream<Employment> employments = co(Employment.class).stream(qem)) {
			assertEquals(2, employments.count());
		}
    	
    	// the right way of counting
    	assertEquals(2, co(Employment.class).count(query));
    }

    @Test
    public void current_employment_contracts_exist() {
    	final EntityResultQueryModel<Employment> query = select(Employment.class)
    			.where().prop("startDate").le().now()
    			.and()
    			.begin()
    				.prop("finishDate").ge().now()
    				.or()
    				.prop("finishDate").isNull()
    			.end()    				
    			.model();
		final fetch<Employment> fetch = fetch(Employment.class).with("contractNo", "employee", "startDate", "finishDate", "salary").fetchModel();
		final OrderingModel orderBy = orderBy().prop("employee.initials").asc().prop("startDate").desc().model();
		final QueryExecutionModel<Employment, EntityResultQueryModel<Employment>> qem = from(query).with(fetch).with(orderBy).model();

    	// the wrong way of checking existance
    	try (final Stream<Employment> employments = co(Employment.class).stream(qem)) {
			assertTrue(employments.findAny().isPresent());
		}
    	
    	// the right way of counting
    	assertTrue(co(Employment.class).exists(query));
    }

    @Override
    public boolean saveDataPopulationScriptToFile() {
        return false;
    }

    @Override
    public boolean useSavedDataPopulationScript() {
        return false;
    }

    @Override
    protected void populateDomain() {
        // Need to invoke super to create a test user that is responsible for data population 
    	super.populateDomain();

    	// Here is how the Test Case universal constants can be set.
    	// In this case the notion of now is overridden, which makes it possible to have an invariant system-time.
    	// However, the now value should be after AbstractDaoTestCase.prePopulateNow in order not to introduce any date-related conflicts.
    	final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
    	constants.setNow(now);

    	// If the use of saved data population script is indicated then there is no need to proceed with any further data population logic.
        if (useSavedDataPopulationScript()) {
            return;
        }

    	// Here the three Person entities are persisted using the the inherited from TG testing framework methods.
        var rmd = save(new_(Person.class).setInitials("RMD").setDesc("Ronald McDonald").setActive(true));
        save(new_(Employment.class).setContractNo("001").setEmployee(rmd).setStartDate(date("2019-01-01 00:00:00")).setFinishDate(date("2019-02-01 00:00:00")).setSalary(Money.of("5000.00")));
        save(new_(Employment.class).setContractNo("002").setEmployee(rmd).setStartDate(date("2019-05-01 00:00:00")).setSalary(Money.of("5100.00")));
        var jc = save(new_(Person.class).setInitials("JC").setDesc("John Carmack").setActive(true));
        save(new_(Employment.class).setContractNo("003").setEmployee(jc).setStartDate(date("2019-09-01 00:00:00")).setSalary(Money.of("9100.00")));
    }

}
