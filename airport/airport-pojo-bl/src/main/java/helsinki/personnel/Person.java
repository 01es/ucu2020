package helsinki.personnel;

import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.expr;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

import helsinki.personnel.definers.PersonRequirendessForSupervisorDefiner;
import helsinki.personnel.validators.PersonInitialsValidator;
import helsinki.personnel.validators.PersonSupervisorValidator;
import helsinki.security.tokens.persistent.Person_CanModify_user_Token;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.Calculated;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
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
import ua.com.fielden.platform.entity.annotation.Readonly;
import ua.com.fielden.platform.entity.annotation.SkipEntityExistsValidation;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.Unique;
import ua.com.fielden.platform.entity.annotation.mutator.AfterChange;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.entity.query.model.ExpressionModel;
import ua.com.fielden.platform.property.validator.EmailValidator;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.security.user.User;
import ua.com.fielden.platform.utils.Pair;

/**
 * Represents a person.
 *
 * @author Generated
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle(value = "Initials", desc = "Person's initials, must represent the person uniquely - e.g. a number may be required if there are many people with the same initials.")
@DescTitle(value = "Full Name", desc = "Person's full name - e.g. the first name followed by the middle initial followed by the surname.")
@MapEntityTo
@CompanionObject(PersonCo.class)
@DescRequired
@DisplayDescription
public class Person extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Person.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    @IsProperty
	@MapTo
	@Title(value = "Initials", desc = "Desc")
	@CompositeKeyMember(1)
    @BeforeChange({@Handler(PersonInitialsValidator.class)})
	private String initials;

    @IsProperty
	@MapTo
	@Title(value = "Supervisor?", desc = "Indicates personnel in the supervisor role.")
    @AfterChange(PersonRequirendessForSupervisorDefiner.class)
	private boolean supervisor;

    @IsProperty
	@MapTo
	@Title(value = "Supervisor", desc = "A supervisor for the employee.")
    @BeforeChange({@Handler(PersonSupervisorValidator.class)})
	private Supervisor aSupervisor;

    @IsProperty
    @Unique
    @MapTo
    @Title(value = "User", desc = "An application user associated with the current person.")
    @SkipEntityExistsValidation(skipActiveOnly = true)
    private User user;

    @IsProperty
    @MapTo
    @Title(value = "Title", desc = "Person's role, position or title.")
    private String title;

    @IsProperty
    @MapTo
    @Title("Employee No")
    @AfterChange(PersonRequirendessForSupervisorDefiner.class)
    @Dependent({"aSupervisor"})
    private String employeeNo;

    @IsProperty
    @MapTo
    @Title("Phone")
    private String phone;

    @IsProperty
    @MapTo
    @Title("Mobile")
    private String mobile;

    @IsProperty
    @MapTo
    @Title("Email")
    @BeforeChange(@Handler(EmailValidator.class))
    private String email;

    @IsProperty
	@Readonly
	@Calculated
	@Title(value = "Current Employment", desc = "The current employment, if exists.")
	private Employment currEmployment;
	protected static final ExpressionModel currEmployment_ = expr().model(
			select(Employment.class)
			.where().prop("employee").eq().extProp("id")
			.and().prop("startDate").le().now()
			.and()
			.begin()
				.prop("finishDate").ge().now()
				.or()
				.prop("finishDate").isNull()
			.end()    				
			.model()).model();

	@Observable
	protected Person setCurrEmployment(final Employment currEmployment) {
		this.currEmployment = currEmployment;
		return this;
	}

	public Employment getCurrEmployment() {
		return currEmployment;
	}
    
    @Override
    @Observable
    public Person setDesc(final String desc) {
        return (Person) super.setDesc(desc);
    }

    @Observable
    public Person setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    @Observable
    public Person setMobile(final String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    @Observable
    public Person setPhone(final String phone) {
        this.phone = phone;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    @Observable
    public Person setEmployeeNo(final String employeeNo) {
        this.employeeNo = employeeNo;
        return this;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    @Observable
    public Person setTitle(final String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    @Override
    @Observable
    public Person setActive(final boolean active) {
        super.setActive(active);
        return this;
    }

    @Observable
    @Authorise(Person_CanModify_user_Token.class)
    public Person setUser(final User user) {
        this.user = user;
        return this;
    }

    public User getUser() {
        return user;
    }

    /** A convenient method to identify whether the current person instance is an application user. */
    public boolean isAUser() {
        return getUser() != null;
    }

	@Observable
	public Person setInitials(final String initials) {
		this.initials = initials;
		return this;
	}

	public String getInitials() {
		return initials;
	}

	@Observable
	public Person setSupervisor(final boolean supervisor) {
		this.supervisor = supervisor;
		return this;
	}

	public boolean isSupervisor() {
		return supervisor;
	}

	@Observable
	public Person setASupervisor(final Supervisor aSupervisor) {
		this.aSupervisor = aSupervisor;
		return this;
	}

	public Supervisor getASupervisor() {
		return aSupervisor;
	}

}