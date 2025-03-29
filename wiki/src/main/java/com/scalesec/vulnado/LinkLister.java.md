# Documentation: `LinkLister.java`

## Overview
The `LinkLister` class provides functionality to extract hyperlinks from a given URL. It uses the `Jsoup` library for HTML parsing and includes methods to retrieve links with additional validation for private IP addresses.

---

## Class: `LinkLister`

### Purpose
The class is designed to fetch and process hyperlinks from a webpage. It includes two methods:
1. `getLinks`: Extracts all hyperlinks from a given URL.
2. `getLinksV2`: Adds validation to ensure the URL does not point to a private IP address before extracting hyperlinks.

---

## Methods

### `getLinks(String url)`
#### Description
Fetches all hyperlinks (`<a>` tags) from the provided URL and returns them as a list of absolute URLs.

#### Parameters
| Name | Type   | Description                     |
|------|--------|---------------------------------|
| `url` | `String` | The URL of the webpage to parse. |

#### Returns
| Type          | Description                          |
|---------------|--------------------------------------|
| `List<String>` | A list of absolute URLs extracted from the webpage. |

#### Exceptions
| Type          | Description                          |
|---------------|--------------------------------------|
| `IOException` | Thrown if there is an issue connecting to the URL or parsing the webpage. |

#### Logic
1. Connects to the given URL using `Jsoup.connect(url).get()`.
2. Selects all `<a>` elements using `doc.select("a")`.
3. Extracts the absolute URL of each hyperlink using `link.absUrl("href")`.
4. Adds the URLs to a result list and returns it.

---

### `getLinksV2(String url)`
#### Description
Fetches all hyperlinks from the provided URL, but first validates that the URL does not point to a private IP address. If the URL points to a private IP, a `BadRequest` exception is thrown.

#### Parameters
| Name | Type   | Description                     |
|------|--------|---------------------------------|
| `url` | `String` | The URL of the webpage to parse. |

#### Returns
| Type          | Description                          |
|---------------|--------------------------------------|
| `List<String>` | A list of absolute URLs extracted from the webpage. |

#### Exceptions
| Type          | Description                          |
|---------------|--------------------------------------|
| `BadRequest`  | Thrown if the URL points to a private IP or if any other error occurs during processing. |

#### Logic
1. Parses the URL using `new URL(url)` to extract the host.
2. Checks if the host starts with private IP prefixes (`172.`, `192.168`, or `10.`).
   - If true, throws a `BadRequest` exception with the message "Use of Private IP".
3. If the host is valid, calls `getLinks(url)` to fetch the hyperlinks.
4. Catches any exceptions and rethrows them as `BadRequest`.

---

## Insights

### Key Features
- **HTML Parsing**: Utilizes the `Jsoup` library for efficient HTML parsing and hyperlink extraction.
- **Private IP Validation**: Ensures that URLs pointing to private IP addresses are rejected, enhancing security.
- **Error Handling**: Implements robust exception handling to manage invalid inputs and connection issues.

### Dependencies
- **Jsoup Library**: Required for HTML parsing and DOM manipulation.
- **Java Networking**: Uses `java.net.URL` for URL validation and host extraction.

### Potential Enhancements
1. **Customizable IP Validation**: Allow users to specify additional IP ranges or domains to block.
2. **Logging**: Add logging for better traceability of errors and validation checks.
3. **Concurrency**: Support parallel processing for fetching links from multiple URLs.

### Security Considerations
- **Private IP Blocking**: Prevents misuse of the method to access internal or restricted networks.
- **Input Validation**: Ensure the `url` parameter is sanitized to avoid injection attacks.

---

## Metadata
| Key         | Value              |
|-------------|--------------------|
| File Name   | `LinkLister.java`  |
| Package     | `com.scalesec.vulnado` |
