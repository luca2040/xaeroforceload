package com.apollo.xaeroforceload;

import net.neoforged.neoforge.common.ModConfigSpec;

public class XaeroForceloadConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> FORCELOAD_COLOR = BUILDER
            .translation("xaeroforceload.config.color")
            .define("forceLoadColor", "#FF0000", value ->
                    value instanceof String s && s.matches("^#[0-9A-Fa-f]{6}$"));

    public static final ModConfigSpec.DoubleValue FORCELOAD_OPACITY_MAIN = BUILDER
            .translation("xaeroforceload.config.opacity.main")
            .defineInRange("forceLoadOpacityMain", 0.2D, 0.1D, 1.0D);
    public static final ModConfigSpec.DoubleValue FORCELOAD_OPACITY_BORDER = BUILDER
            .translation("xaeroforceload.config.opacity.border")
            .defineInRange("forceLoadOpacityBorder", 0.46D, 0.1D, 1.0D);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static int[] parseColor(String color) {
        int rgb = Integer.parseInt(color.substring(1), 16);

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return new int[]{r, g, b};
    }

    private static int toXaeroColor(int[] rgb, int opacity) {
        // 0xBBGGRRAA
        return (rgb[2] << 24) |
                (rgb[1] << 16) |
                (rgb[0] << 8) |
                opacity;
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
