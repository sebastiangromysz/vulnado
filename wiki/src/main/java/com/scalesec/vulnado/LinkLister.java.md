# LinkLister Documentation

## Overview

The `LinkLister` class provides functionality to extract hyperlinks from a given URL. It utilizes the Jsoup library to parse HTML content and retrieve links. The class offers two methods for link extraction, with the second method adding validation to prevent the use of private IP addresses.

## Class: LinkLister

### Methods

#### `getLinks(String url)`

- **Description**: 
  Extracts all hyperlinks from the specified URL.
  
- **Parameters**: 
  - `url`: A `String` representing the URL from which links are to be extracted.
  
- **Returns**: 
  - A `List<String>` containing the absolute URLs of all hyperlinks found in the HTML document.
  
- **Exceptions**: 
  - Throws `IOException` if there is an error connecting to the URL or parsing the document.

#### `getLinksV2(String url)`

- **Description**: 
  Similar to `getLinks`, but includes additional validation to prevent extraction from URLs with private IP addresses.
  
- **Parameters**: 
  - `url`: A `String` representing the URL from which links are to be extracted.
  
- **Returns**: 
  - A `List<String>` containing the absolute URLs of all hyperlinks found in the HTML document.
  
- **Exceptions**: 
  - Throws `BadRequest` if the URL contains a private IP address or if any other error occurs during processing.

### Exception Handling

- **BadRequest**: 
  A custom exception thrown by `getLinksV2` when a private IP address is detected or when an error occurs during URL processing.

## Insights

- **Private IP Address Validation**: 
  The `getLinksV2` method includes a check for private IP addresses (e.g., `172.`, `192.168`, `10.`) to prevent potential security risks associated with accessing internal network resources.

- **Use of Jsoup Library**: 
  The Jsoup library is used for HTML parsing, which simplifies the process of extracting elements from web pages.

- **URL Handling**: 
  The `getLinksV2` method uses Java's `URL` class to parse and validate the host part of the URL, ensuring that only public IP addresses are processed.

- **Error Handling**: 
  The `getLinksV2` method wraps exceptions in a custom `BadRequest` exception, providing a clear indication of issues related to URL processing.
