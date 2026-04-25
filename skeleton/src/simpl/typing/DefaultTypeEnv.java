package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        // DID ?
    TypeVar t1 = new TypeVar(false);
    TypeVar t2 = new TypeVar(false);
    TypeVar l1 = new TypeVar(false);
    TypeVar l2 = new TypeVar(false);

    E = TypeEnv.of(TypeEnv.empty, Symbol.symbol("fst"), new ArrowType(new PairType(t1, t2), t1));
    E = TypeEnv.of(E, Symbol.symbol("snd"), new ArrowType(new PairType(t1, t2), t2));
    E = TypeEnv.of(E, Symbol.symbol("hd"),  new ArrowType(new ListType(l1), l1));
    E = TypeEnv.of(E, Symbol.symbol("tl"),  new ArrowType(new ListType(l2), new ListType(l2)));
    E = TypeEnv.of(E, Symbol.symbol("iszero"),             new ArrowType(Type.INT, Type.BOOL));
    E = TypeEnv.of(E, Symbol.symbol("succ"),               new ArrowType(Type.INT, Type.INT));
    E = TypeEnv.of(E, Symbol.symbol("pred"),               new ArrowType(Type.INT, Type.INT));

}    

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
