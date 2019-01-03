package org.nand.to.tettris.vm.compiler;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SymbolTable {

    private enum Scope {
        CLASS(Kind.FIELD, Kind.STATIC), SUBROUTINE(Kind.ARG, Kind.VAR);

        private static Map<Kind, Scope> map = new EnumMap<>(Kind.class);

        static {
            for (Scope scope : Scope.values()) {
                for (Kind kind : scope.kinds) {
                    map.put(kind, scope);
                }
            }
        }

        private Kind[] kinds;

        private Scope(Kind... kinds) {
            this.kinds = kinds;
        }

        public static Scope of(Kind kind) {
            return map.get(kind);
        }
    }

    private Map<Scope, Map<Kind, Map<String, Map<String, Integer>>>> st          = new HashMap<>();
    private HashMap<String, String>                                  varsInScope = new HashMap<>();

    public void startSubroutine() {
        st.get(Scope.SUBROUTINE).values().stream().flatMap(e -> e.values().stream())
                .flatMap(e -> e.keySet().stream()).forEach(varsInScope::remove);

        st.put(Scope.SUBROUTINE, new HashMap<>());
    }

    public void define(String name, String type, Kind kind) {
        if (varsInScope.containsKey(name))
            throw new IllegalArgumentException(String.format("%s already exist. %s", name, varsInScope.get(name)));

        varsInScope.put(name, String.format("%s-%s-%s-%s", Scope.of(kind), kind, type, name));

        st.putIfAbsent(Scope.of(kind), new HashMap<>());
        Map<Kind, Map<String, Map<String, Integer>>> scopeSt = st.get(Scope.of(kind));
        scopeSt.putIfAbsent(kind, new HashMap<>());
        Map<String, Map<String, Integer>> kindst = scopeSt.get(kind);
        kindst.putIfAbsent(type, new HashMap<>());
        Map<String, Integer> map = kindst.get(type);
        map.put(name, kindst.values().stream().mapToInt(Map::size).sum());
    }

    public int varCount(Kind kind) {
        return st.get(Scope.of(kind)).get(kind).values().stream().mapToInt(Map::size).sum();
    }

    public Kind kindOf(String fname) {
        AtomicReference<Kind> ref = new AtomicReference<>(Kind.NONE);
        st.forEach((scope, a) -> {
            a.forEach((kind, b) -> {
                b.forEach((type, c) -> {
                    c.forEach((name, index) -> {
                        if (name.equals(fname)) {
                            ref.set(kind);
                        }
                    });
                });
            });
        });

        return ref.get();
    }

    public String typeOf(String fname) {
        AtomicReference<String> ref = new AtomicReference<>();
        st.forEach((scope, a) -> {
            a.forEach((kind, b) -> {
                b.forEach((type, c) -> {
                    c.forEach((name, index) -> {
                        if (name.equals(fname)) {
                            ref.set(type);
                        }
                    });
                });
            });
        });

        return ref.get();
    }

    public int indexOf(String fname) {
        AtomicInteger ref = new AtomicInteger();
        st.forEach((scope, a) -> {
            a.forEach((kind, b) -> {
                b.forEach((type, c) -> {
                    c.forEach((name, index) -> {
                        if (name.equals(fname)) {
                            ref.set(index);
                        }
                    });
                });
            });
        });

        return ref.get();
    }

    public void print() {
        st.forEach((scope, a) -> {
            a.forEach((kind, b) -> {
                b.forEach((type, c) -> {
                    c.forEach((name, index) -> {
                        System.out.printf("%10s - %-6s - %-10s - %s - %d %n", scope, kind, type, name, index);
                    });
                });
            });
        });

    }
}
