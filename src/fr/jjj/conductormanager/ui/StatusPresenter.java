package fr.jjj.conductormanager.ui;

import fr.jjj.conductormanager.ConductorRegistry;
import fr.jjj.conductormanager.RegistryListener;
import fr.jjj.conductormanager.model.ConductorDesc;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jaunais on 04/08/2014.
 */
public class StatusPresenter implements RegistryListener {

    private StatusView view;

    public StatusPresenter(StatusView view) {
        ConductorRegistry.INSTANCE.addListener(this);
        update();
    }

    private void update() {
        Set<ConductorDesc> conductors = ConductorRegistry.INSTANCE.getDeviceConductors();

        view.setGlobalStatus(conductors.size() + " registered conductor(s).");

        String list = "";
        Iterator<ConductorDesc> it = conductors.iterator();
        while (it.hasNext()) {
            list += it.next().getLabel();
            if (it.hasNext()) {
                list += ", ";
            }
        }
        view.setDetailedStatus("(" + list + ")");
    }

    @Override
    public void conductorListChanged() {
        update();
    }

    @Override
    public void deviceListChanged() {

    }
}
