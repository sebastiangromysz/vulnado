To address the remarks from SonarQube, we will make the following changes:

### Analysis and Fixes:

#### [13]: 
- **[ISSUE](java:S1118)**: Add a private constructor to hide the implicit public one.
  - The `LinkLister` class is a utility class with only static methods. To prevent instantiation, we need to add a private constructor.

#### [15]: 
- **[ISSUE](java:S2293)**: Replace the type specification in this constructor call with the diamond operator ("<>").
  - The `new ArrayList<String>()` can be simplified to `new ArrayList<>()` using the diamond operator.

#### [28]: 
- **[ISSUE](java:S106)**: Replace this use of `System.out` by a logger.
  - Replace `System.out.println(host);` with a proper logging mechanism. Since no logger is currently declared, we will add one.

---

### Code Changes:

Here is the updated code with the necessary fixes:

```java
package com.scalesec.vulnado;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.*;
import java.util.logging.Logger;

public class LinkLister {
  private static final Logger LOGGER = Logger.getLogger(LinkLister.class.getName());

  private LinkLister() {
    // Private constructor to prevent instantiation
  }

  public static List<String> getLinks(String url) throws IOException {
    List<String> result = new ArrayList<>();
    Document doc = Jsoup.connect(url).get();
    Elements links = doc.select("a");
    for (Element link : links) {
      result.add(link.absUrl("href"));
    }
    return result;
  }

  public static List<String> getLinksV2(String url) throws BadRequest {
    try {
      URL aUrl = new URL(url);
      String host = aUrl.getHost();
      LOGGER.info(host);
      if (host.startsWith("172.") || host.startsWith("192.168") || host.startsWith("10.")) {
        throw new BadRequest("Use of Private IP");
      } else {
        return getLinks(url);
      }
    } catch (Exception e) {
      throw new BadRequest(e.getMessage());
    }
  }
}
```

---

### Explanation of Changes:
1. **[13]**: Added a private constructor to the `LinkLister` class to prevent instantiation.
2. **[15]**: Replaced `new ArrayList<String>()` with `new ArrayList<>()` to use the diamond operator.
3. **[28]**: 
   - Added a `Logger` instance (`LOGGER`) to the class.
   - Replaced `System.out.println(host);` with `LOGGER.info(host);`.

---

### ContentEditor Operations:

```json
{
  "operations": [
    {
      "operation": "INSERT",
      "lineNumber": 10,
      "content": "import java.util.logging.Logger;"
    },
    {
      "operation": "INSERT",
      "lineNumber": 12,
      "content": "private static final Logger LOGGER = Logger.getLogger(LinkLister.class.getName());"
    },
    {
      "operation": "INSERT",
      "lineNumber": 14,
      "content": "private LinkLister() {"
    },
    {
      "operation": "INSERT",
      "lineNumber": 15,
      "content": "// Private constructor to prevent instantiation"
    },
    {
      "operation": "INSERT",
      "lineNumber": 16,
      "content": "}"
    },
    {
      "operation": "REPLACE",
      "lineNumber": 15,
      "content": "List<String> result = new ArrayList<>();"
    },
    {
      "operation": "REPLACE",
      "lineNumber": 28,
      "content": "LOGGER.info(host);"
    }
  ]
}
```

---

### Summary:
- The `LinkLister` class is now a proper utility class with a private constructor.
- The diamond operator is used for type inference in the `ArrayList` instantiation.
- Logging is implemented using `java.util.logging.Logger` instead of `System.out.println`.
