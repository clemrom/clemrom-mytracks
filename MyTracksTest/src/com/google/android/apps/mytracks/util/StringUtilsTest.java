/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.android.apps.mytracks.util;

import java.util.GregorianCalendar;
import java.util.TimeZone;

import junit.framework.TestCase;

/**
 * Tests for {@link StringUtils}.
 *
 * @author Rodrigo Damazio
 */
public class StringUtilsTest extends TestCase {
  public void testParseXmlDateTime() {
    assertParseXmlDateTime("2010-05-04T03:02:01",
        2010, 5, 4, 3, 2, 1, 0);
  }

  public void testParseXmlDateTime_fractional() {
    assertParseXmlDateTime("2010-05-04T03:02:01.3",
        2010, 5, 4, 3, 2, 1, 300);
    assertParseXmlDateTime("2010-05-04T03:02:01.35",
        2010, 5, 4, 3, 2, 1, 350);
    assertParseXmlDateTime("2010-05-04T03:02:01.352",
        2010, 5, 4, 3, 2, 1, 352);
    assertParseXmlDateTime("2010-05-04T03:02:01.3525",
        2010, 5, 4, 3, 2, 1, 352);
  }

  public void testParseXmlDateTime_timezone() {
    assertParseXmlDateTime("2010-05-04T03:02:01Z",
        2010, 5, 4, 3, 2, 1, 0);
    assertParseXmlDateTime("2010-05-04T03:02:01+00:00",
        2010, 5, 4, 3, 2, 1, 0);
    assertParseXmlDateTime("2010-05-04T03:02:01-00:00",
        2010, 5, 4, 3, 2, 1, 0);
    assertParseXmlDateTime("2010-05-04T03:02:01+01:00",
        2010, 5, 4, 2, 2, 1, 0);
    assertParseXmlDateTime("2010-05-04T03:02:01+10:30",
        2010, 5, 3, 16, 32, 1, 0);
    assertParseXmlDateTime("2010-05-04T03:02:01-09:30",
        2010, 5, 4, 12, 32, 1, 0);
    assertParseXmlDateTime("2010-05-04T03:02:01-05:00",
        2010, 5, 4, 8, 2, 1, 0);
  }

  public void testParseXmlDateTime_fractionalAndTimezone() {
    assertParseXmlDateTime("2010-05-04T03:02:01.352Z",
        2010, 5, 4, 3, 2, 1, 352);
    assertParseXmlDateTime("2010-05-04T03:02:01.47+00:00",
        2010, 5, 4, 3, 2, 1, 470);
    assertParseXmlDateTime("2010-05-04T03:02:01.5791+03:00",
        2010, 5, 4, 0, 2, 1, 579);
    assertParseXmlDateTime("2010-05-04T03:02:01.8-05:30",
        2010, 5, 4, 8, 32, 1, 800);
  }

  private void assertParseXmlDateTime(String dateTime,
      int year, int month, int day, int hour, int min, int second, int millis) {
    long timestamp = StringUtils.parseXmlDateTime(dateTime);
    GregorianCalendar calendar =
        new GregorianCalendar(TimeZone.getTimeZone("UTC"));
    calendar.set(year, month - 1, day, hour, min, second);
    calendar.set(GregorianCalendar.MILLISECOND, millis);
    assertEquals(calendar.getTimeInMillis(), timestamp);
  }
}
