/*
 * Copyright (c) 2002-2017 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package org.neo4j.cypher.internal.frontend.v3_3.ast.functions

import org.neo4j.cypher.internal.frontend.v3_3.ast.Function
import org.neo4j.cypher.internal.frontend.v3_3.{SemanticCheck, SemanticError, ast}

// this implementation exists only to handle the case where "reduce(x = 0, x in y : foo)" is parsed as a function invocation,
// rather than a ReduceExpression
case object Reduce extends Function {
  def name = "reduce"

  override def semanticCheckHook(ctx: ast.Expression.SemanticContext, invocation: ast.FunctionInvocation): SemanticCheck =
    semanticCheck(ctx, invocation)

  def semanticCheck(ctx: ast.Expression.SemanticContext, invocation: ast.FunctionInvocation): SemanticCheck =
    SemanticError(s"${name}(...) requires '| expression' (an accumulation expression)", invocation.position)
}
