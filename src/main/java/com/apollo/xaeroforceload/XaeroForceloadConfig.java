package com.apollo.xaeroforceload;

import net.neoforged.neoforge.common.ModConfigSpec;

public class XaeroForceloadConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> FORCELOAD_COLOR = BUILDER
            .translation("test.some.text")
            .define("forceLoadColor", "#FF0000", value ->
                    value instanceof String s && s.matches("^#[0-9A-Fa-f]{6}$"));

    public static final ModConfigSpec.DoubleValue FORCELOAD_OPACITY_MAIN = BUILDER
            .translation("some.other.translation")
            .defineInRange("forceLoadOpacity", 0.2D, 0.1D, 1.0D);
    public static final ModConfigSpec.DoubleValue FORCELOAD_OPACITY_BORDER = BUILDER
            .translation("some.other.translationv2")
            .defineInRange("forceLoadOpacity2", 0.46D, 0.1D, 1.0D);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static int[] parseColor(String color) {
        int rgb = Integer.parseInt(color.substring(1), 16);

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return new int[]{r, g, b};
    }

    private static int toXaeroColor(int[] rgb, int opacity) {
        return opacity |
                (rgb[0] << 8) |
                (rgb[1] << 16) |
                (rgb[2] << 24);
    }

    public static int getFillColor() {
        int[] rgbMainColor = parseColor(FORCELOAD_COLOR.get());
        int fillOpacity = (int) (FORCELOAD_OPACITY_MAIN.get() * 0xFF);

        return toXaeroColor(rgbMainColor, fillOpacity);
    }

    public static int getBorderColor() {
        int[] rgbMainColor = parseColor(FORCELOAD_COLOR.get());
        int borderOpacity = (int) (FORCELOAD_OPACITY_BORDER.get() * 0xFF);

        return toXaeroColor(rgbMainColor, borderOpacity);
    }
}
