package org.gangel.graphomance;

public interface ManagementApi {

  void runScript(final String script);

  void runScriptFromResource(final String resourcePath);

}
