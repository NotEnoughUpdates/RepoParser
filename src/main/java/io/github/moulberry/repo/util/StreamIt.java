package io.github.moulberry.repo.util;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamIt<T> implements Iterable<T> {
    Stream<T> s;

    public StreamIt(Stream<T> t) {
        s = t;
    }


    @Override
    public Iterator<T> iterator() {
        return s.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return s.spliterator();
    }
}
