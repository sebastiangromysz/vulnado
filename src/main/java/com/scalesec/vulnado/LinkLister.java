Let's address the remarks one by one and apply the necessary fixes using the `ContentEditor`.

---

### [13]: 
- **[ISSUE](java:S1118)**: Add a private constructor to hide the implicit public one.

#### Analysis:
The class `LinkLister` does not have a private constructor, which means it has an implicit public one. Since this class is utility-like (only static methods), it should have a private constructor to prevent instantiation.

#### Fix:
Add a private constructor to the class.

---

### [15]: 
- **[ISSUE](java:S2293)**: Replace the type specification in this constructor call with the diamond operator ("<>").

#### Analysis:
The line `List<String> result = new ArrayList<String>();` uses explicit type specification in the constructor call. This can be replaced with the diamond operator (`<>`) for better readability and to adhere to modern Java conventions.

#### Fix:
Replace `new ArrayList<String>()` with `new ArrayList<>()`.

---

### [28]: 
- **[ISSUE](java:S106)**: Replace this use of `System.out` by a logger.

#### Analysis:
The line `System.out.println(host);` uses `System.out` for logging, which is not recommended for production code. Replace it with a proper logger.

#### Fix:
Introduce a logger and replace `System.out.println(host)` with a logging statement.

---

### ContentEditor Fixes:

```json
{
  "operations": [
    {
      "operation": "INSERT",
      "lineNumber": 12,
      "content": "import java.util.logging.Logger;"
    },
    {
      "operation": "INSERT",
      "lineNumber": 13,
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
      "content": "  // Prevent instantiation"
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

### Final Code After Fixes:

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
    // Prevent instantiation
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
1. **Private Constructor**: Added a private constructor to prevent instantiation of the utility class.
2. **Diamond Operator**: Replaced explicit type specification in `new ArrayList<String>()` with `new ArrayList<>()`.
3. **Logger**: Introduced a logger and replaced `System.out.println(host)` with `LOGGER.info(host)`.

These changes address all the remarks while ensuring the code remains clean, readable, and functional.
