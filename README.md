# Zotero Publications Importer

## Overview

The Zotero Publications Importer is a Maven module designed to simplify the extraction of publication information from Zotero. This library provides a convenient way to parse the data and convert it into a Java object, making it easier for developers to integrate Zotero data into their applications.

## Features

- **Zotero API Integration:** Connect to the Zotero API to retrieve publication information for a specific user.
- **Biographical Information:** Extracts and organizes biographical information, including personal details, education, work experience, skills, and more.
- **Java Object Representation:** Returns a structured Java object for easy integration into your applications.
- **Maven Compatibility:** Easily include the library in your Maven projects.

## Usage

### 0. Install

Clone this repository and install the library:

```bash
git clone https://github.com/KostelidisDev/Zotero-Publications-Importer.git
cd Zotero-Publications-Importer
mvn package install
```

### 1. Add Dependency

Add the following dependency to your Maven project:

```xml
<dependency>
    <groupId>gr.ihu.ict</groupId>
    <artifactId>zotero-publications-importer</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 2. Fetch Publication From Zotero API

```java
import gr.ihu.ict.zotero.publications.importer.config.Config;
import gr.ihu.ict.zotero.publications.importer.config.DefaultConfig;
import gr.ihu.ict.zotero.publications.importer.model.PublicationItem;
import gr.ihu.ict.zotero.publications.importer.service.PublicationItemService;
import gr.ihu.ict.zotero.publications.importer.service.PublicationItemServiceImpl;

public class Demo {
    public static void main(String[] args) {
        final Config config = new DefaultConfig();
        final PublicationItemService publicationItemService = new PublicationItemServiceImpl(config);
        final List<PublicationItem> publicationItems = publicationItemService.findAllPublicationItemsByUserId("12296221");
        for (PublicationItem publicationItem : publicationItems) {
            System.out.println(publicationItem.getTitle());
        }
    }
}
```

## Contribution

Contributions are welcome! If you find any issues or have suggestions for improvements, please feel free to open an issue or submit a pull request.

## License

This Zotero Publications Importer is licensed under the [MIT License](LICENSE). See the [LICENSE](LICENSE) file for more details.

## Acknowledgments

This README.md has generated using OpenAI's ChatGPT and modified by [me](https://github.com/IordanisKostelidis)
