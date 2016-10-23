package net.workingdeveloper.java.vardump;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Created by Christoph Graupner on 2016-10-23.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class FieldAcceptorPredicate implements Predicate<Field> {

    private String[] excludeFieldNames;
    private boolean  fAppendStatics;
    private boolean  fAppendTransients;

    public FieldAcceptorPredicate() {

    }

    public FieldAcceptorPredicate(String[] aExcludeFieldNames, boolean aAppendStatics, boolean aAppendTransients) {
        excludeFieldNames = aExcludeFieldNames;
        fAppendStatics = aAppendStatics;
        fAppendTransients = aAppendTransients;
    }

    public boolean isAppendStatics() {
        return fAppendStatics;
    }

    public void setAppendStatics(boolean aAppendStatics) {
        fAppendStatics = aAppendStatics;
    }

    public boolean isAppendTransients() {
        return fAppendTransients;
    }

    public void setAppendTransients(boolean aAppendTransients) {
        fAppendTransients = aAppendTransients;
    }

    public void setExcludeFieldNames(String[] aExcludeFieldNames) {
        excludeFieldNames = aExcludeFieldNames;
    }

    @Override
    public boolean test(final Field field) {
        if (field.getName().indexOf(ClassUtils.INNER_CLASS_SEPARATOR_CHAR) != -1) {
            // Reject field from inner class.
            return false;
        }
        if (Modifier.isTransient(field.getModifiers()) && !this.isAppendTransients()) {
            // Reject transient fields.
            return false;
        }
        if (Modifier.isStatic(field.getModifiers()) && !this.isAppendStatics()) {
            // Reject static fields.
            return false;
        }
        if (this.excludeFieldNames != null
                && Arrays.binarySearch(this.excludeFieldNames, field.getName()) >= 0) {
            // Reject fields from the getExcludeFieldNames list.
            return false;
        }
        if (field.isAnnotationPresent(ToStringExclude.class)) {
            return false;
        }
        return true;
    }
}
