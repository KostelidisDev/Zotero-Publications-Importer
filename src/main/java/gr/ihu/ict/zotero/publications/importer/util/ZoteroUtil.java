package gr.ihu.ict.zotero.publications.importer.util;

import io.vavr.collection.List;
import io.vavr.control.Try;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ZoteroUtil {
    public static Date parseZoteroDate(final String date) {
        final List<SimpleDateFormat> supportedFormats = List.of(
                new SimpleDateFormat("MM d, yyyy"),
                new SimpleDateFormat("MM, yyyy"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("yyyy-MM")
        );

        final List<Date> possibleValidDates = supportedFormats
                .map(format -> Try.of(() -> format.parse(date)))
                .filter(Try::isSuccess)
                .map(Try::get);

        if (possibleValidDates.isEmpty()) {
            return new Date();
        }

        return possibleValidDates.get(0);
    }
}
