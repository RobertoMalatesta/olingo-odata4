/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.api.op;

import java.io.InputStream;
import java.io.Serializable;
import org.apache.olingo.client.api.data.Entry;
import org.apache.olingo.client.api.data.Error;
import org.apache.olingo.client.api.data.Feed;
import org.apache.olingo.client.api.data.LinkCollection;
import org.apache.olingo.client.api.data.ServiceDocument;
import org.apache.olingo.client.api.edm.xml.XMLMetadata;
import org.apache.olingo.client.api.format.ODataFormat;
import org.apache.olingo.client.api.format.ODataPubFormat;
import org.w3c.dom.Element;

/**
 * Utility class for serialization.
 */
public interface ODataDeserializer extends Serializable {

  XMLMetadata toMetadata(InputStream input);

  /**
   * Gets the ServiceDocument object represented by the given InputStream.
   *
   * @param input stream to be de-serialized.
   * @param format OData service document format.
   * @return <tt>ServiceDocument</tt> object.
   */
  ServiceDocument toServiceDocument(InputStream input, ODataFormat format);

  /**
   * Gets a feed object from the given InputStream.
   *
   * @param input stream to be de-serialized.
   * @param format Atom or JSON
   * @return Feed instance.
   */
  Feed toFeed(InputStream input, ODataPubFormat format);

  /**
   * Gets an entry object from the given InputStream.
   *
   * @param input stream to be de-serialized.
   * @param format Atom or JSON
   * @return Entry instance.
   */
  Entry toEntry(InputStream input, ODataPubFormat format);

  /**
   * Gets a DOM representation of the given InputStream.
   *
   * @param input stream to be de-serialized.
   * @param format XML or JSON
   * @return DOM.
   */
  Element toPropertyDOM(InputStream input, ODataFormat format);

  /**
   * Gets the ODataError object represented by the given InputStream.
   *
   * @param input stream to be parsed and de-serialized.
   * @param isXML 'TRUE' if the error is represented by XML; 'FALSE' otherwise.
   * @return
   */
  Error toError(InputStream input, boolean isXML);

  /**
   * Parses the given input into a DOM tree.
   *
   * @param input stream to be parsed and de-serialized.
   * @return DOM tree
   */
  Element toDOM(InputStream input);

  /**
   * Gets a list of links from the given InputStream.
   *
   * @param input stream to be de-serialized.
   * @param format OData format.
   * @return de-serialized links.
   */
  LinkCollection toLinkCollection(InputStream input, ODataFormat format);

}