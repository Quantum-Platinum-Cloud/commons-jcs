package org.apache.commons.jcs3.auxiliary.remote.server;

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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.jcs3.auxiliary.MockCacheEventLogger;
import org.apache.commons.jcs3.auxiliary.remote.MockRemoteCacheListener;
import org.apache.commons.jcs3.utils.timing.SleepUtil;
import org.apache.commons.jcs3.auxiliary.remote.RemoteUtils;
import org.apache.commons.jcs3.auxiliary.remote.server.behavior.IRemoteCacheServerAttributes;
import org.apache.commons.jcs3.auxiliary.remote.server.behavior.RemoteType;
import org.apache.commons.jcs3.engine.CacheElement;
import org.apache.commons.jcs3.engine.behavior.ICacheElement;

import junit.framework.TestCase;

/**
 * Since the server does not know that it is a server, it is easy to unit test. The factory does all
 * the rmi work.
 */
public class RemoteCacheServerUnitTest
    extends TestCase
{
    private static final String expectedIp1 = "adfasdf";
    private static final String expectedIp2 = "adsfadsafaf";

    private RemoteCacheServer<String, String> server;

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        final IRemoteCacheServerAttributes rcsa = new RemoteCacheServerAttributes();
        rcsa.setConfigFileName( "/TestRemoteCacheServer.ccf" );
        final Properties config = RemoteUtils.loadProps(rcsa.getConfigFileName());
        this.server = new RemoteCacheServer<>( rcsa, config );
    }

    @Override
    protected void tearDown() throws Exception
    {
        this.server.shutdown();

        super.tearDown();
    }

    /**
     * Add a listener. Pass the id of 0, verify that the server sets a new listener id. Do another
     * and verify that the second gets an id of 2.
     * <p>
     * @throws Exception
     */
    public void testAddListenerToCache_LOCALtype()
        throws Exception
    {
        final MockRemoteCacheListener<String, String> mockListener1 = new MockRemoteCacheListener<>();
        mockListener1.remoteType = RemoteType.LOCAL;
        mockListener1.localAddress = expectedIp1;
        final MockRemoteCacheListener<String, String> mockListener2 = new MockRemoteCacheListener<>();
        mockListener1.remoteType = RemoteType.LOCAL;
        mockListener2.localAddress = expectedIp2;

        final String cacheName = "testAddListener";

        // DO WORK
        server.addCacheListener( cacheName, mockListener1 );
        server.addCacheListener( cacheName, mockListener2 );

        // VERIFY
        assertEquals( "Wrong listener id.", 1, mockListener1.getListenerId() );
        assertEquals( "Wrong listener id.", 2, mockListener2.getListenerId() );
        assertEquals( "Wrong ip.", expectedIp1, server.getExtraInfoForRequesterId( 1 ) );
        assertEquals( "Wrong ip.", expectedIp2, server.getExtraInfoForRequesterId( 2 ) );
    }

    /**
     * Add a listener. Pass the id of 0, verify that the server sets a new listener id. Do another
     * and verify that the second gets an id of 2.
     * <p>
     * @throws Exception
     */
    public void testAddListenerToCache_CLUSTERtype()
        throws Exception
    {
        final MockRemoteCacheListener<String, String> mockListener1 = new MockRemoteCacheListener<>();
        mockListener1.remoteType = RemoteType.CLUSTER;
        mockListener1.localAddress = expectedIp1;
        final MockRemoteCacheListener<String, String> mockListener2 = new MockRemoteCacheListener<>();
        mockListener1.remoteType = RemoteType.CLUSTER;
        mockListener2.localAddress = expectedIp2;

        final String cacheName = "testAddListener";

        // DO WORK
        server.addCacheListener( cacheName, mockListener1 );
        server.addCacheListener( cacheName, mockListener2 );

        // VERIFY
        assertEquals( "Wrong listener id.", 1, mockListener1.getListenerId() );
        assertEquals( "Wrong listener id.", 2, mockListener2.getListenerId() );
        assertEquals( "Wrong ip.", expectedIp1, server.getExtraInfoForRequesterId( 1 ) );
        assertEquals( "Wrong ip.", expectedIp2, server.getExtraInfoForRequesterId( 2 ) );
    }

    // TODO: This test only works if preconfigured remote caches exist. Need to fix.
//    /**
//     * Add a listener. Pass the id of 0, verify that the server sets a new listener id. Do another
//     * and verify that the second gets an id of 2.
//     * <p>
//     * @throws Exception
//     */
//    public void testAddListener_ToAll()
//        throws Exception
//    {
//        MockRemoteCacheListener<String, String> mockListener1 = new MockRemoteCacheListener<>();
//        mockListener1.localAddress = expectedIp1;
//        MockRemoteCacheListener<String, String> mockListener2 = new MockRemoteCacheListener<>();
//        mockListener2.localAddress = expectedIp2;
//
//        // DO WORK
//        // don't specify the cache name
//        server.addCacheListener( mockListener1 );
//        server.addCacheListener( mockListener2 );
//
//        // VERIFY
//        assertEquals( "Wrong listener id.", 1, mockListener1.getListenerId() );
//        assertEquals( "Wrong listener id.", 2, mockListener2.getListenerId() );
//        assertEquals( "Wrong ip.", expectedIp1, server.getExtraInfoForRequesterId( 1 ) );
//        assertEquals( "Wrong ip.", expectedIp2, server.getExtraInfoForRequesterId( 2 ) );
//    }

    /**
     * Add a listener. Pass the id of 0, verify that the server sets a new listener id. Do another
     * and verify that the second gets an id of 2. Call remove Listener and verify that it is
     * removed.
     * <p>
     * @throws Exception
     */
    public void testAddListener_ToAllThenRemove()
        throws Exception
    {
        final MockRemoteCacheListener<String, String> mockListener1 = new MockRemoteCacheListener<>();
        final MockRemoteCacheListener<String, String> mockListener2 = new MockRemoteCacheListener<>();

        final String cacheName = "testAddListenerToAllThenRemove";

        // DO WORK
        server.addCacheListener( cacheName, mockListener1 );
        server.addCacheListener( cacheName, mockListener2 );

        // VERIFY
        assertEquals( "Wrong number of listeners.", 2, server.getCacheListeners( cacheName ).eventQMap.size() );
        assertEquals( "Wrong listener id.", 1, mockListener1.getListenerId() );
        assertEquals( "Wrong listener id.", 2, mockListener2.getListenerId() );

        // DO WORK
        server.removeCacheListener( cacheName, mockListener1.getListenerId() );
        assertEquals( "Wrong number of listeners.", 1, server.getCacheListeners( cacheName ).eventQMap.size() );
    }

    /**
     * Add a listener. Pass the id of 0, verify that the server sets a new listener id. Do another
     * and verify that the second gets an id of 2. Call remove Listener and verify that it is
     * removed.
     * <p>
     * @throws Exception
     */
    public void testAddListener_ToAllThenRemove_clusterType()
        throws Exception
    {
        final MockRemoteCacheListener<String, String> mockListener1 = new MockRemoteCacheListener<>();
        mockListener1.remoteType = RemoteType.CLUSTER;
        final MockRemoteCacheListener<String, String> mockListener2 = new MockRemoteCacheListener<>();
        mockListener2.remoteType = RemoteType.CLUSTER;

        final String cacheName = "testAddListenerToAllThenRemove";

        // DO WORK
        server.addCacheListener( cacheName, mockListener1 );
        server.addCacheListener( cacheName, mockListener2 );

        // VERIFY
        assertEquals( "Wrong number of listeners.", 0, server.getCacheListeners( cacheName ).eventQMap.size() );
        assertEquals( "Wrong number of listeners.", 2, server.getClusterListeners( cacheName ).eventQMap.size() );
        assertEquals( "Wrong listener id.", 1, mockListener1.getListenerId() );
        assertEquals( "Wrong listener id.", 2, mockListener2.getListenerId() );

        // DO WORK
        server.removeCacheListener( cacheName, mockListener1.getListenerId() );
        assertEquals( "Wrong number of listeners.", 1, server.getClusterListeners( cacheName ).eventQMap.size() );
        assertNull( "Should be no entry in the ip map.", server.getExtraInfoForRequesterId( 1 ) );
    }

    /**
     * Register a listener and then verify that it is called when we put using a different listener
     * id.
     * @throws Exception
     */
    public void testSimpleRegisterListenerAndPut()
        throws Exception
    {
        final IRemoteCacheServerAttributes rcsa = new RemoteCacheServerAttributes();
        rcsa.setConfigFileName( "/TestRemoteCacheServer.ccf" );

        final Properties config = RemoteUtils.loadProps(rcsa.getConfigFileName());
        final MockRemoteCacheListener<String, Long> mockListener = new MockRemoteCacheListener<>();
        final RemoteCacheServer<String, Long> server = new RemoteCacheServer<>( rcsa, config );

        final String cacheName = "testSimpleRegisterListenerAndPut";
        server.addCacheListener( cacheName, mockListener );

        // DO WORK
        final List<ICacheElement<String, Long>> inputItems = new LinkedList<>();
        final int numToPut = 10;

        for ( int i = 0; i < numToPut; i++ )
        {
            final ICacheElement<String, Long> element = new CacheElement<>( cacheName, String.valueOf( i ), Long.valueOf( i ) );
            inputItems.add( element );
            server.update( element, 9999 );
        }

        Thread.sleep( 100 );
        Thread.yield();
        Thread.sleep( 100 );

        // VERIFY
        assertEquals( "Wrong number of items put to listener.", numToPut, mockListener.putItems.size() );
        for ( int i = 0; i < numToPut; i++ )
        {
            assertEquals( "Wrong item.", inputItems.get( i ), mockListener.putItems.get( i ) );
        }

        server.shutdown();
    }

    /**
     * Register a listener and then verify that it is called when we put using a different listener
     * id. The updates should come from a cluster listener and local cluster consistency should be
     * true.
     * <p>
     * @throws Exception
     */
    public void testSimpleRegisterListenerAndPut_FromClusterWithLCC()
        throws Exception
    {
        // SETUP
        final IRemoteCacheServerAttributes rcsa = new RemoteCacheServerAttributes();
        rcsa.setLocalClusterConsistency( true );
        rcsa.setConfigFileName( "/TestRemoteCacheServer.ccf" );
        final Properties config = RemoteUtils.loadProps(rcsa.getConfigFileName());
        final RemoteCacheServer<String, Long> server = new RemoteCacheServer<>( rcsa, config );

        // this is to get the listener id for inserts.
        final MockRemoteCacheListener<String, Long> clusterListener = new MockRemoteCacheListener<>();
        clusterListener.remoteType = RemoteType.CLUSTER;

        // this should get the updates
        final MockRemoteCacheListener<String, Long> localListener = new MockRemoteCacheListener<>();
        localListener.remoteType = RemoteType.LOCAL;

        final String cacheName = "testSimpleRegisterListenerAndPut_FromClusterWithLCC";
        server.addCacheListener( cacheName, clusterListener );
        server.addCacheListener( cacheName, localListener );

        // DO WORK
        final List<ICacheElement<String, Long>> inputItems = new LinkedList<>();
        final int numToPut = 10;

        for ( int i = 0; i < numToPut; i++ )
        {
            final ICacheElement<String, Long> element = new CacheElement<>( cacheName, String.valueOf( i ), Long.valueOf( i ) );
            inputItems.add( element );
            // update using the cluster listener id
            server.update( element, clusterListener.getListenerId() );
        }

        SleepUtil.sleepAtLeast( 200 );
        Thread.yield();
        SleepUtil.sleepAtLeast( 200 );

        // VERIFY
        assertEquals( "Wrong number of items put to listener.", numToPut, localListener.putItems.size() );
        for ( int i = 0; i < numToPut; i++ )
        {
            assertEquals( "Wrong item.", inputItems.get( i ), localListener.putItems.get( i ) );
        }

        server.shutdown();
    }

    /**
     * Register a listener and then verify that it is called when we put using a different listener
     * id.
     * @throws Exception
     */
    public void testSimpleRegisterListenerAndRemove()
        throws Exception
    {
        final MockRemoteCacheListener<String, String> mockListener = new MockRemoteCacheListener<>();

        final String cacheName = "testSimpleRegisterListenerAndPut";
        server.addCacheListener( cacheName, mockListener );

        // DO WORK
        final int numToPut = 10;

        for ( int i = 0; i < numToPut; i++ )
        {
            // use a junk listener id
            server.remove( cacheName, String.valueOf( i ), 9999 );
        }

        Thread.sleep( 100 );
        Thread.yield();
        Thread.sleep( 100 );

        // VERIFY
        assertEquals( "Wrong number of items removed from listener.", numToPut, mockListener.removedKeys.size() );
        for ( int i = 0; i < numToPut; i++ )
        {
            assertEquals( "Wrong key.", String.valueOf( i ), mockListener.removedKeys.get( i ) );
        }
    }

    /**
     * Verify event log calls.
     * <p>
     * @throws Exception
     */
    public void testUpdate_simple()
        throws Exception
    {
        final MockCacheEventLogger cacheEventLogger = new MockCacheEventLogger();
        server.setCacheEventLogger( cacheEventLogger );

        final ICacheElement<String, String> item = new CacheElement<>( "region", "key", "value" );

        // DO WORK
        server.update( item );

        // VERIFY
        assertEquals( "Start should have been called.", 1, cacheEventLogger.startICacheEventCalls );
        assertEquals( "End should have been called.", 1, cacheEventLogger.endICacheEventCalls );
    }

    /**
     * Verify event log calls.
     * <p>
     * @throws Exception
     */
    public void testGet_simple()
        throws Exception
    {
        final MockCacheEventLogger cacheEventLogger = new MockCacheEventLogger();
        server.setCacheEventLogger( cacheEventLogger );

        // DO WORK
        server.get( "region", "key" );

        // VERIFY
        assertEquals( "Start should have been called.", 1, cacheEventLogger.startICacheEventCalls );
        assertEquals( "End should have been called.", 1, cacheEventLogger.endICacheEventCalls );
    }

    /**
     * Verify event log calls.
     * <p>
     * @throws Exception
     */
    public void testGetMatching_simple()
        throws Exception
    {
        final MockCacheEventLogger cacheEventLogger = new MockCacheEventLogger();
        server.setCacheEventLogger( cacheEventLogger );

        // DO WORK
        server.getMatching( "region", "pattern", 0 );

        // VERIFY
        assertEquals( "Start should have been called.", 1, cacheEventLogger.startICacheEventCalls );
        assertEquals( "End should have been called.", 1, cacheEventLogger.endICacheEventCalls );
    }

    /**
     * Verify event log calls.
     * <p>
     * @throws Exception
     */
    public void testGetMultiple_simple()
        throws Exception
    {
        final MockCacheEventLogger cacheEventLogger = new MockCacheEventLogger();
        server.setCacheEventLogger( cacheEventLogger );

        // DO WORK
        server.getMultiple( "region", new HashSet<>() );

        // VERIFY
        assertEquals( "Start should have been called.", 1, cacheEventLogger.startICacheEventCalls );
        assertEquals( "End should have been called.", 1, cacheEventLogger.endICacheEventCalls );
    }

    /**
     * Verify event log calls.
     * <p>
     * @throws Exception
     */
    public void testRemove_simple()
        throws Exception
    {
        final MockCacheEventLogger cacheEventLogger = new MockCacheEventLogger();
        server.setCacheEventLogger( cacheEventLogger );

        // DO WORK
        server.remove( "region", "key" );

        // VERIFY
        assertEquals( "Start should have been called.", 1, cacheEventLogger.startICacheEventCalls );
        assertEquals( "End should have been called.", 1, cacheEventLogger.endICacheEventCalls );
    }

    /**
     * Verify event log calls.
     * <p>
     * @throws Exception
     */
    public void testRemoveAll_simple()
        throws Exception
    {
        final MockCacheEventLogger cacheEventLogger = new MockCacheEventLogger();
        server.setCacheEventLogger( cacheEventLogger );

        // DO WORK
        server.removeAll( "region" );

        // VERIFY
        assertEquals( "Start should have been called.", 1, cacheEventLogger.startICacheEventCalls );
        assertEquals( "End should have been called.", 1, cacheEventLogger.endICacheEventCalls );
    }
}
