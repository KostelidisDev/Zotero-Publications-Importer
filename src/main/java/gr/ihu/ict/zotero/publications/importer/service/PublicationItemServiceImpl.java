package gr.ihu.ict.zotero.publications.importer.service;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import gr.ihu.ict.zotero.publications.importer.util.MapSafeResolverUtil;
import gr.ihu.ict.zotero.publications.importer.util.ZoteroUtil;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import gr.ihu.ict.zotero.publications.importer.config.Config;
import gr.ihu.ict.zotero.publications.importer.model.PublicationItem;
import io.vavr.collection.Seq;
import io.vavr.control.Try;

public class PublicationItemServiceImpl implements PublicationItemService {

    private final Config config;

    public PublicationItemServiceImpl(final Config config) {
        this.config = config;
    }

    private Try<URL> constructUrlToCall(final String userId) {
        return Try.success(config)
                .map(Config::getUrl)
                .map(_baseUrl -> String.format("%s/users/%s/publications/items", _baseUrl, userId))
                .flatMap(url -> Try.of(() -> new URL(url)));
    }

    private Try<List<Map<String, Object>>> getAllItems(final String userId) {
        return constructUrlToCall(userId)
                .flatMap(PublicationItemServiceImpl::getContentFromUrl)
                .map(JSONArray::new)
                .map(JSONArray::toList)
                .map(list -> list.stream().map(listItem -> (Map<String, Object>) listItem).collect(Collectors.toList()));
    }

    private static Try<String> getContentFromUrl(final URL urlToCall) {
        return Try.of(() -> IOUtils.toString(urlToCall, Charset.defaultCharset()));
    }

    private Try<PublicationItem> parsePublication(final Map<String, Object> publication) {
        return Try.of(() -> MapSafeResolverUtil.safeResolveMapValueAsMapOfStringObject("data", publication, new HashMap<>()))
                .map(publicationData -> {
                    final String title = MapSafeResolverUtil.safeResolveMapValueAsString("title", publicationData, "Untitled");
                    final List<String> creators = io.vavr.collection.List.ofAll(
                                    MapSafeResolverUtil.safeResolveMapValueAsListOfMapStringObject(
                                            "creators",
                                            publicationData,
                                            Collections.emptyList()
                                    )
                            )
                            .map(creatorData -> String.format(
                                    "%s %s",
                                    MapSafeResolverUtil.safeResolveMapValueAsString(
                                            "firstName",
                                            creatorData,
                                            "Unknown First Name"
                                    ),
                                    MapSafeResolverUtil.safeResolveMapValueAsString(
                                            "lastName",
                                            creatorData,
                                            "Unknown Last Name"
                                    )))
                            .toJavaList();
                    final String abstractNote = MapSafeResolverUtil.safeResolveMapValueAsString("abstractNote", publicationData, "");
                    final String date = MapSafeResolverUtil.safeResolveMapValueAsString("date", publicationData, "");
                    final Date parsedDate = ZoteroUtil.parseZoteroDate(date);
                    final String doi = MapSafeResolverUtil.safeResolveMapValueAsString("DOI", publicationData, "");
                    final String url = MapSafeResolverUtil.safeResolveMapValueAsString("url", publicationData, "");

                    return new PublicationItem(
                            title,
                            creators,
                            abstractNote,
                            parsedDate,
                            doi,
                            url);
                });
    }

    @Override
    public List<PublicationItem> findAllPublicationItemsByUserId(final String userId) {
        return getAllItems(userId)
                .map(io.vavr.collection.List::ofAll)
                .flatMap(publications -> Try.sequence(publications.map(this::parsePublication))
                        .map(Seq<PublicationItem>::toJavaList))
                .get();
    }

}
