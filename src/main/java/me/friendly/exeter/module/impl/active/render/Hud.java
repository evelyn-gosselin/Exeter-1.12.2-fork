package me.friendly.exeter.module.impl.active.render;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Collection;

import me.friendly.api.event.Listener;
import me.friendly.api.interfaces.Toggleable;
import me.friendly.api.minecraft.helper.PlayerHelper;
import me.friendly.api.minecraft.render.RenderMethods;
import me.friendly.api.minecraft.render.font.FontUtil;
import me.friendly.exeter.core.Exeter;
import me.friendly.exeter.events.RenderGameOverlayEvent;
import me.friendly.exeter.module.Module;
import me.friendly.exeter.module.ToggleableModule;
import me.friendly.exeter.properties.EnumProperty;
import me.friendly.exeter.properties.Property;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
//import net.minecraft.util.StatCollector;

public final class Hud
extends Module {
    public static final Property<Boolean> customFont = new Property<Boolean>(false, "CustomFont", "cf", "font");
    private final Property<Boolean> watermark = new Property<Boolean>(false, "Watermark", "wm", "water");
    private final Property<Boolean> transparent = new Property<Boolean>(true, "Transparent", "trans");
    private final Property<Boolean> direction = new Property<Boolean>(true, "Direction", "facing", "d");
    private final Property<Boolean> armor = new Property<Boolean>(true, "Armor", "a");
    private final Property<Boolean> potions = new Property<Boolean>(true, "Potions", "pots");
    private final Property<Boolean> time = new Property<Boolean>(true, "Time", "t");
    private final Property<Boolean> coords = new Property<Boolean>(true, "Coords", "coord", "c", "cord");
    private final Property<Boolean> arraylist = new Property<Boolean>(true, "ArrayList", "array", "al");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
    private final EnumProperty<Organize> organize = new EnumProperty<Organize>(Organize.LENGTH, "Organize", "o");
    private final EnumProperty<Look> look = new EnumProperty<Look>(Look.DEFAULT, "Casing", "c");

    public Hud() {
        super("Hud", new String[]{"textgui", "hud", "overlay"});
        this.offerProperties(this.customFont, this.look, this.watermark, this.organize, this.transparent, this.potions, this.armor, this.time, this.direction, this.arraylist, this.coords);
        Exeter.getInstance().getEventManager().register(new Listener<RenderGameOverlayEvent>("text_gui_render_game_overlay_listener"){

            @Override
            public void call(RenderGameOverlayEvent event) {
                Collection<PotionEffect> effects;
                if (minecraft.gameSettings.showDebugInfo || event.getType() != RenderGameOverlayEvent.Type.IN_GAME) {
                    return;
                }
                if (watermark.getValue()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    FontUtil.drawString(String.format("%s \u00a77b%s", "Exeter", 23), 2.0f, 2.0f, (Boolean) transparent.getValue() != false ? -1711276033 : -1);
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                    
                }
                ScaledResolution scaledResolution = event.getScaledResolution();
                int positionY = -7;
                if (arraylist.getValue()) {
                    List<Module> modules = Exeter.getInstance().getModuleManager().getRegistry();
                    switch ((Organize)((Object) organize.getValue())) {
                        case ABC: {
                            modules.sort((mod1, mod2) -> mod1.getTag().compareTo(mod2.getTag()));
                            break;
                        }
                        case LENGTH: {
                            modules.sort((mod1, mod2) -> FontUtil.getStringWidth(mod2.getTag()) - FontUtil.getStringWidth(mod1.getTag()));
                        }
                    }
                    for (Module module : modules) {
                        ToggleableModule toggleableModule;
                        if (!(module instanceof Toggleable) || !(toggleableModule = (ToggleableModule)module).isDrawn() || toggleableModule.getColor() == 0 || !toggleableModule.isRunning()) continue;
                        int labelWidth = FontUtil.getStringWidth(getTag(toggleableModule.getTag()));
                        FontUtil.drawString(getTag(toggleableModule.getTag()), scaledResolution.getScaledWidth() - labelWidth - 2, positionY += 9, toggleableModule.getColor());
                    }
                }
                if (armor.getValue()) {
                    int x = 15;
                    GlStateManager.pushMatrix();
                    RenderHelper.enableGUIStandardItemLighting();
                    for (int index = 3; index >= 0; --index) {
                        ItemStack stack = minecraft.player.inventory.armorInventory.get(index);
                        if (stack == null) continue;
                        int y = minecraft.player.isInsideOfMaterial(Material.WATER) && !minecraft.player.capabilities.isCreativeMode ? 65 : (minecraft.player.capabilities.isCreativeMode ? 38 : 55);
                        minecraft.getRenderItem().renderItemAndEffectIntoGUI(stack, scaledResolution.getScaledWidth() / 2 + x, scaledResolution.getScaledHeight() - y);
                        minecraft.getRenderItem().renderItemOverlays(minecraft.fontRenderer, stack, scaledResolution.getScaledWidth() / 2 + x, scaledResolution.getScaledHeight() - y);
                        x += 18;
                    }
                    RenderHelper.disableStandardItemLighting();
                    GlStateManager.popMatrix();
                }

                int y = scaledResolution.getScaledHeight() - (minecraft.currentScreen instanceof GuiChat ? 24 : 10);

                if (potions.getValue()
                        && (effects = minecraft.player.getActivePotionEffects()) != null
                        && !effects.isEmpty())
                {

                    for (PotionEffect effect : effects) {
//                        Potion potion;
//                        if (effect == null ||
//                                (potion = Potion.POTION_TYPES[effect.getPotionID()]) == null) continue;

                        if (effect == null) return;

//                        final Potion potion = Potion.POTION_TYPES[effect.getPotionID()];
                        final Potion potion = effect.getPotion();
                        if (potion == null) {
                            continue;
                        }


                        String name = I18n.translateToLocal(potion.getName());
                        name = name + String.format(" \u00a77%s : %s", effect.getAmplifier() + 1, Potion.getPotionDurationString(effect, 1));
                        int align = scaledResolution.getScaledWidth() - FontUtil.getStringWidth(name) - 2;
                        FontUtil.drawString(name, align, y, potion.getLiquidColor());

                        y -= 9;
                    }
                }

                y += 9;

                if (coords.getValue()) {
                    String coordinatesFormat = String.format("\u00a7f%s, %s, %s \u00a77XYZ", (int)minecraft.player.posX, (int)minecraft.player.posY, (int)minecraft.player.posZ);
                    FontUtil.drawString(coordinatesFormat, scaledResolution.getScaledWidth() - FontUtil.getStringWidth(coordinatesFormat) - 2, y -= 9, -1);
                }
                if (time.getValue()) {
                    String time = String.format("\u00a77%s", dateFormat.format(new Date()));
                    FontUtil.drawString(time, scaledResolution.getScaledWidth() - FontUtil.getStringWidth(time) - 2, y -= 9, -1);
                }
                if (direction.getValue()) {
                    String direction = String.format("\u00a77%s", PlayerHelper.getFacingWithProperCapitals().toUpperCase());
                    FontUtil.drawString(direction, scaledResolution.getScaledWidth() - FontUtil.getStringWidth(direction) - 2, y -= 9, -1);
                }
            }
        });
    }

    private String getTag(String tag) {
        switch (look.getValue()) {
            case UPPER: {
                tag = tag.toUpperCase();
                break;
            }
            case LOWER: {
                tag = tag.toLowerCase();
                break;
            }
            case CUB: {
                tag = String.format("[%s]", tag.toLowerCase());
            }
        }

        return tag;
    }

    private enum Look {
        DEFAULT,
        LOWER,
        UPPER,
        CUB;

    }

    private enum Organize {
        ABC,
        LENGTH;

    }
}

