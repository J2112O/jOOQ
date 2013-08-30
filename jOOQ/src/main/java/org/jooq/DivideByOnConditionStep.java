/**
 * Copyright (c) 2009-2013, Data Geekery GmbH (http://www.datageekery.com)
 * All rights reserved.
 *
 * This work is triple-licensed under ASL 2.0, AGPL 3.0, and jOOQ EULA
 * =============================================================================
 * You may choose which license applies to you:
 *
 * - If you're using this work with Open Source databases, you may choose
 *   ASL 2.0 or jOOQ EULA.
 * - If you're using this work with at least one commercial database, you may
 *   choose AGPL 3.0 or jOOQ EULA.
 *
 * For more information, please visit http://www.jooq.org/licenses
 *
 * Apache Software License 2.0:
 * -----------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * AGPL 3.0
 * -----------------------------------------------------------------------------
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this library.
 * If not, see http://www.gnu.org/licenses.
 *
 * jOOQ End User License Agreement:
 * -----------------------------------------------------------------------------
 * This library is commercial software; you may not redistribute it nor
 * modify it.
 *
 * This library is distributed with a LIMITED WARRANTY. See the jOOQ End User
 * License Agreement for more details: http://www.jooq.org/eula
 */
package org.jooq;

import org.jooq.api.annotation.State;
import org.jooq.impl.DSL;

/**
 * An intermediate type for the construction of a relational division. This type
 * can be used as a convenience type for connecting more conditions.
 *
 * @author Lukas Eder
 */
@State
public interface DivideByOnConditionStep extends DivideByReturningStep {

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#AND} operator.
     */
    @Support
    DivideByOnConditionStep and(Condition condition);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#AND} operator.
     */
    @Support
    DivideByOnConditionStep and(Field<Boolean> condition);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#AND} operator.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String)
     */
    @Support
    DivideByOnConditionStep and(String sql);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#AND} operator.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String, Object...)
     */
    @Support
    DivideByOnConditionStep and(String sql, Object... bindings);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#AND} operator.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String, QueryPart...)
     */
    @Support
    DivideByOnConditionStep and(String sql, QueryPart... parts);

    /**
     * Combine the currently assembled conditions with a negated other one using
     * the {@link Operator#AND} operator.
     */
    @Support
    DivideByOnConditionStep andNot(Condition condition);

    /**
     * Combine the currently assembled conditions with a negated other one using
     * the {@link Operator#AND} operator.
     */
    @Support
    DivideByOnConditionStep andNot(Field<Boolean> condition);

    /**
     * Combine the currently assembled conditions with an <code>EXISTS</code>
     * clause using the {@link Operator#AND} operator.
     */
    @Support
    DivideByOnConditionStep andExists(Select<?> select);

    /**
     * Combine the currently assembled conditions with a <code>NOT EXISTS</code>
     * clause using the {@link Operator#AND} operator.
     */
    @Support
    DivideByOnConditionStep andNotExists(Select<?> select);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#OR} operator.
     */
    @Support
    DivideByOnConditionStep or(Condition condition);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#OR} operator.
     */
    @Support
    DivideByOnConditionStep or(Field<Boolean> condition);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#OR} operator.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String)
     */
    @Support
    DivideByOnConditionStep or(String sql);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#OR} operator.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String, Object...)
     */
    @Support
    DivideByOnConditionStep or(String sql, Object... bindings);

    /**
     * Combine the currently assembled conditions with another one using the
     * {@link Operator#OR} operator.
     * <p>
     * <b>NOTE</b>: When inserting plain SQL into jOOQ objects, you must
     * guarantee syntax integrity. You may also create the possibility of
     * malicious SQL injection. Be sure to properly use bind variables and/or
     * escape literals when concatenated into SQL clauses!
     *
     * @see DSL#condition(String, QueryPart...)
     */
    @Support
    DivideByOnConditionStep or(String sql, QueryPart... parts);

    /**
     * Combine the currently assembled conditions with a negated other one using
     * the {@link Operator#OR} operator.
     */
    @Support
    DivideByOnConditionStep orNot(Condition condition);

    /**
     * Combine the currently assembled conditions with a negated other one using
     * the {@link Operator#OR} operator.
     */
    @Support
    DivideByOnConditionStep orNot(Field<Boolean> condition);

    /**
     * Combine the currently assembled conditions with an <code>EXISTS</code>
     * clause using the {@link Operator#OR} operator.
     */
    @Support
    DivideByOnConditionStep orExists(Select<?> select);

    /**
     * Combine the currently assembled conditions with a <code>NOT EXISTS</code>
     * clause using the {@link Operator#OR} operator.
     */
    @Support
    DivideByOnConditionStep orNotExists(Select<?> select);
}
