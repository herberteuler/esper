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

package com.espertech.esper.core.context.stmt;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.core.ExprEvaluator;
import com.espertech.esper.epl.expression.core.ExprEvaluatorContext;
import com.espertech.esper.epl.expression.prior.ExprPriorEvalStrategy;

public class AIRegistryPriorSingle implements AIRegistryPrior, ExprPriorEvalStrategy {

    private ExprPriorEvalStrategy strategy;

    public AIRegistryPriorSingle() {
    }

    public void assignService(int num, ExprPriorEvalStrategy value) {
        strategy = value;
    }

    public void deassignService(int num) {
        strategy = null;
    }

    public int getAgentInstanceCount() {
        return strategy == null ? 0 : 1;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext, int streamNumber, ExprEvaluator evaluator, int constantIndexNumber) {
        return strategy.evaluate(eventsPerStream, isNewData, exprEvaluatorContext, streamNumber, evaluator, constantIndexNumber);
    }
}
