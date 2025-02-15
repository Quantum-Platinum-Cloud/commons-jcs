package org.apache.commons.jcs3.auxiliary.remote.server;

import org.apache.commons.jcs3.auxiliary.remote.server.behavior.RemoteType;

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

import junit.framework.TestCase;

/**
 * Tests for the remote cache server attributes.
 */
public class RemoteCacheServerAttributesUnitTest
    extends TestCase
{

    /**
     * Verify that we get a string, even if not attributes are set.
     */
    public void testToString()
    {
        final RemoteCacheServerAttributes attributes = new RemoteCacheServerAttributes();
        assertNotNull( "Should have a string.", attributes.toString() );
    }

    /**
     * Verify that the type is set correctly and that the correct name is returned for the type.
     */
    public void testSetRemoteTypeName_local()
    {
        final RemoteCacheServerAttributes attributes = new RemoteCacheServerAttributes();
        attributes.setRemoteTypeName( "LOCAL" );
        assertEquals( "Wrong type.", RemoteType.LOCAL, attributes.getRemoteType() );
        assertEquals( "Wrong name", "LOCAL", attributes.getRemoteTypeName() );
    }

    /**
     * Verify that the type is set correctly and that the correct name is returned for the type.
     */
    public void testSetRemoteTypeName_cluster()
    {
        final RemoteCacheServerAttributes attributes = new RemoteCacheServerAttributes();
        attributes.setRemoteTypeName( "CLUSTER" );
        assertEquals( "Wrong type.", RemoteType.CLUSTER, attributes.getRemoteType() );
        assertEquals( "Wrong name", "CLUSTER", attributes.getRemoteTypeName() );
    }
}
