package helsinki.personnel;

import java.util.Date;

import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DateOnly;
import ua.com.fielden.platform.entity.annotation.Dependent;
import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.validation.annotation.GeProperty;
import ua.com.fielden.platform.entity.validation.annotation.LeProperty;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.types.Hyperlink;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Contract")
@CompanionObject(EmploymentCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
public class Employment extends AbstractPersistentEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Employment.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty
	@MapTo
	@Title(value = "Contract No", desc = "Contract number for this employment.")
	@CompositeKeyMember(1)
	private String contractNo;

    @IsProperty
	@MapTo
	@Title(value = "Employee", desc = "Employee under this contract.")
    @Required
	private Person employee;

    @IsProperty
	@MapTo
	@Dependent("finishDate")
	@Title(value = "Start Date", desc = "The start of the employment period.")
    @Required
    @DateOnly
	private Date startDate;

	@IsProperty
	@MapTo
	@Dependent("startDate")
	@Title(value = "Finish Date", desc = "The finish date of the employment period.")
	@DateOnly
	private Date finishDate;

	@IsProperty
	@MapTo
	@Title(value = "Contract Document", desc = "A hyperlink to the contract.")
	private Hyperlink contractDocument;
	
	@IsProperty
	@MapTo
	@Title(value = "Salary", desc = "The initial salary under the contract.")
	private Money salary;

	@Observable
	public Employment setSalary(final Money salary) {
		this.salary = salary;
		return this;
	}

	public Money getSalary() {
		return salary;
	}

	@Observable
	public Employment setContractDocument(final Hyperlink contractDocument) {
		this.contractDocument = contractDocument;
		return this;
	}

	public Hyperlink getContractDocument() {
		return contractDocument;
	}
	
	@Observable
	@LeProperty("finishDate")
	public Employment setStartDate(final Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public Date getStartDate() {
		return startDate;
	}

	@Observable
	@GeProperty("startDate")
	public Employment setFinishDate(final Date finishDate) {
		this.finishDate = finishDate;
		return this;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	@Observable
	public Employment setEmployee(final Person employee) {
		this.employee = employee;
		return this;
	}

	public Person getEmployee() {
		return employee;
	}
    
	@Observable
	public Employment setContractNo(final String contractNo) {
		this.contractNo = contractNo;
		return this;
	}

	public String getContractNo() {
		return contractNo;
	}

}
