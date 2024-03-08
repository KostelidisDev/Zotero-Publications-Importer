package gr.ihu.ict.zotero.publications.importer.service;

import gr.ihu.ict.zotero.publications.importer.model.PublicationItem;

import java.util.List;

public interface PublicationItemService {
    List<PublicationItem> findAllPublicationItemsByUserId(String userId);
}
