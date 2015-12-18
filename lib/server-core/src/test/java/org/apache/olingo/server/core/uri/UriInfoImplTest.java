/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.server.core.uri;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.apache.olingo.commons.api.edm.EdmAction;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriInfoKind;
import org.apache.olingo.server.api.uri.UriResourceAction;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.queryoption.AliasQueryOption;
import org.apache.olingo.server.api.uri.queryoption.QueryOption;
import org.apache.olingo.server.core.uri.queryoption.AliasQueryOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.CountOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.CustomQueryOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.ExpandOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.FilterOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.FormatOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.IdOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.LevelsOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.OrderByOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SearchOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SelectOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SkipOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.SkipTokenOptionImpl;
import org.apache.olingo.server.core.uri.queryoption.TopOptionImpl;
import org.junit.Test;
import org.mockito.Mockito;

public class UriInfoImplTest {

  @Test
  public void kind() {
    final UriInfo uriInfo = new UriInfoImpl().setKind(UriInfoKind.all);
    assertEquals(UriInfoKind.all, uriInfo.getKind());
  }

  @Test
  public void casts() {
    final UriInfo uriInfo = new UriInfoImpl();

    assertEquals(uriInfo, uriInfo.asUriInfoAll());
    assertEquals(uriInfo, uriInfo.asUriInfoBatch());
    assertEquals(uriInfo, uriInfo.asUriInfoCrossjoin());
    assertEquals(uriInfo, uriInfo.asUriInfoEntityId());
    assertEquals(uriInfo, uriInfo.asUriInfoMetadata());
    assertEquals(uriInfo, uriInfo.asUriInfoResource());
    assertEquals(uriInfo, uriInfo.asUriInfoService());
  }

  @Test
  public void entityNames() {
    final UriInfo uriInfo = new UriInfoImpl()
        .addEntitySetName("A")
        .addEntitySetName("B");
    assertArrayEquals(new String[] { "A", "B" }, uriInfo.getEntitySetNames().toArray());
  }

  @Test
  public void resourceParts() {
    UriInfoImpl uriInfo = new UriInfoImpl();

    final UriResourceAction action = new UriResourceActionImpl((EdmAction) null);
    final UriResourceEntitySet entitySet0 = new UriResourceEntitySetImpl(null);
    final UriResourceEntitySet entitySet1 = new UriResourceEntitySetImpl(null);

    uriInfo.addResourcePart(action);
    uriInfo.addResourcePart(entitySet0);

    assertEquals(action, uriInfo.getUriResourceParts().get(0));
    assertEquals(entitySet0, uriInfo.getUriResourceParts().get(1));

    assertEquals(entitySet0, uriInfo.getLastResourcePart());

    uriInfo.addResourcePart(entitySet1);
    assertEquals(entitySet1, uriInfo.getLastResourcePart());
  }

  @Test(expected = ODataRuntimeException.class)
  public void doubleSystemQueryOptions() {
    new UriInfoImpl()
        .setSystemQueryOption(new FormatOptionImpl())
        .setSystemQueryOption(new FormatOptionImpl());
  }

  @Test
  public void customQueryOption() {
    final QueryOption expand = new ExpandOptionImpl().setName("");
    final QueryOption filter = new FilterOptionImpl().setName("");
    final QueryOption format = new FormatOptionImpl().setName("");
    final QueryOption id = new IdOptionImpl().setName("");
    final QueryOption inlinecount = new CountOptionImpl().setName("");
    final QueryOption orderby = new OrderByOptionImpl().setName("");
    final QueryOption search = new SearchOptionImpl().setName("");
    final QueryOption select = new SelectOptionImpl().setName("");
    final QueryOption skip = new SkipOptionImpl().setName("");
    final QueryOption skipToken = new SkipTokenOptionImpl().setName("");
    final QueryOption top = new TopOptionImpl().setName("");
    final QueryOption levels = new LevelsOptionImpl().setName("");

    final QueryOption customOption0 = new CustomQueryOptionImpl().setName("").setText("A");
    final QueryOption customOption1 = new CustomQueryOptionImpl().setName("").setText("B");

    final QueryOption initialQueryOption = new CustomQueryOptionImpl();

    final QueryOption alias = new AliasQueryOptionImpl().setName("alias").setText("C");

    final UriInfo uriInfo = new UriInfoImpl()
        .setQueryOptions(Arrays.asList(
            expand,
            filter,
            format,
            id,
            inlinecount,
            orderby,
            search,
            select,
            skip,
            skipToken,
            top,
            customOption0,
            customOption1,
            levels,
            initialQueryOption,
            alias));

    assertEquals(12, uriInfo.getSystemQueryOptions().size());
    assertEquals(expand, uriInfo.getExpandOption());
    assertEquals(filter, uriInfo.getFilterOption());
    assertEquals(format, uriInfo.getFormatOption());
    assertEquals(id, uriInfo.getIdOption());
    assertEquals(inlinecount, uriInfo.getCountOption());
    assertEquals(orderby, uriInfo.getOrderByOption());
    assertEquals(search, uriInfo.getSearchOption());
    assertEquals(select, uriInfo.getSelectOption());
    assertEquals(skip, uriInfo.getSkipOption());
    assertEquals(skipToken, uriInfo.getSkipTokenOption());
    assertEquals(top, uriInfo.getTopOption());

    assertArrayEquals(new QueryOption[] { alias }, uriInfo.getAliases().toArray());
    assertEquals("C", uriInfo.getValueForAlias("alias"));

    assertArrayEquals(new QueryOption[] { customOption0, customOption1, initialQueryOption },
        uriInfo.getCustomQueryOptions().toArray());
  }

  @Test
  public void fragment() {
    final UriInfo uriInfo = new UriInfoImpl().setFragment("F");
    assertEquals("F", uriInfo.getFragment());
  }

  @Test
  public void entityTypeCast() {
    final EdmEntityType entityType = Mockito.mock(EdmEntityType.class);
    final UriInfo uriInfo = new UriInfoImpl()
        .setEntityTypeCast(entityType);
    assertEquals(entityType, uriInfo.getEntityTypeCast());
  }

  @Test
  public void alias() {
    final UriInfo uriInfo = new UriInfoImpl()
        .addAlias((AliasQueryOption) new AliasQueryOptionImpl().setName("A").setText("notUsed"))
        .addAlias((AliasQueryOption) new AliasQueryOptionImpl().setName("A").setText("X"))
        .addAlias((AliasQueryOption) new AliasQueryOptionImpl().setName("B").setText("Y"))
        .addAlias((AliasQueryOption) new AliasQueryOptionImpl().setName("C").setText("Z"));

    assertEquals(3, uriInfo.getAliases().size());
    assertEquals("X", uriInfo.getValueForAlias("A"));
    assertEquals("Y", uriInfo.getValueForAlias("B"));
    assertEquals("Z", uriInfo.getValueForAlias("C"));
    assertNull(uriInfo.getValueForAlias("D"));

    assertTrue(uriInfo.getSystemQueryOptions().isEmpty());
    assertTrue(uriInfo.getCustomQueryOptions().isEmpty());
  }
}
