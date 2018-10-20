package org.gangel.graphomance.orientdb;

import com.orientechnologies.orient.core.id.ORID;
import lombok.Getter;
import lombok.NonNull;
import org.gangel.graphomance.RelationIdentifier;

class EdgeId implements RelationIdentifier {

    @Getter
    @NonNull
    private ORID id;

    public EdgeId(ORID orid) {
        this.id = orid;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public static final EdgeId build(ORID orid) {
        return new EdgeId(orid);
    }
}
