package simpl.typing;

import simpl.typing.TypeMismatchError;

final class IntType extends Type {

    protected IntType() {
    }

    @Override
    public boolean isEqualityType() {
        // DID
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // DID
        if(t instanceof TypeVar) return t.unify(this); // defer to typevar for binding
        if(t instanceof IntType) return Substitution.IDENTITY; // int = int
        throw new TypeMismatchError(); // int = bool or something
        // return null;
    }

    @Override
    public boolean contains(TypeVar tv) {
        // DID ?
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // DID ?
        return Type.INT;
        // return null;
    }

    public String toString() {
        return "int";
    }
}
