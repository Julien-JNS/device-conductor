package fr.jjj.conductor.access;

import fr.jjj.conductor.ConductorImpl;

/**
 * Created by Jaunais on 01/07/2014.
 */
public abstract class ConductorAccess {

    protected ConductorImpl conductor;

    protected ConductorAccess(ConductorImpl conductor) {
        this.conductor = conductor;
    }

    public abstract void close();


}
