/*
 *   @Author Irakli Nadareishvili
 *   CVS-ID: $Id: Settings.java,v 1.1 2004/11/30 22:47:42 idumali Exp $
 *
 *   Copyright (c) 2004 Development Gateway Foundation, Inc. All rights reserved.
 *   This program and the accompanying materials are made available under the
 *   terms of the Common Public License v1.0 which accompanies this
 *   distribution, and is available at:
 *   http://www.opensource.org/licenses/cpl.php
 *
 *****************************************************************************/


package com.jcrawler.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class Settings {

  public static final boolean CRAWL_ALLOWED = true;
  public static final boolean CRAWL_DENIED = false;

  private int interval;
  private int monitorInterval;
  private int connectionTimeout;
  private Map headers;
  private Set crawlUrls;

  private boolean crawlDefaultPermission;
  private Set urlPatternFragments;

  public Settings() {

    // crawlDefaultPermission = Settings.CRAWL_DENIED;

    this.headers = new HashMap();
    this.urlPatternFragments = new HashSet();
    this.crawlUrls = new HashSet();

  }

  public boolean getCrawlDefaultPermission() {
    return crawlDefaultPermission;
  }

  /**
   * This method exists solely for unit-testing purposes.
   * @param permission boolean
   */
  public void setCrawlDefaultPermission( boolean permission ) {
    crawlDefaultPermission = permission;
  }


  public void addCrawlUrl(ParamMapEntry url) {
    this.crawlUrls.add(url.getKey());
  }

  public void addHeader(ParamMapEntry entry) {
    this.headers.put(entry.getKey(), entry.getValue());
  }

  public void addUrlPatternFragment(PatternFragment pattern) {
    this.urlPatternFragments.add(pattern);
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public Set getCrawlUrls() {
    return crawlUrls;
  }

  public Map getHeaders() {
    return headers;
  }

  public int getInterval() {
    return interval;
  }

  public int getMonitorInterval() {
    return monitorInterval;
  }

  public Set getUrlPatternFragments() {
    return urlPatternFragments;
  }

  public void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public void setCrawlUrls(Set crawlUrls) {
    this.crawlUrls = crawlUrls;
  }

  public void setHeaders(Map headers) {
    this.headers = headers;
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }

  public void setMonitorInterval(int interval) {
    this.monitorInterval = interval;
  }

  public void setUrlPatternFragments(Set urlPatternFragments) {
    this.urlPatternFragments = urlPatternFragments;
  }
}
