---
title: "How to use EVL"
linkTitle: "How to use EVL"
description: "Get the links to integrate EVL in your projects"
weight: 2
---

## Get the library

The library is deployed on [Maven central](https://mvnrepository.com/artifact/eu.daproject.evl/evl/1.0.1).
The library code is hosted on [Github](https://github.com/ylegoc/evl).  

To use it with Maven:  

```xml
<!-- https://mvnrepository.com/artifact/eu.daproject.evl/evl -->
<dependency>
    <groupId>eu.daproject.evl</groupId>
    <artifactId>evl</artifactId>
    <version>1.0.1</version>
</dependency>
```

Other build tools are available on [Maven central](https://mvnrepository.com/artifact/eu.daproject.evl/evl/1.0.1).  
The code is compiled using Java release 11.

## Use the library as module

The library defines a Java 9 module. Simply add to your *module-info.java*:

```java
requires eu.daproject.evl;
```

## And now?

Depending on your patience, you can start to by reading the [concepts](/docs/the-multimethods.html) or directly jump to the [examples](/docs/examples.html).
You can also access the [javadoc](/apidocs/index.html).

