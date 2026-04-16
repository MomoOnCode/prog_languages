package simpl.typing;

import simpl.typing.TypeMismatchError;

final class BoolType extends Type {

    protected BoolType() {
    }

    @Override
    public boolean isEqualityType() {
        // DID
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // DID
        if(t instanceof TypeVar) return t.unify(this);
        if( t instanceof BoolType) return Substitution.IDENTITY;
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // DID ?
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // DID
        return Type.BOOL;
    }

    public String toString() {
        return "bool";
    }
}
