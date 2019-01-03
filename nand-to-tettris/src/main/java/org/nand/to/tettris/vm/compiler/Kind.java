package org.nand.to.tettris.vm.compiler;

public enum Kind {

    STATIC(KeyWord.STATIC), FIELD(KeyWord.STATIC), ARG(KeyWord.STATIC), VAR(KeyWord.STATIC), NONE(null);

    private KeyWord keyWord;

    private Kind(KeyWord keyWord) {
        this.keyWord = keyWord;
    }

    public static Kind of(KeyWord keyWord) {
        for (Kind kind : Kind.values()) {
            if (kind.keyWord == keyWord) {
                return kind;
            }
        }

        return NONE;
    }

}
