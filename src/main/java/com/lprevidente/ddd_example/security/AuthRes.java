package com.lprevidente.ddd_example.security;

import org.jspecify.annotations.Nullable;

public record AuthRes(boolean signed, @Nullable String reason) {
}
