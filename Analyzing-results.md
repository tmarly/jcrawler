# Results analysis

Be sure to execute this program on a server where the bandwidth is large
enough, otherwise your results will be biased by the bandwidth limit.

Important note: If you set a very low interval between each request, JCrawler
may not be performant enough to send requests and parse result.

An interesting test:
- On the target server, add a HTTP header containing the request timing.
  On Nginx, this can be achieved with:
  add_headers X-Debug-Time request_time;
- In JCrawler config, add this option:
  <option name="fetch-headers">X-Debug-Time</option>
- Then run your test.
- Check in the report.csv the columns 'delta' and 'X-Debug-Time'
  The difference between the 2 values is due to the transfer time.
  If you see a very big difference, this may be due to bandwidth limit.
  