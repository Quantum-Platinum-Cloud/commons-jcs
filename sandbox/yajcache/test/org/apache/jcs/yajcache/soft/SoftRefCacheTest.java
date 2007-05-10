package org.apache.jcs.yajcache.soft;

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

import junit.framework.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jcs.yajcache.core.CacheManager;
import org.apache.jcs.yajcache.core.CacheType;
import org.apache.jcs.yajcache.core.ICache;
import org.apache.jcs.yajcache.lang.annotation.*;

/**
 *
 * @author Hanson Char
 */
@CopyRightApache
@TestOnly
public class SoftRefCacheTest extends TestCase {
    private Log log = LogFactory.getLog(this.getClass());

    public void testSoftRefCache() throws Exception {
        ICache<byte[]> c = CacheManager.inst.getCache(
                "bytesCache", byte[].class, CacheType.SOFT_REFERENCE);
        for (int h=0; h < 10; h++) {
            for (int i=h*10, max=i+10; i < max; i++) {
                log.debug("put i="+i);
                c.put(String.valueOf(i), new byte[100*1024]);
//                c.put("0", new byte[100*1024]);
                c.get("0");
            }
            for (int i=0; i < 10; i++) {
                log.debug("get i="+i +":"+ c.get(String.valueOf(i)));
            }
//            for (int i=0; i < h*10+10; i++) {
//                log.debug("get i="+i +":"+ c.get(String.valueOf(i)));
//            }
        }
        log.debug("size: " + c.size());
//        SoftRefFileCache sc = (SoftRefFileCache)c;
//        log.debug("count: " + sc.getCollectorCount());
//        log.debug(SoftRefCacheCleaner.inst.toString());

//        for (int i=0; i < 100; i++) {
//            log.debug("get i="+i +":"+ c.get(String.valueOf(i)));
//        }
//        log.debug("sleeping for 5 secs");
//        Thread.sleep(5*1000);

        for (int i=0; i < 100; i++) {
            log.debug("get i="+i +":"+ c.get(String.valueOf(i)));
        }
        log.debug("size: " + c.size());
//        log.debug("count: " + sc.getCollectorCount());
//        log.debug(SoftRefCacheCleaner.inst.toString());
        log.debug(c);
        log.debug(CacheManager.inst);
    }
}
