package simpl.parser.ast;

import java.lang.classfile.TypeAnnotation.TypeArgumentTarget;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.TypeCheckError;

import simpl.typing.ListType;
import simpl.typing.PairType;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeMismatchError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        // DID ?
        TypeResult r1 = l.typecheck(E);
        TypeResult r2 = r.typecheck(r1.s.compose(E));
        Substitution s3 = r1.t.unify(r2.t);
        Substitution combined = s3.compose(r2.s).compose(r1.s);
        if(!combined.apply(r1.t).isEqualityType()){
            throw new TypeMismatchError();
        }
        return TypeResult.of(combined, Type.BOOL);
     }
}

