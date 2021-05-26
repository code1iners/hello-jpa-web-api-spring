package jpabook.jpashopreview.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h3>Result wrapper</h3>
 */
@Getter
@AllArgsConstructor
public class Result<T> {
    private final int count;
    private final T data;
}
