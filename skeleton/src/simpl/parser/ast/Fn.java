package simpl.parser.ast;

import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Fn extends Expr {

    public Symbol x;
    public Expr e;

    public Fn(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
      //  return "fun";
       return "(fn " + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID
        TypeVar tv = new TypeVar(false);
        TypeEnv newE = TypeEnv.of(E, x, tv);
        TypeResult r1 = e.typecheck(newE);
        Substitution combined = r1.s;
        return TypeResult.of(combined, new ArrowType(combined.apply(tv), r1.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        // DID
        return new FunValue(s.E, x, e);
    }
}
