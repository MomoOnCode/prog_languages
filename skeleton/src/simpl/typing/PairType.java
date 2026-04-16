package simpl.typing;

import java.util.concurrent.Flow.Subscription;

import simpl.typing.TypeMismatchError;

public final class PairType extends Type {

    public Type t1, t2;

    public PairType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        // DID
        return t1.isEqualityType() && t2.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        // DID
        if(t instanceof TypeVar){
            return t.unify(this);
        }
        if(t instanceof PairType){
            PairType that = (PairType) t;
            Substitution s1 = this.t1.unify(that.t1);
            Substitution s2 = s1.apply(this.t2).unify(s1.apply(that.t2));
            return s2.compose(s1);
        }
        throw new TypeMismatchError();
    }

    @Override
    public boolean contains(TypeVar tv) {
        // DID
        return t1.contains(tv) || t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        // DID
        return new PairType(t1.replace(a,t), t2.replace(a,t));
    }

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
