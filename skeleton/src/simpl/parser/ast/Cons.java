package simpl.parser.ast;

import java.awt.Window.Type;

import simpl.interpreter.ConsValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Cons extends BinaryExpr {

    public Cons(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " :: " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeResult r1 = l.typecheck(E);
        TypeResult r2 = r.typecheck(r1.s.compose(E));
        Substitution s1 = r2.t.unify(new ListType(r1.t));
        Substitution combined = s1.compose(r2.s).compose(r1.s);
        return TypeResult.of(combined, s1.apply(new ListType(r1.t)));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        Value v1 = l.eval(s);
        Value v2 = r.eval(s);
        return new ConsValue(v1,v2);
    }
}
