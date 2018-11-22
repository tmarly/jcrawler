# JCrawler (load-testing tool)

This is a fork from http://jcrawler.sourceforge.net/

This is a load-testing tool, wich is different from usual similar tools mainly for 2 reasons:
- You don't have to define the URL list. It will crawl all links (however you can define inclusion / exclusion patterns)
- The load schema: you only have to define the number of milliseconds between each request

More information on the original JCrawler site.

## Installation

You need Java >= 1.5 + Ant, or you can use Docker to build the project.

**With Java + Ant**

```bash
ant build
``` 

**With Docker**

```bash
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
