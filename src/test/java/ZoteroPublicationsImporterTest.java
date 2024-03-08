import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gr.ihu.ict.zotero.publications.importer.config.Config;
import gr.ihu.ict.zotero.publications.importer.config.DefaultConfig;
import gr.ihu.ict.zotero.publications.importer.model.PublicationItem;
import gr.ihu.ict.zotero.publications.importer.service.PublicationItemService;
import gr.ihu.ict.zotero.publications.importer.service.PublicationItemServiceImpl;

public class ZoteroPublicationsImporterTest {
    @Test
    public void test() {
        final Config config = new DefaultConfig();
        final PublicationItemService publicationItemService = new PublicationItemServiceImpl(config);
        final List<PublicationItem> publicationItems = publicationItemService.findAllPublicationItemsByUserId("12296221");
        Assert.assertFalse(publicationItems.isEmpty());
        for (PublicationItem publicationItem : publicationItems) {
            System.out.println(publicationItem.getTitle());
        }
    }
}
