/*
 * *************************************************************************************
 *  Copyright (C) 2006-2015 EsperTech, Inc. All rights reserved.                       *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core.thread;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.core.service.EPRuntimeImpl;
import com.espertech.esper.core.service.EPStatementHandleCallback;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Route unit for single match.
 */
public class RouteUnitSingle implements RouteUnitRunnable
{
    private static final Log log = LogFactory.getLog(RouteUnitSingle.class);

    private final EPRuntimeImpl epRuntime;
    private EPStatementHandleCallback handleCallback;
    private final EventBean theEvent;
    private final long filterVersion;

    /**
     * Ctor.
     * @param epRuntime runtime to process
     * @param handleCallback callback
     * @param theEvent event
     * @param filterVersion version of filter
     */
    public RouteUnitSingle(EPRuntimeImpl epRuntime, EPStatementHandleCallback handleCallback, EventBean theEvent, long filterVersion)
    {
        this.epRuntime = epRuntime;
        this.theEvent = theEvent;
        this.handleCallback = handleCallback;
        this.filterVersion = filterVersion;
    }

    public void run()
    {
        try
        {
            epRuntime.processStatementFilterSingle(handleCallback.getAgentInstanceHandle(), handleCallback, theEvent, filterVersion);

            epRuntime.dispatch();

            epRuntime.processThreadWorkQueue();
        }
        catch (RuntimeException e)
        {
            log.error("Unexpected error processing route execution: " + e.getMessage(), e);
        }
    }

}
