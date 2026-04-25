package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Rec extends Expr {

  public Symbol x;
  public Expr e;

  public Rec(Symbol x, Expr e) {
    this.x = x;
    this.e = e;
  }

  public String toString() {
    return "(rec " + x + "." + e + ")";
  }

  @Override
  public TypeResult typecheck(TypeEnv E) throws TypeError {
    // DID
    TypeVar tv = new TypeVar(false);
    TypeEnv newE = TypeEnv.of(E, x, tv);
    TypeResult r1 = e.typecheck(newE);
  // Substitution s1 = r1.t.unify(r1.s.apply(tv));
//    Substitution s1 = tv.unify(r1.t);
 //   Substitution combined = s1.compose(r1.s);
  //  return TypeResult.of(combined, combined.apply(tv));
    //RECORDS ARE NOT TYPING IM LOSING IT
    Substitution s1 = r1.s.apply(tv).unify(r1.s.apply(r1.t));
    Substitution combined = s1.compose(r1.s);
    return TypeResult.of(combined, combined.apply(r1.t));
  }

  @Override
  public Value eval(State s) throws RuntimeError {
    // DID
    return new RecValue(s.E, x, e);
  }
}
