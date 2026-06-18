/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.avatica;

import org.apache.calcite.avatica.proto.Common;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link org.apache.calcite.avatica.Meta.CursorFactory} proto conversions.
 */
public class CursorFactoryProtoTest {

  @Test
  public void testFromProtoAvoidsClassInitialization() {
    Meta.CursorFactory baseFactory = Meta.CursorFactory.create(Meta.Style.RECORD, AuthorPojo.class,
        Arrays.asList("id", "fname", "lname", "age"));
    Common.CursorFactory.Builder builder = Common.CursorFactory.newBuilder();
    builder.mergeFrom(baseFactory.toProto());
    builder.setClassName("org.apache.calcite.avatica.AuthorNoInitPojo");
    Common.CursorFactory newProto = builder.build();
    Meta.CursorFactory newFactory = Meta.CursorFactory.fromProto(newProto);
    assertNotNull(newFactory);
    assertEquals(baseFactory.fieldNames, newFactory.fieldNames);
  }
}
