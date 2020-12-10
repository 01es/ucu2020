package helsinki.example;

import static org.junit.Assert.*;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.fetch;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.from;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.orderBy;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

import java.math.BigDecimal;

import org.junit.Test;

import ua.com.fielden.platform.dao.QueryExecutionModel;
import ua.com.fielden.platform.entity.meta.MetaProperty;
import ua.com.fielden.platform.entity.query.fluent.fetch;
import ua.com.fielden.platform.entity.query.model.EntityResultQueryModel;
import ua.com.fielden.platform.entity.query.model.OrderingModel;
import ua.com.fielden.platform.security.user.User;
import ua.com.fielden.platform.utils.IUniversalConstants;
import helsinki.personnel.Person;
import helsinki.personnel.PersonCo;
import helsinki.personnel.validators.PersonInitialsValidator;
import helsinki.test_config.AbstractDaoTestCase;
import helsinki.test_config.UniversalConstantsForTesting;

/**
 * This is an example unit test, which can be used as a starting point for creating application unit tests.
 * 
 * @author Generated
 *
 */
public class PersonnelTest extends AbstractDaoTestCase {

    /**
     * The names of the test method should be informative. 
     * It is recommended to make the method name sound like a sentence stating the expected behaviour.
     * In this case, the test method name indicates that it is expected to find person with initials RDM and that it has an active status.
     * <p> 
     * Each test method should be related to exactly one concern, which facilitates creation of unit tests that address a single concern.
     */
    @Test
    public void user_RMD_is_present_and_active() {
    	final Person person = co(Person.class).findByKey("RMD");
    	assertNotNull(person);
    	assertTrue(person.isActive());
    }

    @Test
    public void user_JC_is_present_but_not_active() {
        final Person person = co(Person.class).findByKey("JC");
        assertNotNull(person);
        assertFalse(person.isActive());
    }

    @Test
    public void initials_do_not_permit_spaces() {
    	final Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
    	assertNotNull(person);
    	assertTrue(person.isValid().isSuccessful());
    	
    	assertEquals("RMD", person.getInitials());
    	
    	person.setInitials("R MD");
    	assertFalse(person.isValid().isSuccessful());
    	
    	final MetaProperty<String> mp = person.getProperty("initials");
    	assertEquals(person, mp.getEntity());
    	assertFalse(mp.isValid());
    	assertEquals(PersonInitialsValidator.ERR_NO_SPACES_PERMITTED, mp.getFirstFailure().getMessage());
    	assertEquals("R MD", mp.getLastAttemptedValue());
    	assertEquals("R MD", mp.getLastInvalidValue());
    	assertEquals("RMD", mp.getValue());
    	assertEquals("RMD", person.getInitials());
    	
    	assertFalse(mp.isDirty());
    	
    	person.setInitials("RMD1");
    	assertTrue(person.isValid().isSuccessful());
    	
    	assertTrue(mp.isDirty());
    }

    @Test
    public void too_short_values_for_initials_result_in_warnings() {
    	final Person person = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
    	assertEquals(Long.valueOf(0L), person.getVersion());
    	
    	person.setInitials("A");
    	final MetaProperty<String> mp = person.getProperty("initials");
    	assertTrue(mp.isValid());
    	assertEquals("A", mp.getValue());
    	assertEquals("A", person.getInitials());
    	assertNotNull(mp.getFirstWarning());
    	assertEquals(PersonInitialsValidator.WARN_TOO_SHORT, mp.getFirstWarning().getMessage());
    	
    	final Person savedPerson = save(person);
    	assertEquals(Long.valueOf(1L), savedPerson.getVersion());
    	assertEquals("A", savedPerson.getInitials());
    }

    @Test
    public void chaning_different_properties_of_person_concurrently_is_resolved_automatically() {
    	final Person person1 = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
    	final Person person2 = co$(Person.class).findByKeyAndFetch(PersonCo.FETCH_PROVIDER.fetchModel(), "RMD");
    	assertEquals(Long.valueOf(0L), person1.getVersion());
    	assertEquals(Long.valueOf(0L), person2.getVersion());
    	
    	final Person savedPerson1 = save(person1.setInitials("A"));
    	assertEquals(Long.valueOf(1L), savedPerson1.getVersion());
    	
    	final Person savedPerson2 = save(person2.setEmployeeNo("SDRT123").setInitials("A"));
    	assertEquals(Long.valueOf(2L), savedPerson2.getVersion());
    	assertEquals("A", savedPerson2.getInitials());
    }

    /**
     * In case of a complex data population it is possible to store the data into a script by changing this method to return <code>true</code>.
     * <p>
     * This way it is possible to reuse it later in place of re-running the data population logic, which is a lot faster.
     * Please also refer method {@link #useSavedDataPopulationScript()} below -- the values returned by this and that method cannot be <code>true</code> simultaneously.
     */
    @Override
    public boolean saveDataPopulationScriptToFile() {
        return false;
    }

    /**
     * If the test data was populated and saved as a script file (hinted in method {@link #saveDataPopulationScriptToFile()} above),
     * then this method can be changed to return <code>true</code> in order to avoid execution of the data population logic and simply execute the saved script.
     * This makes the population of the test data a lot faster.
     * It is very convenient when there is a need to run the same test case multiple times interactively.
     * <p>
     * However, this method should never return <code>true</code> when running multiple test cases.
     * Therefore, it is important to change this method to return <code>false</code> before committing changes into your VCS such as Git.
     */
    @Override
    public boolean useSavedDataPopulationScript() {
        return true;
    }

    /**
     * Domain state population method.
     * <p>
     * <b>IMPORTANT:</p> this method executes only once for a Test Case. At the same time, new instances of a Test Case are created for each test method.
     * Thus, this method should not be used for initialisation of the Test Case state other than the persisted domain state.
     */
    @Override
    protected void populateDomain() {
        // Need to invoke super to create a test user that is responsible for data population 
    	super.populateDomain();

    	// Here is how the Test Case universal constants can be set.
    	// In this case the notion of now is overridden, which makes it possible to have an invariant system-time.
    	// However, the now value should be after AbstractDaoTestCase.prePopulateNow in order not to introduce any date-related conflicts.
    	final UniversalConstantsForTesting constants = (UniversalConstantsForTesting) getInstance(IUniversalConstants.class);
    	constants.setNow(dateTime("2019-10-01 11:30:00"));

    	// If the use of saved data population script is indicated then there is no need to proceed with any further data population logic.
        if (useSavedDataPopulationScript()) {
            return;
        }

    	// Here the three Person entities are persisted using the the inherited from TG testing framework methods.
        save(new_(Person.class).setInitials("RMD").setDesc("Ronald McDonald").setActive(true));
        save(new_(Person.class).setInitials("JC").setDesc("John Carmack").setActive(false));
    }

}
