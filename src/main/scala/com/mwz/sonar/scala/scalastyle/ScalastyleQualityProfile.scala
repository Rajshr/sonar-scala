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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.mwz.sonar.scala
package scalastyle

import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition.NewBuiltInQualityProfile

/**
 * Defines a Scalastyle quality profile.
 */
final class ScalastyleQualityProfile extends BuiltInQualityProfilesDefinition {
  override def define(context: BuiltInQualityProfilesDefinition.Context): Unit = {
    // Create an empty profile.
    val profile = context.createBuiltInQualityProfile(ScalastyleQualityProfile.ProfileName, Scala.LanguageKey)

    // Ensure this is not the default profile.
    profile.setDefault(false)

    // Activate all rules in the Scalastyle rules repository.
    // (except for those which were not included in the repository)
    ScalastyleQualityProfile.activateAllRules(profile)

    // Save the profile.
    profile.done()
  }
}

object ScalastyleQualityProfile {
  private[scalastyle] final val ProfileName = "Scalastyle"

  /** Activates all rules in the Scalastyle rules repository in the given profile */
  def activateAllRules(profile: BuiltInQualityProfilesDefinition.NewBuiltInQualityProfile): Unit = {
    ScalastyleInspections.AllInspections
      .filterNot(i => ScalastyleRulesRepository.SkipTemplateInstances.contains(i.id))
      .foreach(i => profile.activateRule(ScalastyleRulesRepository.RepositoryKey, i.clazz))
  }
}
