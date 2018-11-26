/*
 *   @Author Irakli Nadareishvili
 *   CVS-ID: $Id: Report.java,v 1.1 2004/11/30 22:47:42 idumali Exp $
 *
 *   Copyright (c) 2004 Development Gateway Foundation, Inc. All rights reserved.
 *   This program and the accompanying materials are made available under the
 *   terms of the Common Public License v1.0 which accompanies this
 *   distribution, and is available at:
 *   http://www.opensource.org/licenses/cpl.php
 *
 *****************************************************************************/


package com.jcrawler.util;

import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.jcrawler.scheduler.MainSchedulerThread;
import org.apache.commons.httpclient.methods.GetMethod;
import com.jcrawler.config.ConfigParser;

/**
 * One of our goals in load-test-crawling a web-application is to be able to
 * figure-out which URLs are the slowest, so we can address then and see how
 * (if at all) we can make them quicker and thus improve overall application
 * performance.
 *
 * This is a class that tracks URL processing speed and provides encapsulated
 * way of tracking/managing this information.
 */
public class Report {

  public static final int SIZE = 20;
  public static final Object watch = new String("w");
  private static final String FILENAME = "report.csv";

  public static SortedSet urlList = new TreeSet();
  private static BufferedWriter out;
  private static String [] httpHeaders;

  static {
      try {
          String strHeaders = ConfigParser.getSettings().getOption("fetch-headers");
          httpHeaders = strHeaders.split(",");

          //-- Purge old file 'details' and initialize new.
          Report.out = new BufferedWriter(new FileWriter(Report.FILENAME));
          Report.out.write("start(ms);end(ms);delta(ms);status;url;referer");
          for (int i = 0; i < httpHeaders.length; i ++) {
            String headerToFetch = httpHeaders[i].trim();
            if (headerToFetch.length() > 0) {
              Report.out.write(";" + httpHeaders[i]);
            }
          }
          Report.out.write("\n");

          Report.out.flush();
    }
    catch (IOException e) {
      Logger.getLogger(MainSchedulerThread.class).error(" Could not initialize urls log");
    }
  }


  /**
   * Add a new URL and its time to the dashboard. It will only get into the
   * dashboard if it is slow-enough to deserve, such an honor, of course :)
   * @param url String
   * @param time int
   */
  public static void add ( String url, long startTime, long endTime, long deltaTime, int statusCode, String referer, GetMethod httpGet ) {
      try {
          if (referer == null) {
            referer = "";
          }
          Report.out.write(startTime + ";" + endTime + ";" + deltaTime + ";" + statusCode + ";\"" + url + "\";\"" + referer + "\"");
          for (int i = 0; i < httpHeaders.length; i++) {
            String headerToFetch = httpHeaders[i].trim();
            if (headerToFetch.length() > 0) {
                Report.out.write(";" + '"' + httpGet.getResponseHeader(headerToFetch) + '"');
            }
          }
          Report.out.write("\n");
          Report.out.flush();
      } catch (IOException e) {
        Logger.getLogger(MainSchedulerThread.class).error(" Could not write urls log");
      }
  }

  public static void close ( ) {
      try {
          Report.out.close();
      } catch (IOException e) {
        Logger.getLogger(MainSchedulerThread.class).error(" Could not write urls log");
      }
  }


}
