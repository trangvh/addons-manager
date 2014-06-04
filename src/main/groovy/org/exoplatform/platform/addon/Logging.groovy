/**
 * Copyright (C) 2003-2014 eXo Platform SAS.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.exoplatform.platform.addon

import org.fusesource.jansi.AnsiConsole

import static org.fusesource.jansi.Ansi.ansi

class Logging {
  def static final CONSOLE_WIDTH = 120
  def static boolean verbose

  static initialize() {
    AnsiConsole.systemInstall()
  }

  static dispose() {
    AnsiConsole.systemUninstall();
  }

  static displayHeader(String managerVersion) {
    println(ansi().render("""
    @|yellow               xx      xx |@
    @|yellow                xx    xx  |@
    @|yellow    eeeeeee      xx  xx  |@    ooooooo
    @|yellow  ee       ee     xxxx   |@  oo       @|yellow oo  |@
    @|yellow eeeeeeeeeeeee    xxxx   |@ oo        @|yellow  oo |@
    @|yellow ee              xx  xx  |@ oo        @|yellow  oo |@
    @|yellow  ee       ee   xx    xx |@  oo       @|yellow oo  |@
    @|yellow    eeeeeee    xx      xx    ooooooo |@           Add-ons Manager v @|yellow ${managerVersion} |@
    """).toString())
  }

  static logWithStatus(String text, Closure closure, Object... args) {
    try {
      displayMsg(text)
      def result = closure.call(args)
      displayStatusOK()
      return result
    } catch (Exception e) {
      displayStatusKO()
      throw e
    }
  }

  static logWithStatusOK(String text) {
    displayMsg(text)
    displayStatusOK()
  }

  static logWithStatusKO(String text) {
    displayMsg(text)
    displayStatusKO()
  }

  static displayException(Exception e) {
    displayMsgError("Error : " + e.getMessage() + " <" + e.getClass() + ">")
    if (verbose) {
      e.printStackTrace()
    }
  }

  static displayMsgInfo(String text) {
    printf(ansi().render('+ %s\n').toString(), ansi().render(text).toString())
  }

  static displayMsgError(String text) {
    printf(ansi().render('@|red,bold - %s|@\n').toString(), ansi().render(text).toString())
  }

  static displayMsgWarn(String text) {
    printf(ansi().render('@|yellow ! %s|@\n').toString(), ansi().render(text).toString())
  }

  static displayMsgVerbose(String text) {
    if (verbose) {
      printf(ansi().render('@|cyan . %s|@\n').toString(), ansi().render(text).toString())
    }
  }

  private static displayMsg(String text) {
    printf(ansi().render('+ %-' + (CONSOLE_WIDTH - 8) + 's ').toString(), ansi().render(text).toString())
  }

  private static displayStatusOK() {
    println ansi().render(' [@|green OK|@]').toString()
  }

  private static displayStatusKO() {
    println ansi().render(' [@|red KO|@]').toString()
  }

  private static displayStatusKO(Exception e) {
    displayStatusKO()
    displayException(e)
  }

}