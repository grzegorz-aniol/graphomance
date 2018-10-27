package org.gangel.graphomance;

public enum DbType {
  NEO4J,

  ORIENTDB,

  ARANGODB

  ;

  public static DbType of(String s) {
    if (s == null) {
      return null;
    }
    return DbType.valueOf(s.toUpperCase());
  }
}
