/*
 * Sonar Scala Plugin
 * Copyright (C) 2018 All contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.mwz.sonar.scala

import com.mwz.sonar.scala.scapegoat.{
  ScapegoatQualityProfile,
  ScapegoatReportParser,
  ScapegoatRulesRepository,
  ScapegoatSensor
}
import com.mwz.sonar.scala.scoverage.{ScoverageMetrics, ScoverageReportParser, ScoverageSensor}
import com.mwz.sonar.scala.sensor.ScalaSensor
import com.ncredinburgh.sonar.scalastyle.{ScalastyleQualityProfile, ScalastyleRepository, ScalastyleSensor}
import org.scalatest.{FlatSpec, Matchers}
import org.sonar.api.internal.SonarRuntimeImpl
import org.sonar.api.utils.Version
import org.sonar.api.{Plugin, SonarQubeSide, SonarRuntime}

/** Tests the Scala SonarQube plugin extension points */
class ScalaPluginSpec extends FlatSpec with Matchers {
  val runtime: SonarRuntime = SonarRuntimeImpl.forSonarQube(Version.create(6, 7), SonarQubeSide.SCANNER)
  val context = new Plugin.Context(runtime)
  new ScalaPlugin().define(context)
  behavior of "the scala plugin"

  it should "provide scala sensor" in {
    assert(context.getExtensions.contains(classOf[Scala]))
    assert(context.getExtensions.contains(classOf[ScalaSensor]))
  }

  it should "provide scalastyle sensor" in {
    assert(context.getExtensions.contains(classOf[ScalastyleRepository]))
    assert(context.getExtensions.contains(classOf[ScalastyleQualityProfile]))
    assert(context.getExtensions.contains(classOf[ScalastyleSensor]))
  }

  it should "provide scoverage sensor" in {
    assert(context.getExtensions.contains(classOf[ScoverageMetrics]))
    assert(context.getExtensions.contains(classOf[ScoverageReportParser]))
    assert(context.getExtensions.contains(classOf[ScoverageSensor]))
  }

  it should "provide scapegoat sensor" in {
    assert(context.getExtensions.contains(classOf[ScapegoatRulesRepository]))
    assert(context.getExtensions.contains(classOf[ScapegoatQualityProfile]))
    assert(context.getExtensions.contains(classOf[ScapegoatReportParser]))
    assert(context.getExtensions.contains(classOf[ScapegoatSensor]))
  }
}
