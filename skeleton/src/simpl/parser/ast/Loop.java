package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeMismatchError;
import simpl.typing.TypeResult;

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeResult t1 = e1.typecheck(E);
        TypeResult t2 = e2.typecheck(t1.s.compose(E));
        //this should throw its own TypeMismatchError();
        Substitution s1 = t1.t.unify(Type.BOOL);
        Substitution s2 = t2.t.unify(Type.UNIT);
        Substitution combined = s2.compose(s1).compose(t2.s).compose(t1.s);
        return TypeResult.of(combined, Type.UNIT);        
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        BoolValue v1 = (BoolValue) e1.eval(s);
        if(v1.b){
            e2.eval(s);
            return eval(s);
        } else {
            return Value.UNIT;
        }
    }
}
