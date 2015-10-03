package tw.meowdev.android.starter.app;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.HashMap;

public final class BusProvider {
    private static final HashMap<String, Bus> buses = new HashMap<String, Bus>();

    public static Bus get() {
        return get("GLOBAL");
    }

    public static Bus get(String name) {
        if (!buses.containsKey(name))
            return _init(name, ThreadEnforcer.MAIN);

        return buses.get(name);
    }

    public static Bus init(String name, ThreadEnforcer enforcer){
        if (buses.containsKey(name))
            return null;

        return _init(name, enforcer);
    }

    private static Bus _init(String name, ThreadEnforcer enforcer) {
        Bus newBus = new Bus(enforcer, name);
        buses.put(name, newBus);
        return newBus;
    }

    private BusProvider() {
        // No instances.
    }
}