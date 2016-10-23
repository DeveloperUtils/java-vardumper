# java-vardumper()

Dumps Java POJOs similar to PHP's `vardump()`

## Usage

### Dump directly into file

```java
    PrintWriter lWriter = new PrintWriter(lFileName);

    VarDumpFactory lVarDumpFactory = VarDumpFactory.instance();
    Predicate<Field> lFieldAcceptPredicate = lVarDumpFactory.createFieldAcceptPredicate(
            new String[]{
                    "fParent"
            },
            false,
            false
    );
    IVarDumper lRecursiveDumper = lVarDumpFactory.createRecursiveDumper(
            lVarDumpFactory.createIndentFormatter(lWriter, 2, true),
            lFieldAcceptPredicate
    );
    lRecursiveDumper.vardump(lBook);
```
