package com.cloudfun.base.tool.object.compare.exception;

import java.io.Serial;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 9:23
 */
public class CompareException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;


    public CompareException(String message) {
        super(message);
    }
}
