package helsinki.webapp.config.personnel;

import static helsinki.common.StandardActionsStyles.MASTER_CANCEL_ACTION_LONG_DESC;
import static helsinki.common.StandardActionsStyles.MASTER_CANCEL_ACTION_SHORT_DESC;
import static helsinki.common.StandardActionsStyles.MASTER_SAVE_ACTION_LONG_DESC;
import static helsinki.common.StandardActionsStyles.MASTER_SAVE_ACTION_SHORT_DESC;
import static helsinki.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static java.lang.String.format;
import static ua.com.fielden.platform.web.PrefDim.mkDim;

import java.util.Optional;

import com.google.inject.Injector;

import helsinki.common.LayoutComposer;
import helsinki.common.StandardActions;
import helsinki.main.menu.personnel.MiEmployment;
import helsinki.personnel.Employment;
import helsinki.personnel.Person;
import helsinki.personnel.Supervisor;
import ua.com.fielden.platform.web.PrefDim.Unit;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
/**
 * {@link Employment} Web UI configuration.
 *
 * @author Developers
 *
 */
public class EmploymentWebUiConfig {

    public final EntityCentre<Employment> centre;
    public final EntityMaster<Employment> master;

    public static EmploymentWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new EmploymentWebUiConfig(injector, builder);
    }

    private EmploymentWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Employment}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Employment> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 1, 2, 2);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Employment.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Employment.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Employment.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(Employment.class, standardEditAction);

        final EntityCentreConfig<Employment> ecc = EntityCentreBuilder.centreFor(Employment.class)
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(Employment.class).also()
                .addCrit("salary").asRange().decimal().also()
                .addCrit("desc").asMulti().text().also()
                .addCrit("employee").asMulti().autocompleter(Person.class).also()
                .addCrit("employee.aSupervisor").asMulti().autocompleter(Supervisor.class).also()
                .addCrit("startDate").asRange().date().also()
                .addCrit("finishDate").asRange().date()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("this").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Employment.ENTITY_TITLE))
                    .withAction(standardEditAction).also()
                .addProp("employee").withActionSupplier(builder.getOpenMasterAction(Person.class)).also()
                .addProp("desc").minWidth(100).also()
                .addProp("startDate").minWidth(100).also()
                .addProp("finishDate").minWidth(100).also()
                .addProp("salary").minWidth(100).also()
                .addProp("contractDocument").minWidth(100)
                //.addProp("prop").minWidth(100).withActionSupplier(builder.getOpenMasterAction(Entity.class)).also()
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiEmployment.class, MiEmployment.class.getSimpleName(), ecc, injector, null);
    }

    /**
     * Creates entity master for {@link Employment}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Employment> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(2, 2, 2, 1);

        final IMaster<Employment> masterConfig = new SimpleMasterBuilder<Employment>().forEntity(Employment.class)
                .addProp("contractNo").asSinglelineText().also()
                .addProp("employee").asAutocompleter().also()
                .addProp("startDate").asDatePicker().also()
                .addProp("finishDate").asDatePicker().also()
                .addProp("salary").asMoney().also()
                .addProp("contractDocument").asHyperlink().also()
                .addProp("desc").asMultilineText().also()
                .addAction(MasterActions.REFRESH).shortDesc(MASTER_CANCEL_ACTION_SHORT_DESC).longDesc(MASTER_CANCEL_ACTION_LONG_DESC)
                .addAction(MasterActions.SAVE).shortDesc(MASTER_SAVE_ACTION_SHORT_DESC).longDesc(MASTER_SAVE_ACTION_LONG_DESC)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_TWO_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(Employment.class, masterConfig, injector);
    }
}