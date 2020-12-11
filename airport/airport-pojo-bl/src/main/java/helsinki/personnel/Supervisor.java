package helsinki.personnel;

import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.expr;
import static ua.com.fielden.platform.entity.query.fluent.EntityQueryUtils.select;

import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.DynamicEntityKey;
import ua.com.fielden.platform.entity.annotation.Calculated;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
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
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.query.model.ExpressionModel;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Supervisor")
@CompanionObject(SupervisorCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
public class Supervisor extends ActivatableAbstractEntity<DynamicEntityKey> {
	private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter
			.getEntityTitleAndDesc(Supervisor.class);
	public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
	public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

	@IsProperty
	@MapTo
	@Title(value = "Person", desc = "Person that is in the supervisor role.")
	@CompositeKeyMember(1)
	private Person person;

	@IsProperty
	@Readonly
	@Calculated
	@Title(value = "Description", desc = "Desc")
	private String desc;
	protected static final ExpressionModel desc_ = expr().prop("person.desc").model();

	@Override
	@Observable
	public Supervisor setDesc(final String desc) {
		this.desc = desc;
		return this;
	}

	public String getDesc() {
		return desc;
	}

	@Observable
	public Supervisor setPerson(final Person person) {
		this.person = person;
		return this;
	}

	public Person getPerson() {
		return person;
	}

	@Override
	@Observable
	public Supervisor setActive(boolean active) {
		super.setActive(active);
		return this;
	}
}