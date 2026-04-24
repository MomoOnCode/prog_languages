package simpl.parser.ast;

import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public abstract class RelExpr extends BinaryExpr {

    public RelExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        //DID
        TypeResult r1 = l.typecheck(E);
        TypeResult r2 = r.typecheck(r1.s.compose(E));
        Substitution s3 = r1.t.unify(Type.INT);
        Substitution s4 = r2.t.unify(Type.INT);
        Substitution combined = s4.compose(s3).compose(r2.s).compose(r1.s);
        return TypeResult.of(combined, Type.BOOL);
    }
}
