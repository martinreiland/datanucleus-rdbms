/**********************************************************************
Copyright (c) 2008 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package org.datanucleus.store.rdbms.sql.method;

import java.util.List;

import org.datanucleus.store.rdbms.sql.SQLStatement;
import org.datanucleus.store.rdbms.sql.expression.SQLExpression;

/**
 * Interface to implement to wrap an SQL function.
 */
public interface SQLMethod
{
    /**
     * Return the expression for this SQL function.
     * @param expr The expression that it is invoked on
     * @param args Arguments passed in
     * @return The SQL expression using the SQL function
     */
    public SQLExpression getExpression(SQLExpression expr, List<SQLExpression> args);

    /**
     * Method to set the SQLStatement that we are related to.
     * @param stmt The statement
     */
    public void setStatement(SQLStatement stmt);
}