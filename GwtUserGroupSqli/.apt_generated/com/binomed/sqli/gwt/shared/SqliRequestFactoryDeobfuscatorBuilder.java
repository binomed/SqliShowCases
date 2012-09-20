// Automatically Generated -- DO NOT EDIT
// com.binomed.sqli.gwt.shared.SqliRequestFactory
package com.binomed.sqli.gwt.shared;
import java.util.Arrays;
import com.google.web.bindery.requestfactory.vm.impl.OperationData;
import com.google.web.bindery.requestfactory.vm.impl.OperationKey;
public final class SqliRequestFactoryDeobfuscatorBuilder extends com.google.web.bindery.requestfactory.vm.impl.Deobfuscator.Builder {
{
withOperation(new OperationKey("SuadInbGBUSgdGTD2dZx2f$mwVo="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Ljava/lang/String;Ljava/lang/String;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Ljava/lang/String;Ljava/lang/String;)Lcom/binomed/sqli/gwt/server/model/SqliUser;")
  .withMethodName("verifyUser")
  .withRequestContext("com.binomed.sqli.gwt.shared.SqliUserRequest")
  .build());
withOperation(new OperationKey("qrTILW7cRRWSeyqBS_VUrMf$FiE="),
  new OperationData.Builder()
  .withClientMethodDescriptor("()Lcom/google/web/bindery/requestfactory/shared/InstanceRequest;")
  .withDomainMethodDescriptor("()V")
  .withMethodName("persist")
  .withRequestContext("com.binomed.sqli.gwt.shared.SqliUserRequest")
  .build());
withOperation(new OperationKey("be08YeTY6lbGlq$FlSn2GtEq1jc="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Ljava/lang/String;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Ljava/lang/String;)Lcom/binomed/sqli/gwt/server/model/SqliUser;")
  .withMethodName("findUser")
  .withRequestContext("com.binomed.sqli.gwt.shared.SqliUserRequest")
  .build());
withOperation(new OperationKey("cO$BjvMda8bXSUc$0UBQzYIrz7Q="),
  new OperationData.Builder()
  .withClientMethodDescriptor("(Ljava/lang/Long;)Lcom/google/web/bindery/requestfactory/shared/Request;")
  .withDomainMethodDescriptor("(Ljava/lang/Long;)Lcom/binomed/sqli/gwt/server/model/SqliUser;")
  .withMethodName("findSqliUser")
  .withRequestContext("com.binomed.sqli.gwt.shared.SqliUserRequest")
  .build());
withRawTypeToken("xEDp9go$wt7QqNDtDOamuHccPOU=", "com.binomed.sqli.gwt.shared.model.SqliUserProxy");
withRawTypeToken("w1Qg$YHpDaNcHrR5HZ$23y518nA=", "com.google.web.bindery.requestfactory.shared.EntityProxy");
withRawTypeToken("FXHD5YU0TiUl3uBaepdkYaowx9k=", "com.google.web.bindery.requestfactory.shared.BaseProxy");
withClientToDomainMappings("com.binomed.sqli.gwt.server.model.SqliUser", Arrays.asList("com.binomed.sqli.gwt.shared.model.SqliUserProxy"));
}}
