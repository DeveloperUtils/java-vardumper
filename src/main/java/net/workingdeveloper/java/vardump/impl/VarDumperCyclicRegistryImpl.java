package net.workingdeveloper.java.vardump.impl;

import net.workingdeveloper.java.vardump.IVarDumperCyclicRegistry;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Christoph Graupner on 2016-10-20.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class VarDumperCyclicRegistryImpl implements IVarDumperCyclicRegistry {
    private Set<IDKey> fKeys = new HashSet<>();

    @Override
    public IVarDumperCyclicRegistry clear() {
        fKeys.clear();
        return this;
    }

    @Override
    public boolean isRegistered(Object aObject) {
        return fKeys.contains(new IDKey(aObject));
    }

    @Override
    public IVarDumperCyclicRegistry register(Object aObject) {
        fKeys.add(new IDKey(aObject));
        return this;
    }
}
