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

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeResult t1 = l.typecheck(E);
        TypeResult t2 = r.typecheck(t1.s.compose(E));
        Substitution s1 = t1.t.unify(Type.BOOL);
        Substitution s2 = t2.t.unify(Type.BOOL);
        Substitution combined = s2.compose(s1).compose(t2.s).compose(t1.s);
        return TypeResult.of(combined, Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        BoolValue e1 = (BoolValue) l.eval(s);
        if(e1.b){
            return r.eval(s);
        }
        return new BoolValue(false);
    }
}
