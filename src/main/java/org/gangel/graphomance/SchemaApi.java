package org.gangel.graphomance;

public interface SchemaApi {

  long countObjects(final String clsName);

  boolean classExists(final String clsName);

  void createClass(final String clsName);

  void createNodeClass(final String clsName);

  void createRelationClass(final String clsName);

  void createClass(final String clsName, final String extendsClass);

  void dropClass(final String clsName);

  void createProperty(final String clsName, final String propName, Class type, boolean isMandatory);

  void createIndex(final String indexName, final String clsName, final IndexType indexType, boolean isUnique, String... propNames);

  void dropAllIndexesOnClass(final String clsName);
}
