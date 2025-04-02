Let's address the issues and hotspots reported by SonarQube one by one, starting with [12].

---

### [12]: 
- **[ISSUE](java:S1118)**: Add a private constructor to hide the implicit public one.

#### Analysis:
The `Postgres` class is a utility class, and utility classes should not be instantiated. Adding a private constructor will prevent instantiation.

#### Fix:
Add a private constructor to the `Postgres` class.

#### ContentEditor:
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

---

### [16]: 
- **[ISSUE](java:S4925)**: Remove this "Class.forName()", it is useless.

#### Analysis:
The `Class.forName()` method is unnecessary because modern JDBC drivers are automatically loaded. This line can be safely removed.

#### Fix:
Remove the line containing `Class.forName("org.postgresql.Driver");`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "DELETE",
      "lineNumber": 16
    }
  ]
}
```

---

### [25]: 
- **[HOTSPOT](java:S4507)**: Make sure this debug feature is deactivated before delivering the code in production.

#### Analysis:
The `e.printStackTrace()` is a debug feature and should not be used in production. Replace it with proper logging.

#### Fix:
Replace `e.printStackTrace()` with a logger.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 25,
      "content": "            LOGGER.error(\"Exception occurred\", e);"
    },
    {
      "operation": "INSERT",
      "lineNumber": 3,
      "content": "import java.util.logging.Logger;"
    },
    {
      "operation": "INSERT",
      "lineNumber": 11,
      "content": "    private static final Logger LOGGER = Logger.getLogger(Postgres.class.getName());"
    }
  ]
}
```

---

### [26]: 
- **[ISSUE](java:S106)**: Replace this use of `System.err` by a logger.

#### Analysis:
Replace `System.err.println` with proper logging using the `LOGGER`.

#### Fix:
Replace `System.err.println` with `LOGGER.error`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 26,
      "content": "            LOGGER.severe(e.getClass().getName() + \": \" + e.getMessage());"
    }
  ]
}
```

---

### [33]: 
- **[ISSUE](java:S106)**: Replace this use of `System.out` by a logger.

#### Analysis:
Replace `System.out.println` with proper logging using the `LOGGER`.

#### Fix:
Replace `System.out.println` with `LOGGER.info`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 33,
      "content": "            LOGGER.info(\"Setting up Database...\");"
    }
  ]
}
```

---

### [35]: 
- **[ISSUE](java:S2095)**: Use try-with-resources or close this `Statement` in a `finally` clause.

#### Analysis:
The `Statement` object should be closed properly to avoid resource leaks. Use try-with-resources.

#### Fix:
Refactor the code to use try-with-resources for the `Statement`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 35,
      "content": "            try (Statement stmt = c.createStatement()) {"
    },
    {
      "operation": "INSERT",
      "lineNumber": 54,
      "content": "            }"
    }
  ]
}
```

---

### [56]: 
- **[ISSUE](java:S106)**: Replace this use of `System.out` by a logger.

#### Analysis:
Replace `System.out.println` with proper logging using the `LOGGER`.

#### Fix:
Replace `System.out.println` with `LOGGER.error`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 56,
      "content": "            LOGGER.severe(e.getMessage());"
    }
  ]
}
```

---

### [67]: 
- **[HOTSPOT](java:S4790)**: Make sure this weak hash algorithm is not used in a sensitive context here.

#### Analysis:
MD5 is considered a weak hash algorithm. Replace it with a stronger algorithm like SHA-256.

#### Fix:
Replace `MessageDigest.getInstance("MD5")` with `MessageDigest.getInstance("SHA-256")`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 67,
      "content": "            MessageDigest md = MessageDigest.getInstance(\"SHA-256\");"
    }
  ]
}
```

---

### [79]: 
- **[ISSUE](java:S1643)**: Use a `StringBuilder` instead.

#### Analysis:
Replace the concatenation with `StringBuilder`.

#### Fix:
Refactor the code to use `StringBuilder`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 79,
      "content": "                hashtext = new StringBuilder(\"0\").append(hashtext).toString();"
    }
  ]
}
```

---

### [86]: 
- **[ISSUE](java:S112)**: Define and throw a dedicated exception instead of using a generic one.

#### Analysis:
Replace `RuntimeException` with a custom exception.

#### Fix:
Define a custom exception and use it.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 86,
      "content": "            throw new HashingException(\"Error while hashing\", e);"
    },
    {
      "operation": "INSERT",
      "lineNumber": 117,
      "content": "class HashingException extends Exception {"
    },
    {
      "operation": "INSERT",
      "lineNumber": 118,
      "content": "    public HashingException(String message, Throwable cause) {"
    },
    {
      "operation": "INSERT",
      "lineNumber": 119,
      "content": "        super(message, cause);"
    },
    {
      "operation": "INSERT",
      "lineNumber": 120,
      "content": "    }"
    },
    {
      "operation": "INSERT",
      "lineNumber": 121,
      "content": "}"
    }
  ]
}
```

---

### [94]: 
- **[ISSUE](java:S2095)**: Use try-with-resources or close this `PreparedStatement` in a `finally` clause.

#### Analysis:
Refactor the code to use try-with-resources for the `PreparedStatement`.

#### Fix:
Use try-with-resources for `PreparedStatement`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 92,
      "content": "       try (PreparedStatement pStatement = connection().prepareStatement(sql)) {"
    },
    {
      "operation": "DELETE",
      "lineNumber": 94
    }
  ]
}
```

---

### [100]: 
- **[HOTSPOT](java:S4507)**: Make sure this debug feature is deactivated before delivering the code in production.

#### Analysis:
Replace `e.printStackTrace()` with proper logging.

#### Fix:
Replace `e.printStackTrace()` with `LOGGER.error`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 100,
      "content": "         LOGGER.error(\"Exception occurred\", e);"
    }
  ]
}
```

---

### [108]: 
- **[ISSUE](java:S2095)**: Use try-with-resources or close this `PreparedStatement` in a `finally` clause.

#### Analysis:
Refactor the code to use try-with-resources for the `PreparedStatement`.

#### Fix:
Use try-with-resources for `PreparedStatement`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 106,
      "content": "        try (PreparedStatement pStatement = connection().prepareStatement(sql)) {"
    },
    {
      "operation": "DELETE",
      "lineNumber": 108
    }
  ]
}
```

---

### [114]: 
- **[HOTSPOT](java:S4507)**: Make sure this debug feature is deactivated before delivering the code in production.

#### Analysis:
Replace `e.printStackTrace()` with proper logging.

#### Fix:
Replace `e.printStackTrace()` with `LOGGER.error`.

#### ContentEditor:
```json
{
  "operations": [
    {
      "operation": "REPLACE",
      "lineNumber": 114,
      "content": "            LOGGER.error(\"Exception occurred\", e);"
    }
  ]
}
```
