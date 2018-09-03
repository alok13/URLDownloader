package com.stream;

import com.exception.StreamException;

public interface StreamService<REQUEST> {
    REQUEST getStream(String input) throws StreamException;
}
