package simpl.parser.ast;

// import com.sun.beans.TypeResolver;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeResult r1 = e.typecheck(E);
        return TypeResult.of(r1.s, new RefType(r1.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        Value v = e.eval(s);
        int addr = s.p.get();
        s.M.put(addr, v);
        s.p.set(addr+1);
        return new RefValue(addr);      
    }
}
