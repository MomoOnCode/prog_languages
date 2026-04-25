package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeResult r1 = l.typecheck(E);
        TypeResult r2 = r.typecheck(r1.s.compose(E));
        Substitution s1 = r1.t.unify(new RefType(r2.t));
        Substitution combined = s1.compose(r2.s).compose(r1.s);
        return TypeResult.of(combined, Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        RefValue ref = (RefValue) l.eval(s);
        Value v = (Value) r.eval(s);
        s.M.put(ref.p, v);
        return Value.UNIT;
        
    }
}
