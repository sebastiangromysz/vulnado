To address the remarks provided by SonarQube, we will make the necessary changes to the code. Below is the analysis and the corresponding fixes for each remark:

---

### **[13]: [ISSUE](java:S1118)**
- **Problem**: The class `LinkLister` has an implicit public constructor, which is unnecessary since it only contains static methods.
- **Solution**: Add a private constructor to prevent instantiation of the class.

---

### **[15]: [ISSUE](java:S2293)**
- **Problem**: The type specification in the constructor call `new ArrayList<String>()` can be replaced with the diamond operator (`<>`) to simplify the code.
- **Solution**: Replace `new ArrayList<String>()` with `new ArrayList<>()`.

---

### **[28]: [ISSUE](java:S106)**
- **Problem**: The use of `System.out.println` is not recommended. A logger should be used instead.
- **Solution**: Replace `System.out.println` with a logger. If no logger is declared, add one.

---

### **Code Changes**

Here are the changes to address the issues:

```java
ContentEditor({
  "operations": [
    {
      "operation": "INSERT",
      "lineNumber": 12,
      "content": "  private LinkLister() {"
    },
    {
      "operation": "INSERT",
      "lineNumber": 13,
      "content": "    // Prevent instantiation"
    },
    {
      "operation": "INSERT",
      "lineNumber": 14,
      "content": "  }"
    },
    {
      "operation": "REPLACE",
      "lineNumber": 15,
      "content": "    List<String> result = new ArrayList<>();"
    },
    {
      "operation": "INSERT",
      "lineNumber": 10,
      "content": "import java.util.logging.Logger;"
    },
    {
      "operation": "INSERT",
      "lineNumber": 12,
      "content": "  private static final Logger LOGGER = Logger.getLogger(LinkLister.class.getName());"
    },
    {
      "operation": "REPLACE",
      "lineNumber": 28,
      "content": "      LOGGER.info(host);"
    }
  ]
})
```

---

### **Explanation of Changes**
1. **[13]**: Added a private constructor to prevent instantiation of the `LinkLister` class.
2. **[15]**: Replaced `new ArrayList<String>()` with `new ArrayList<>()` to use the diamond operator.
3. **[28]**: Replaced `System.out.println` with a logger. Added the necessary import and logger declaration.

---

### **Final Code After Fixes**
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
      URL aUrl= new URL(url);
      String host = aUrl.getHost();
      LOGGER.info(host);
      if (host.startsWith("172.") || host.startsWith("192.168") || host.startsWith("10.")){
        throw new BadRequest("Use of Private IP");
      } else {
        return getLinks(url);
      }
    } catch(Exception e) {
      throw new BadRequest(e.getMessage());
    }
  }
}
```

---

### **Summary**
- Added a private constructor to `LinkLister` to prevent instantiation.
- Simplified the `ArrayList` instantiation using the diamond operator.
- Replaced `System.out.println` with a logger and added the necessary import and declaration.

Let me know if you need further assistance!
