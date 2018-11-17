package org.gangel.graphomance.orientdb;

import com.orientechnologies.orient.core.id.ORID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.gangel.graphomance.NodeIdentifier;

class NodeId implements NodeIdentifier {

    @Getter
    @NonNull
    private ORID id;

    private NodeId(ORID orid) {
        this.id = orid;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public static final NodeId build(ORID orid) {
        return new NodeId(orid);
    }

    public static  final ORID getORID(final NodeIdentifier idObject) {
        if (!(idObject instanceof NodeId)) {
            throw new RuntimeException("Wrong implementation of identifier");
        }
        return ((NodeId)idObject).id;
    }
}
