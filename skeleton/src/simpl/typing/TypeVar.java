package simpl.typing;

import simpl.parser.Symbol;

public class TypeVar extends Type {

    private static int tvcnt = 0;

    private boolean equalityType;
    private Symbol name;

    public TypeVar(boolean equalityType) {
        this.equalityType = equalityType;
        name = Symbol.symbol("tv" + ++tvcnt);
    }

    @Override
    public boolean isEqualityType() {
        return equalityType;
    }

    @Override
    public Substitution unify(Type t) throws TypeCircularityError {
        // DID ?
        if(t instanceof TypeVar && ((TypeVar) t).name == this.name){
            return Substitution.IDENTITY;
        }
        if(t.contains(this)){ // occurs check
            throw new TypeCircularityError();
        }
        return Substitution.of(this, t);
    }

    public String toString() {
        return "" + name;
    }

    @Override
    public boolean contains(TypeVar tv) {
        // DID ?
        // all the type errors on name idk man
        return this.name == tv.name;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // DID
        
        return (this.name == a.name) ? t : this;
    }
}
