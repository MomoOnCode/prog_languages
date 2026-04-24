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
import simpl.typing.TypeVar;

public class Deref extends UnaryExpr {

    public Deref(Expr e) {
        super(e);
    }

    public String toString() {
        return "!" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeVar tv = new TypeVar(false);
        TypeResult r1 = e.typecheck(E);
        Substitution s1 = r1.t.unify(new RefType(tv));
        Substitution combined = s1.compose(r1.s);
        return TypeResult.of(combined, combined.apply(tv));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        RefValue ref = (RefValue) e.eval(s);
        return s.M.get(ref.p);
    }
}
