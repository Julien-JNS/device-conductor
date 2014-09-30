package fr.jjj.conductor.standalone;

import fr.jjj.conductor.Conductor;
import fr.jjj.conductor.ConductorFactory;
import fr.jjj.conductor.TestUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jjaunais
 * Date: 30/09/14
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */
public class Starter {

    private static Conductor conductor;

    public static void main(String[] args)
    {
        TestUtils.initializeConfig();
        TestUtils.initializeFileSystem();
        conductor=ConductorFactory.getInstance().getConductor();
    }
}
