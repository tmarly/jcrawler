# JCrawler (load-testing tool)

This is a fork from http://jcrawler.sourceforge.net, with some improvements.

This is a load-testing tool, wich is different from usual similar tools mainly for these reasons:
- You don't have to define the URL list. It will crawl all links (however you can define inclusion / exclusion patterns)
- manage cookies
- The load schema: you only have to define the number of milliseconds between each request

Improvements added to the original tool:
- URL patterns: allows more complex rules. Cf. conf/crawlerConfig.xml
- Display HTTP status code

More information on the original JCrawler site.

## Installation

You need Java >= 1.5 + Ant, or you can use Docker to build the project.

**With Java + Ant**

```bash
git clone git@github.com:tmarly/jcrawler.git
cd jcrawler
ant build
``` 

**With Docker**

```bash
git clone git@github.com:tmarly/jcrawler.git
cd jcrawler
docker run --rm -v $PWD:/root frekele/ant ant build
```

**Configuration**

Both of the methods described above will create a new folder, 'dist', containing the built JCrawler.

Edit dist/conf/crawlerConfig.xml to customize settings for your need.

You can adjust logging settings in dist/log4j.properties

Then run the crawler:
```bash
cd dist
chmod +x run.sh
./run.sh
```

To stop it, just press ENTER

It will produce output to:
- console
- file monitor.log (contains speed + slowest URLs)
- file crawler.log (if the 'file' is enabled in log4j.properties)

@TODO - final report with URLs, timing, and status code, in CSV for example.