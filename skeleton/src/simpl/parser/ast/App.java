package simpl.parser.ast;

import simpl.interpreter.Env;
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

public class App extends BinaryExpr {

  public App(Expr l, Expr r) {
    super(l, r);
  }

  public String toString() {
    return "(" + l + " " + r + ")";
  }

  @Override
  public TypeResult typecheck(TypeEnv E) throws TypeError {
    // DID
    TypeVar tv = new TypeVar(false);
    TypeResult r1 = l.typecheck(E);
    TypeResult r2 = r.typecheck(r1.s.compose(E));
    Substitution s1 = r1.t.unify(new ArrowType(r2.t, tv));
    Substitution combined = s1.compose(r2.s).compose(r1.s);
    return TypeResult.of(combined, combined.apply(tv));
  }

  @Override
  public Value eval(State s) throws RuntimeError {
    // DID
    FunValue f = (FunValue) l.eval(s);
    Value v = r.eval(s);
    Env newE = new Env(f.E, f.x, v);
    return f.e.eval(State.of(newE, s.M, s.p));
  }
}
