package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // Did
        TypeResult r1 = e.typecheck(E);
        Substitution s1 = r1.t.unify(Type.BOOL);
        return TypeResult.of(s1.compose(r1.s), Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        BoolValue v = (BoolValue) e.eval(s);
        return new BoolValue(!v.b);
    }
}
