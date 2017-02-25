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
    public static final String INDENT_STRING = "  ";
    public static final boolean DEFAULT_SHORT_CLASS_NAME = true;
    private static FormatterFactory sfInstance;
    private final  AppendableFactory fAppendableFactory;
    private final String fNullString = "<null>";
    private final String fRefString  = "REF>>";
    private String  fIndentationString;
    private boolean fShortClassName;

    protected FormatterFactory(AppendableFactory aAppendableFactory, String aIndentString, boolean aShortClassName) {
        fShortClassName = aShortClassName;
        fAppendableFactory = aAppendableFactory;
        fIndentationString = aIndentString;
    }

    public static FormatterFactory createInstance(
            AppendableFactory aAppendableFactory,
            String aIndentString,
            boolean aShortClassName
    ) {
        return new FormatterFactory(aAppendableFactory, aIndentString, aShortClassName);
    }

    public static FormatterFactory getDefaultInstance() {
        if (sfInstance == null) {
            sfInstance = createInstance(StringBuilder::new, INDENT_STRING, DEFAULT_SHORT_CLASS_NAME);
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

    public IVarDumperFormatter createVarDumperFormatter(AppendableFactory aBuffer, String aIndentString, boolean aShortClassName) {
        return new VarDumperIndentFormatterImpl(aBuffer, aIndentString, aShortClassName);
    }

    public IVarDumperFormatter createVarDumperFormatter() {
        return createVarDumperFormatter(getAppendableFactory(), getIndentationString(), isShortClassName());
    }

    public AppendableFactory getAppendableFactory() {
        return fAppendableFactory;
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
