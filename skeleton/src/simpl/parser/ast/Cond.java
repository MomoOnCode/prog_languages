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

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeResult r1 = e1.typecheck(E);
        Substitution s1 = r1.t.unify(Type.BOOL);
        
        TypeResult r2 = e2.typecheck(r1.s.compose(E));
        TypeResult r3 = e3.typecheck(r2.s.compose(E));
        Substitution s2 = r2.t.unify(r3.t);
        Substitution combined = s2.compose(r3.s).compose(r2.s).compose(s1).compose(r1.s);
        return TypeResult.of(combined, s2.apply(r2.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        BoolValue b1 = (BoolValue) e1.eval(s);
        if(b1.b){
            return e2.eval(s);
        } else return e3.eval(s);
        
                
    }
}
