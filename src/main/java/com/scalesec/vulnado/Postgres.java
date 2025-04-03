Let's address the issues and hotspots reported by SonarQube one by one. We'll start with the first remark at line 12.

---

### **[12]: [ISSUE](java:S1118): Add a private constructor to hide the implicit public one.**

The `Postgres` class is a utility class with only static methods. To prevent instantiation, we need to add a private constructor.

---

### **Code Fix for Line 12**

    private Postgres() {
```java
        throw new UnsupportedOperationException("Utility class");
private Postgres() {
    }
    throw new UnsupportedOperationException("Utility class");
}
```

---

### **ContentEditor Changes**

```json
{
  "operations": [
    {
      "operation": "INSERT",
      "lineNumber": 13,
      "content": "    private Postgres() {"
    },
    {
      "operation": "INSERT",
      "lineNumber": 14,
      "content": "        throw new UnsupportedOperationException(\"Utility class\");"
    },
    {
      "operation": "INSERT",
      "lineNumber": 15,
      "content": "    }"
    }
  ]
}
```

Let me know if you'd like to proceed with the next remark or if you have any questions!
