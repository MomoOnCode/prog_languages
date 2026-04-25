package simpl.interpreter;

public class ConsValue extends Value {

    public final Value v1, v2;

    public ConsValue(Value v1, Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        // DID
        int count = 0;
        Value current = this;
        while(current instanceof ConsValue) {
            count++;
            current = ((ConsValue) current).v2;
        }
        return "list@" + count;
    }

    @Override
    public boolean equals(Object other) {
        // DID 
        return (other instanceof ConsValue) &&
            v1.equals(((ConsValue) other).v1) &&
            v2.equals(((ConsValue) other).v2);
    }
}
