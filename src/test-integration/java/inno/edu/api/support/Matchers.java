package inno.edu.api.support;

public class Matchers {

    /**
     * This is need to avoid anoying warning about the varargs expansion with possible null values,
     * the hamcrest library is not adding it because of Java < 1.7 compatibility.
     */
    @SafeVarargs
    public static <T> org.hamcrest.Matcher<java.lang.Iterable<T>> safeHasItems(T... items) {
        return org.hamcrest.core.IsCollectionContaining.<T>hasItems(items);
    }
}
