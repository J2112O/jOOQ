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

package org.jooq.impl;

import static java.util.Arrays.asList;
import static org.jooq.Clause.CONDITION;
import static org.jooq.Clause.CONDITION_BETWEEN;
import static org.jooq.Clause.CONDITION_BETWEEN_SYMMETRIC;
import static org.jooq.Clause.CONDITION_NOT_BETWEEN;
import static org.jooq.Clause.CONDITION_NOT_BETWEEN_SYMMETRIC;
import static org.jooq.SQLDialect.ASE;
import static org.jooq.SQLDialect.CUBRID;
import static org.jooq.SQLDialect.DB2;
import static org.jooq.SQLDialect.DERBY;
import static org.jooq.SQLDialect.FIREBIRD;
import static org.jooq.SQLDialect.H2;
import static org.jooq.SQLDialect.MARIADB;
import static org.jooq.SQLDialect.MYSQL;
import static org.jooq.SQLDialect.ORACLE;
import static org.jooq.SQLDialect.SQLITE;
import static org.jooq.SQLDialect.SQLSERVER;
import static org.jooq.SQLDialect.SYBASE;
import static org.jooq.impl.DSL.val;

import org.jooq.BetweenAndStep;
import org.jooq.BindContext;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;

/**
 * @author Lukas Eder
 */
class BetweenCondition<T> extends AbstractCondition implements BetweenAndStep<T> {

    private static final long     serialVersionUID              = -4666251100802237878L;
    private static final Clause[] CLAUSES_BETWEEN               = { CONDITION, CONDITION_BETWEEN };
    private static final Clause[] CLAUSES_BETWEEN_SYMMETRIC     = { CONDITION, CONDITION_BETWEEN_SYMMETRIC };
    private static final Clause[] CLAUSES_NOT_BETWEEN           = { CONDITION, CONDITION_NOT_BETWEEN };
    private static final Clause[] CLAUSES_NOT_BETWEEN_SYMMETRIC = { CONDITION, CONDITION_NOT_BETWEEN_SYMMETRIC };

    private final boolean         symmetric;
    private final boolean         not;
    private final Field<T>        field;
    private final Field<T>        minValue;
    private Field<T>              maxValue;

    BetweenCondition(Field<T> field, Field<T> minValue, boolean not, boolean symmetric) {
        this.field = field;
        this.minValue = minValue;
        this.not = not;
        this.symmetric = symmetric;
    }

    @Override
    public final Condition and(T value) {
        return and(val(value));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public final Condition and(Field f) {
        if (maxValue == null) {
            this.maxValue = f;
            return this;
        }
        else {
            return super.and(f);
        }
    }

    @Override
    public final void bind(BindContext ctx) {
        delegate(ctx.configuration()).bind(ctx);
    }

    @Override
    public final void toSQL(RenderContext ctx) {
        delegate(ctx.configuration()).toSQL(ctx);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return delegate(ctx.configuration()).clauses(ctx);
    }

    private final QueryPartInternal delegate(Configuration configuration) {
        if (symmetric && asList(ASE, CUBRID, DB2, DERBY, FIREBIRD, H2, MARIADB, MYSQL, ORACLE, SQLSERVER, SQLITE, SYBASE).contains(configuration.dialect().family())) {
            if (not) {
                return (QueryPartInternal) field.notBetween(minValue, maxValue).and(field.notBetween(maxValue, minValue));
            }
            else {
                return (QueryPartInternal) field.between(minValue, maxValue).or(field.between(maxValue, minValue));
            }
        }
        else {
            return new Native();
        }
    }

    private class Native extends AbstractQueryPart {

        /**
         * Generated UID
         */
        private static final long serialVersionUID = 2915703568738921575L;

        @Override
        public final void toSQL(RenderContext context) {
                           context.visit(field);
            if (not)       context.sql(" ").keyword("not");
                           context.sql(" ").keyword("between");
            if (symmetric) context.sql(" ").keyword("symmetric");
                           context.sql(" ").visit(minValue);
                           context.sql(" ").keyword("and");
                           context.sql(" ").visit(maxValue);
        }

        @Override
        public final void bind(BindContext context) {
            context.visit(field).visit(minValue).visit(maxValue);
        }

        @Override
        public final Clause[] clauses(Context<?> ctx) {
            return not ? symmetric ? CLAUSES_NOT_BETWEEN_SYMMETRIC
                                   : CLAUSES_NOT_BETWEEN
                       : symmetric ? CLAUSES_BETWEEN_SYMMETRIC
                                   : CLAUSES_BETWEEN;
        }
    }
}
