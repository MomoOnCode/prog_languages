package simpl.interpreter.lib;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class fst extends FunValue {

  public fst() {
    // DID
    super(Env.empty, Symbol.symbol("x"), new Expr() {
      public Value eval(State s) throws RuntimeError {
        PairValue v = (PairValue) s.E.get(Symbol.symbol("x"));
        return v.v1;
      }

      public TypeResult typecheck(TypeEnv E) {
        return null;
      }
    });
  }
}
