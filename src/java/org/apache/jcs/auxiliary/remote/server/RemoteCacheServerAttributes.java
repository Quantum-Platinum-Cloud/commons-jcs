package org.apache.jcs.auxiliary.remote.server;


/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.apache.jcs.auxiliary.AbstractAuxiliaryCacheAttributes;
import org.apache.jcs.auxiliary.AuxiliaryCacheAttributes;

import org.apache.jcs.auxiliary.remote.server.behavior.IRemoteCacheServerAttributes;
import org.apache.jcs.auxiliary.remote.behavior.IRemoteCacheConstants;

/**
 * Description of the Class
 *
 */
public class RemoteCacheServerAttributes extends AbstractAuxiliaryCacheAttributes
	implements IRemoteCacheServerAttributes
{

    private String remoteServiceName = IRemoteCacheConstants.REMOTE_CACHE_SERVICE_VAL;
    private String remoteHost;
    private int remotePort;

    /*
     * failover servers will be used by local caches one at a time.
     * Listeners will be registered with all cluster servers.
     * If we add a get from cluster attribute we will have the ability
     * to chain clusters and have them get from each other.
     */
    private String clusterServers = "";
    private boolean getFromCluster = true;

    private int servicePort = 0;

    private int remoteType = LOCAL;

    private boolean removeUponRemotePut = true;
    private boolean getOnly = false;

    private boolean localClusterConsistency = false;

    private boolean allowClusterGet = false;
    private String configFileName = "";

    /** Constructor for the RemoteCacheAttributes object */
    public RemoteCacheServerAttributes() { }


    /**
     * Gets the remoteTypeName attribute of the RemoteCacheAttributes object
     *
     * @return The remoteTypeName value
     */
    public String getRemoteTypeName()
    {
        if ( remoteType == LOCAL )
        {
            return "LOCAL";
        }
        else if ( remoteType == CLUSTER )
        {
            return "CLUSTER";
        }
        return "LOCAL";
    }


    /**
     * Sets the remoteTypeName attribute of the RemoteCacheAttributes object
     *
     * @param s The new remoteTypeName value
     */
    public void setRemoteTypeName( String s )
    {
        if ( s.equals( "LOCAL" ) )
        {
            remoteType = LOCAL;
        }
        else if ( s.equals( "CLUSTER" ) )
        {
            remoteType = CLUSTER;
        }
    }


    /**
     * Gets the remoteType attribute of the RemoteCacheAttributes object
     *
     * @return The remoteType value
     */
    public int getRemoteType()
    {
        return remoteType;
    }


    /**
     * Sets the remoteType attribute of the RemoteCacheAttributes object
     *
     * @param p The new remoteType value
     */
    public void setRemoteType( int p )
    {
        this.remoteType = p;
    }

   
    /** Description of the Method */
    public AuxiliaryCacheAttributes copy()
    {
        try
        {
            return ( AuxiliaryCacheAttributes ) this.clone();
        }
        catch ( Exception e )
        {
        }
        return this;
    }


    /**
     * Gets the remoteServiceName attribute of the RemoteCacheAttributes object
     *
     * @return The remoteServiceName value
     */
    public String getRemoteServiceName()
    {
        return this.remoteServiceName;
    }


    /**
     * Sets the remoteServiceName attribute of the RemoteCacheAttributes object
     *
     * @param s The new remoteServiceName value
     */
    public void setRemoteServiceName( String s )
    {
        this.remoteServiceName = s;
    }


    /**
     * Gets the remoteHost attribute of the RemoteCacheAttributes object
     *
     * @return The remoteHost value
     */
    public String getRemoteHost()
    {
        return this.remoteHost;
    }


    /**
     * Sets the remoteHost attribute of the RemoteCacheAttributes object
     *
     * @param s The new remoteHost value
     */
    public void setRemoteHost( String s )
    {
        this.remoteHost = s;
    }


    /**
     * Gets the remotePort attribute of the RemoteCacheAttributes object
     *
     * @return The remotePort value
     */
    public int getRemotePort()
    {
        return this.remotePort;
    }


    /**
     * Sets the remotePort attribute of the RemoteCacheAttributes object
     *
     * @param p The new remotePort value
     */
    public void setRemotePort( int p )
    {
        this.remotePort = p;
    }


    /**
     * Gets the clusterServers attribute of the RemoteCacheAttributes object
     *
     * @return The clusterServers value
     */
    public String getClusterServers()
    {
        return this.clusterServers;
    }


    /**
     * Sets the clusterServers attribute of the RemoteCacheAttributes object
     *
     * @param s The new clusterServers value
     */
    public void setClusterServers( String s )
    {
        this.clusterServers = s;
    }


    /**
     * Gets the localPort attribute of the RemoteCacheAttributes object
     *
     * @return The localPort value
     */
    public int getServicePort()
    {
        return this.servicePort;
    }


    /**
     * Sets the localPort attribute of the RemoteCacheAttributes object
     *
     * @param p The new localPort value
     */
    public void setServicePort( int p )
    {
        this.servicePort = p;
    }


    /**
     * Gets the removeUponRemotePut attribute of the RemoteCacheAttributes
     * object
     *
     * @return The removeUponRemotePut value
     */
    public boolean getRemoveUponRemotePut()
    {
        return this.removeUponRemotePut;
    }


    /**
     * Sets the removeUponRemotePut attribute of the RemoteCacheAttributes
     * object
     *
     * @param r The new removeUponRemotePut value
     */
    public void setRemoveUponRemotePut( boolean r )
    {
        this.removeUponRemotePut = r;
    }


    /**
     * Gets the getOnly attribute of the RemoteCacheAttributes object
     *
     * @return The getOnly value
     */
    public boolean getGetOnly()
    {
        return this.getOnly;
    }


    /**
     * Sets the getOnly attribute of the RemoteCacheAttributes object
     *
     * @param r The new getOnly value
     */
    public void setGetOnly( boolean r )
    {
        this.getOnly = r;
    }

    /**
     * Should cluster updates be propogated to the locals
     *
     * @return The localClusterConsistency value
     */
    public boolean getLocalClusterConsistency()
    {
        return localClusterConsistency;
    }

    /**
     * Should cluster updates be propogated to the locals
     *
     * @param r The new localClusterConsistency value
     */
    public void setLocalClusterConsistency( boolean r )
    {
        this.localClusterConsistency = r;
    }


    /**
     * Should cluster updates be propogated to the locals
     *
     * @return The localClusterConsistency value
     */
    public boolean getAllowClusterGet()
    {
        return allowClusterGet;
    }

    /**
     * Should cluster updates be propogated to the locals
     *
     * @param r The new localClusterConsistency value
     */
    public void setAllowClusterGet( boolean r )
    {
        allowClusterGet = r;
    }

    /**
     * Gets the ConfigFileName attribute of the IRemoteCacheAttributes object
     *
     * @return The clusterServers value
     */
    public String getConfigFileName()
    {
        return configFileName;
    }


    /**
     * Sets the ConfigFileName attribute of the IRemoteCacheAttributes object
     *
     * @param s The new clusterServers value
     */
    public void setConfigFileName( String s )
    {
        configFileName = s;
    }


    /** Description of the Method */
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append( "\nremotePort = " + this.remoteHost );
        buf.append( "\nremotePort = " + this.remotePort );
        buf.append( "\ncacheName = " + this.cacheName );
        buf.append( "\nremoveUponRemotePut = " + this.removeUponRemotePut );
        buf.append( "\ngetOnly = " + getOnly );
        return buf.toString();
    }

}
