package net.workingdeveloper.java.vardump.impl.formatter.indent;

import net.workingdeveloper.java.vardump.AppendableFactory;
import net.workingdeveloper.java.vardump.IVarDumperFormatter;
import net.workingdeveloper.java.vardump.formatter.*;
import net.workingdeveloper.java.vardump.impl.formatter.IVarDumperFormatterFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Christoph Graupner on 2016-12-31.
 *
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class FormatterFactory implements IVarDumperFormatterFactory {
    private static FormatterFactory  sfInstance;
    private final  AppendableFactory fAppendableFactory;
    private final  int               fIndentLevel;
    private final String fNullString        = "<null>";
    private final String fRefString         = "REF>>";
    private       String fIndentationString = "  ";
    private boolean fShortClassName;

    public FormatterFactory(AppendableFactory aAppendableFactory, int aIndentLevel, boolean aShortClassName) {
        fShortClassName = aShortClassName;
        fAppendableFactory = aAppendableFactory;
        fIndentLevel = aIndentLevel;
    }

    public static FormatterFactory createInstance(
            AppendableFactory aAppendableFactory,
            int aIndentLevel,
            boolean aShortClassName
    ) {
        return new FormatterFactory(aAppendableFactory, aIndentLevel, aShortClassName);
    }

    public static FormatterFactory getDefaultInstance() {
        if (sfInstance == null) {
            sfInstance = createInstance(StringBuilder::new, 2, true);
        }
        return sfInstance;
    }

    @Override
    public IArrayEntryFormatter createArrayEntryFormatter(Object aObject, Appendable aBuffer, int aEntryNumber, int aIndention, IArrayFormatter aParent) {
        final ArrayEntryFormatter lArrayEntryFormatter = new ArrayEntryFormatter(
                aIndention, aParent, aBuffer, this, aEntryNumber);
        lArrayEntryFormatter.open(aObject);
        return lArrayEntryFormatter;
    }

    @Override
    public IArrayFormatter createArrayFormatter(Collection<?> aCollection, Appendable aBuffer, int aIndentation, IElementFormatter aParent) {

        final ArrayFormatter lArrayFormatter = new ArrayFormatter(aIndentation, aParent, aBuffer, this);
        lArrayFormatter.open(aCollection);
        return lArrayFormatter;
    }

    @Override
    public IArrayFormatter createArrayFormatter(Object[] aArray, Appendable aBuffer, int aIndentation, IElementFormatter aParent) {
        final ArrayFormatter lArrayFormatter = new ArrayFormatter(aIndentation, aParent, aBuffer, this);
        lArrayFormatter.open(aArray);
        return lArrayFormatter;
    }

    public IFieldFormatter createFieldFormatter(String aName, Appendable aBuffer, int aIndentation, IElementFormatter aParent) {
        final FieldFormatter lFieldFormatter = new FieldFormatter(aIndentation, aParent, aBuffer, this);
        lFieldFormatter.open(aName);
        return lFieldFormatter;
    }

    @Override
    public IMapEntryFormatter createMapEntryFormatter(Map.Entry<?, ?> aEntry, Appendable aBuffer, int aIndentation, IMapFormatter aParent) {
        final MapEntryFormatter lMapEntryFormatter = new MapEntryFormatter(aIndentation, aParent, aBuffer, this);
        lMapEntryFormatter.open(aEntry);
        return lMapEntryFormatter;
    }

    @Override
    public IMapFormatter createMapFormatter(Map<?, ?> aMap, Appendable aBuffer, int aIndentation, IElementFormatter aParent) {
        final MapFormatter lMapFormatter = new MapFormatter(aIndentation, aParent, aBuffer, this);
        lMapFormatter.open(aMap);
        return lMapFormatter;
    }

    @Override
    public IObjectFormatter createObjectFormatter(Object aObject, Appendable aBuffer, int aIndentation, IElementFormatter aParent) {
        final ObjectFormatter lObjectFormatter = new ObjectFormatter(aIndentation, aParent, aBuffer, this);
        lObjectFormatter.open(aObject);
        return lObjectFormatter;
    }

    @Override
    public IPrimitiveFormatter createPrimitiveFormatter(Appendable aBuffer, int aIndentation, IElementFormatter aParent) {
        return new PrimitiveFormatter(aIndentation, aParent, aBuffer, this);
    }

    public IVarDumperFormatter createVarDumperFormatter(AppendableFactory aBuffer, int aIndentLevel, boolean aShortClassName) {
        return new VarDumperIndentFormatterImpl(aBuffer, aIndentLevel, aShortClassName);
    }

    public IVarDumperFormatter createVarDumperFormatter() {
        return createVarDumperFormatter(fAppendableFactory, fIndentLevel, fShortClassName);
    }

    public String getIndentationString() {
        return fIndentationString;
    }

    public void setIndentationString(String aIndentationString) {
        fIndentationString = aIndentationString;
    }

    public String getNullString() {
        return fNullString;
    }

    public String getRefString() {
        return fRefString;
    }

    @Override
    public boolean isShortClassName() {
        return fShortClassName;
    }

    @Override
    public void setShortClassName(boolean aShortClassName) {
        fShortClassName = aShortClassName;
    }
}
