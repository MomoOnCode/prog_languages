package simpl.typing;

import simpl.typing.TypeMismatchError;

public final class RefType extends Type {

    public Type t;

    public RefType(Type t) {
        this.t = t;
    }

    @Override
    public boolean isEqualityType() {
        // DID
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // DID
        if(t instanceof TypeVar){
            return t.unify(this);
        }
        if(t instanceof RefType){
            return this.t.unify(((RefType) t).t);
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
        // DID
        return new RefType(this.t.replace(a,t));
    }

    public String toString() {
        return t + " ref";
    }
}
