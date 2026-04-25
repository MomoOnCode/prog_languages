package simpl.parser.ast;

// import com.sun.beans.TypeResolver;

import simpl.interpreter.Env;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.Substitution;

public class Let extends Expr {

    public Symbol x;
    public Expr e1, e2;

    public Let(Symbol x, Expr e1, Expr e2) {
        this.x = x;
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(let " + x + " = " + e1 + " in " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeResult r1 = e1.typecheck(E);
        TypeEnv newE = TypeEnv.of(r1.s.compose(E), x, r1.s.apply(r1.t));
        TypeResult r2 = e2.typecheck(newE);
        Substitution combined = r2.s.compose(r1.s);
        return TypeResult.of(combined, combined.apply(r2.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        Value v1 = e1.eval(s);
        Env newE = new Env(s.E, x, v1);
        return e2.eval(State.of(newE, s.M, s.p));
        
    }
}
