/*
 *   @Author Irakli Nadareishvili
 *   CVS-ID: $Id: FetcherTask.java,v 1.2 2005/02/03 01:43:52 idumali Exp $
 *
 *   Copyright (c) 2004 Development Gateway Foundation, Inc. All rights reserved.
 *   This program and the accompanying materials are made available under the
 *   terms of the Common Public License v1.0 which accompanies this
 *   distribution, and is available at:
 *   http://www.opensource.org/licenses/cpl.php
 *
 *****************************************************************************/

package com.jcrawler.scheduler;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import com.jcrawler.UrlFetchException;
import com.jcrawler.UrlFetcher;
import com.jcrawler.util.Crawler;
import com.jcrawler.util.UrlAndReferer;

public class FetcherTask
    extends Thread {

  private static Logger log = Logger.getLogger(FetcherTask.class);

  public FetcherTask() {
  }

  public void run() {
    log.debug("Starting FetcherTask");
    Crawler.numOfActiveThreads++;

    // If there is anything to process, do process
    UrlAndReferer urlAndReferer = null;
    String urlString = null;
    boolean nextUrlFound = false;
    while(nextUrlFound == false) {

        // no more element ?
        if (Crawler.rawURLs.length() == 0) {
            // then exit
            break;
        }

        urlAndReferer = (UrlAndReferer) Crawler.rawURLs.get();
        urlString = urlAndReferer.url; 

        // manage specific case
        if (urlString == null) {
          continue;
        }

        // by the time we reach this point, may be this URL has already been fetched.
        if (Crawler.getUrls().contains(urlString)) {
          continue;
        }

        // if we're here, then all is ok.
        nextUrlFound = true;
    }

    if (nextUrlFound == true) {
      log.debug("Begin processing URL: " + urlString);

      String resultedHTML = null;
      try {
        resultedHTML = UrlFetcher.fetch(urlString, urlAndReferer.referer);
      }
      catch (UrlFetchException ex1) {
        log.warn("Could not fetch URL: " + urlString +  " \n" + ex1.getMessage());
      }

      if (resultedHTML == null) {
        log.warn("resultedHTML is null for url " + urlString);
        Crawler.numOfActiveThreads--;
        return;
      }

      Set urls = UrlFetcher.parse(urlString, resultedHTML);
      if (urls == null) {
        log.debug(
            "Parse result is null (no links from this page to another) for url " +
            urlString);
        Crawler.numOfActiveThreads--;
        return;
      }
      log.debug("Found " + urls.size() + " URLs when processing " + urlString);
      Iterator it = urls.iterator();
      int no;
      Set processedUrls = null;
      while (it.hasNext()) {
        String currUrl = (String) it.next();
        no = -1;
        try {

          processedUrls = Crawler.getUrls();

          if (! (processedUrls.contains(currUrl) || currUrl == null)) {
            synchronized (Crawler.watch) {
              Crawler.rawURLs.put(new UrlAndReferer(currUrl, urlString));
            }
          }
        }
        catch (Exception ex) {
          log.error("Error fetching URL: " + urlString + " : " + currUrl + "," +
                    no, ex);
          Crawler.numOfActiveThreads--;
          return;
        }
      }


    } else {
      log.debug("No URLs left in crawler");
    }
    log.debug("Stopping FetcherTask");
    Crawler.numOfActiveThreads--;
  }
}
