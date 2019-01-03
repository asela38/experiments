package org.nand.to.tettris.vm.compiler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SymbolTableTest {

    private SymbolTable symbolTable = new SymbolTable();

    @Test
    public void testVarCountWhenSymbolTableHasOneArg() {
        symbolTable.define("a", "A", Kind.ARG);
        assertEquals(symbolTable.varCount(Kind.ARG), 1);
    }

    @Test
    public void testVarCountWhenSymbolTableHasTwoArg() {
        symbolTable.define("a", "A", Kind.ARG);
        symbolTable.define("b", "B", Kind.ARG);
        assertEquals(symbolTable.varCount(Kind.ARG), 2);
    }

    @Test
    public void testVarCountWhenEnterWithSameName() {
        symbolTable.define("a", "A", Kind.ARG);
        symbolTable.define("a", "B", Kind.ARG);
        assertEquals(symbolTable.varCount(Kind.ARG), 2);
    }

    @Test
    public void testVisualWithPrint() {
        symbolTable.define("a", "A", Kind.ARG);
        symbolTable.define("b", "B", Kind.ARG);
        symbolTable.define("c", "C", Kind.ARG);
        symbolTable.define("d", "D", Kind.ARG);
        symbolTable.define("e", "A", Kind.VAR);
        symbolTable.define("f", "A", Kind.VAR);
        symbolTable.define("g", "C", Kind.VAR);
        symbolTable.define("h", "A", Kind.STATIC);
        symbolTable.define("i", "A", Kind.STATIC);
        symbolTable.define("j", "A", Kind.STATIC);
        symbolTable.define("k", "A", Kind.STATIC);
        symbolTable.define("l", "A", Kind.STATIC);
        symbolTable.define("m", "A", Kind.FIELD);
        symbolTable.define("n", "A", Kind.FIELD);
        symbolTable.define("o", "A", Kind.FIELD);
        symbolTable.define("p", "A", Kind.FIELD);
        symbolTable.define("q", "A", Kind.FIELD);

        symbolTable.print();

        symbolTable.startSubroutine();
        System.out.println("------");

        symbolTable.print();
        System.out.println("------");
        symbolTable.define("d", "D", Kind.ARG);
        symbolTable.define("e", "A", Kind.VAR);

        symbolTable.print();

        assertThat(symbolTable.kindOf("d"), is(Kind.ARG));
        assertThat(symbolTable.kindOf("e"), is(Kind.VAR));
        assertThat(symbolTable.typeOf("e"), is("A"));
        assertThat(symbolTable.typeOf("d"), is("D"));
        assertThat(symbolTable.indexOf("d"), is(0));
    }

}
