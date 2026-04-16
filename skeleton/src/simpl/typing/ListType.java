package simpl.typing;

import simpl.typing.TypeMismatchError;

public final class ListType extends Type {

    public Type t;

    public ListType(Type t) {
        this.t = t;
    }

    @Override
    public boolean isEqualityType() {
        // DID
        return t.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // DID
        if(t instanceof TypeVar){
            return t.unify(this);
        }
        if(t instanceof ListType){
            return this.t.unify(((ListType) t).t);
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // DID
        return t.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // DID ?
        return new ListType(this.t.replace(a, t));
    }

    public String toString() {
        return t + " list";
    }
}
