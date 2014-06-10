/*
 * Copyright (C) 2003-2014 eXo Platform SAS.
 *
 * This file is part of eXo Platform - Add-ons Manager.
 *
 * eXo Platform - Add-ons Manager is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * eXo Platform - Add-ons Manager software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with eXo Platform - Add-ons Manager; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.platform.am.settings

import org.exoplatform.platform.am.utils.AddonsManagerException
import org.exoplatform.platform.am.utils.FileUtils
import org.exoplatform.platform.am.utils.Logging

/**
 * This class exposes environment settings about the Add-ons Manager, the PLF server, the system, ...
 */
class EnvironmentSettings {
  private PlatformSettings _platformSettings
  private AddonsManagerSettings _managerSettings
  private File _addonsDirectory
  private File _archivesDirectory
  private File _statusesDirectory
  private File _localAddonsCatalogFile

  EnvironmentSettings(AddonsManagerSettings managerSettings, PlatformSettings platformSettings) {
    this._managerSettings = managerSettings
    this._platformSettings = platformSettings
    // Let's validate few things
    validate()
  }

  /**
   * Returns the path where add-ons are stored
   * @return a directory path
   */
  File getAddonsDirectory() {
    if (!this._addonsDirectory) {
      this._addonsDirectory = new File(this._platformSettings.homeDirectory, this._managerSettings.addonsDirectoryPath)
      if (!this._addonsDirectory.exists()) {
        FileUtils.mkdirs(this._addonsDirectory)
      }
    }
    return this._addonsDirectory
  }

  /**
   * Returns the path where add-ons archives are stored
   * @return a directory path
   */
  File getArchivesDirectory() {
    if (!this._archivesDirectory) {
      this._archivesDirectory = new File(addonsDirectory, this._managerSettings.archivesDirectoryName)
      if (!this._archivesDirectory.exists()) {
        FileUtils.mkdirs(this._archivesDirectory)
      }
    }
    return this._archivesDirectory
  }

  /**
   * Returns the path where add-ons statuses are stored
   * @return a directory path
   */
  File getStatusesDirectory() {
    if (!this._statusesDirectory) {
      this._statusesDirectory = new File(addonsDirectory, _managerSettings.archivesDirectoryName)
      if (!_statusesDirectory.exists()) {
        FileUtils.mkdirs(_statusesDirectory)
      }
    }
    return _statusesDirectory
  }

  /**
   * Returns the path to the local catalog
   * @return a file path
   */
  File getLocalAddonsCatalogFile() {
    if (!this._localAddonsCatalogFile) {
      this._localAddonsCatalogFile = new File(addonsDirectory, _managerSettings.localAddonsCatalogFilename)
    }
    return this._localAddonsCatalogFile
  }

  /**
   * Returns the content of the local catalog
   * @return a JSON formatted text
   */
  String getLocalAddonsCatalog() {
    return localAddonsCatalogFile.text
  }

  /**
   * Returns the URL to the remote catalog
   * @return the URL of the central catalog
   */
  URL getCentralCatalogUrl() {
    return new URL(_managerSettings.centralCatalogUrl)
  }

  /**
   * Returns the content of the remote catalog
   * @return a JSON formatted text
   */
  String getCentralCatalog() {
    return centralCatalogUrl.text
  }

  private void validate() {
    if (!addonsDirectory.isDirectory()) {
      throw new AddonsManagerException("Erroneous setup, add-ons directory (${addonsDirectory}) is invalid.")
    }
  }

  void describe() {
    Logging.displayMsgVerbose(
        "Environment Settings :\n${this.properties.sort { it.key }.collect { it }.findAll { !['class', '_platformSettings', '_managerSettings', 'commandLineArgs', 'centralCatalog', 'localAddonsCatalog'].contains(it.key) }.join('\n')}\n")
  }

}
