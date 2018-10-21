package org.gangel.graphomance.orientdb;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import org.gangel.graphomance.IndexType;
import org.gangel.graphomance.ManagementApi;
import org.gangel.graphomance.SchemaApi;

import java.time.LocalDateTime;
import java.util.Map;

public class OrientSchemaApi implements SchemaApi {

  private final ODatabaseSession session;

  private final ManagementApi managementApi;

  OrientSchemaApi(ODatabaseSession session, ManagementApi managementApi) {
    this.session = session;
    this.managementApi = managementApi;
  }
//  final String query = "create class User if not exists extends V;\n" +
//      "create property User.login if not exists STRING (mandatory, notnull);\n" +
//      "create property User.uid if not exists STRING\t(mandatory, notnull);\n";
//                "create index IDX_USER_ID if not exists on User(uid) unique_hash_index;";
//                "create index IDX_USER_ID if not exists on User(uid) unique;";

  private final Map<Class, String> javaToDbType =
    Map.of(String.class, "STRING",
          Integer.class, "INTEGER",
          Long.class, "INTEGER",
          LocalDateTime.class, "DATETIME");

  private String convertJavaTypeToDbType(Class cls) {
    String str = javaToDbType.get(cls);
    if (str == null) {
      throw new RuntimeException(String.format("Can't map java type '%s' into Orient types", cls.getSimpleName()));
    }
    return str;
  }

  @Override
  public boolean classExists(String clsName) {
    return session.getClass(clsName) != null;
  }

  @Override
  public void createClass(String clsName) {
    managementApi.runScript(String.format("create class %s if not exists extends V;", clsName));
  }

  @Override
  public void dropClass(String clsName) {
    managementApi.runScript(String.format("drop class %s;", clsName));
  }

  @Override
  public void createProperty(String clsName, String propName, Class type, boolean isMandatory) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("create property %s.%s if not exists %s ", clsName, propName, convertJavaTypeToDbType(type)));
    if (isMandatory) {
      sb.append(" (mandatory, notnull) ");
    }
    sb.append(";");
    managementApi.runScript(sb.toString());
  }

  @Override
  public void createIndex(final String indexName, final String clsName, final IndexType indexType, boolean isUnique, String... propNames ) {
    //"create index IDX_USER_ID if not exists on User(uid) unique;";
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("create index %s if not exists on %s (%s) ",
        indexName, clsName,
        String.join(",", propNames)));
    switch (indexType) {
      case HASH_UNIQUE:
        sb.append("unique_hash_index");
        break;
      case HASH:
        sb.append("hash_index");
        break;
      case UNIQUE:
        sb.append("unique");
        break;
      case DEFAULT:
        sb.append("notunique");
        break;
    }
    managementApi.runScript(sb.toString());
  }
}
