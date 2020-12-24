package helsinki.webapp.config.personnel;

import static helsinki.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static java.lang.String.format;

import java.util.Optional;

import com.google.inject.Injector;

import helsinki.common.LayoutComposer;
import helsinki.common.StandardActions;
import helsinki.main.menu.personnel.MiSupervisor;
import helsinki.personnel.Person;
import helsinki.personnel.Supervisor;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
/**
 * {@link Supervisor} Web UI configuration.
 *
 * @author Developers
 *
 */
public class SupervisorWebUiConfig {

    public final EntityCentre<Supervisor> centre;

    public static SupervisorWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new SupervisorWebUiConfig(injector, builder);
    }

    private SupervisorWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
    }

    /**
     * Creates entity centre for {@link Supervisor}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Supervisor> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkGridForCentre(1, 2);

        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Supervisor.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<Supervisor> ecc = EntityCentreBuilder.centreFor(Supervisor.class)
                .runAutomatically()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(Supervisor.class).also()
                .addCrit("desc").asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("person").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Supervisor.ENTITY_TITLE))
                    .withActionSupplier(builder.getOpenMasterAction(Person.class)).also()
                .addProp("active").minWidth(100).also()
                .addProp("desc").minWidth(100).also()
                .addProp("person.employeeNo").minWidth(100).also()
                .addProp("person.aSupervisor").minWidth(100)
                .build();

        return new EntityCentre<>(MiSupervisor.class, MiSupervisor.class.getSimpleName(), ecc, injector, null);
    }

}