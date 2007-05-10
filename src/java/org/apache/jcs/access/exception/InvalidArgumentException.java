package org.apache.jcs.access.exception;

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

/**
 * InvalidArgumentException is thrown if an argument is passed to the cache that
 * is invalid. For instance, null values passed to put result in this exception.
 */
public class InvalidArgumentException
    extends CacheException
{
    private static final long serialVersionUID = -6058373692208755562L;

    /** Constructor for the InvalidArgumentException object */
    public InvalidArgumentException()
    {
        super();
    }

    /**
     * Constructor for the InvalidArgumentException object
     * @param message
     */
    public InvalidArgumentException( String message )
    {
        super( message );
    }
}
