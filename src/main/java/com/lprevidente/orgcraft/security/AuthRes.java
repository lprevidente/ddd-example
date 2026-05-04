package com.lprevidente.orgcraft.security;

import org.jspecify.annotations.Nullable;

record AuthRes(boolean signed, @Nullable String reason) {}
