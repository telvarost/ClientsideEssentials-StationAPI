package com.github.telvarost.clientsideessentials;

import java.util.HashMap;

public class ModHelper {

    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static float clamp(float val, float min, float max) {
        return val < min ? min : Math.min(val, max);
    }

    public static class ModHelperFields {
        public static Integer IS_LAVA_BUCKET_CONSUMED = 0;
        public static Boolean IS_MOJANGFIX_LOADED = false;
    }
}
