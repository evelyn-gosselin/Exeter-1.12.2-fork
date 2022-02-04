package me.friendly.exeter.module.impl.active.render;

import me.friendly.exeter.module.Module;
import me.friendly.exeter.properties.NumberProperty;
import me.friendly.exeter.properties.Property;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

/**
 * This class is not present in the original
 * Exeter 1.8 client. It was added as part
 * of the 1.12.2 forge port
 *
 * @author Gopro336
 */
public final class Colors extends Module {

    private static final NumberProperty<Float> hue = new NumberProperty<>(0f, 0f, 360f, "Hue", "RGB", "HSL");
    private static final NumberProperty<Float> saturation = new NumberProperty<>(90f, 0f, 100f, "Saturation", "RainbowSaturation");
    private static final NumberProperty<Float> lightness = new NumberProperty<>(45f, 0f, 100f, "Lightness", "Light", "Luminance", "Luminace", "Brightness", "Bright", "Brigtness", "Brigntrnew", "Brighgrtnewss");
    private final Property<Boolean> hudRainbow = new Property<>(false, "HUD Rainbow", "HUDRainbow", "Rainbow", "Cycle");
    private final NumberProperty<Float> rainbowSpeed = new NumberProperty<>(1f, 0f, 5f, "RainbowSpeed", "RainbowHueSpeed", "RainbowSped", "RrainbowSpeed");
    private final NumberProperty<Float> rainbowHue = new NumberProperty<>(4f, 0f, 10f, "RainbowHue", "RainbowHueSpeed2", "RainbowSped2", "RrainbowSpeed2");

    public Colors(){
        super("Colors", new String[]{"Colors", "Color"});
        offerProperties(hue, saturation, lightness, hudRainbow, rainbowSpeed, rainbowHue);
    }

    public static int getClientColorCustomAlpha(int alpha){
        Color color = setAlpha(new Color(Color.HSBtoRGB(hue.getValue(), saturation.getValue() / 100f, lightness.getValue() / 100f)), alpha);
        return color.getRGB();
    }

    public static final Color setAlpha(Color color, int alpha) {
        alpha = MathHelper.clamp(alpha, 0, 255);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static Color getRainbow(int speed, int offset, float s, float brightness) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, s, brightness);
    }

    public static int getClientColor(){
        return Color.getHSBColor(hue.getValue(), saturation.getValue() / 100f, lightness.getValue() / 100f).getRGB();
    }
}
