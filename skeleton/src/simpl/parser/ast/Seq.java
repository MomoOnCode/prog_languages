package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.Type;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeResult r1 = l.typecheck(E);
        TypeResult r2 = r.typecheck(E);
        Substitution s1 = r1.t.unify(Type.UNIT);
        Substitution combined = s1.compose(r2.s).compose(r1.s);
        return TypeResult.of(combined, r2.t);
        
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        l.eval(s);
        return r.eval(s);
    }
}
