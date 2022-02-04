package me.friendly.exeter.module.impl.toggle.render.clickgui.item.properties;

import me.friendly.api.minecraft.render.RenderMethods;
import me.friendly.api.minecraft.render.font.FontUtil;
import me.friendly.exeter.module.impl.active.render.Colors;
import me.friendly.exeter.module.impl.toggle.render.clickgui.ClickGui;
import me.friendly.exeter.module.impl.toggle.render.clickgui.Panel;
import me.friendly.exeter.module.impl.toggle.render.clickgui.item.Item;
import me.friendly.exeter.properties.NumberProperty;
import org.lwjgl.input.Mouse;

public class NumberSlider
extends Item {
    private NumberProperty numberProperty;
    private Number min;
    private Number max;
    private int difference;

    public NumberSlider(NumberProperty numberProperty) {
        super(numberProperty.getAliases()[0]);
        this.numberProperty = numberProperty;
        this.min = (Number)numberProperty.getMinimum();
        this.max = (Number)numberProperty.getMaximum();
        this.difference = max.intValue() - min.intValue();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        dragSetting(mouseX, mouseY);
//        RenderMethods.drawRect(x, y, ((Number)numberProperty.getValue()).floatValue() <= min.floatValue() ? x : x + (width + 7.4F) * partialMultiplier(), y + height - 0.5f, !isHovering(mouseX, mouseY) ? 2012955202 : -1711586750);
        RenderMethods.drawRect(x, y, ((Number)numberProperty.getValue()).floatValue() <= min.floatValue() ? x : x + (width + 7.4F) * partialMultiplier(), y + height - 0.5f, !isHovering(mouseX, mouseY) ? Colors.getClientColorCustomAlpha(77) : Colors.getClientColorCustomAlpha(55));
//        RenderMethods.drawRect(x, y, x + getValueWidth(), y + height, !isHovering(mouseX, mouseY) ? 0x775CE843 : 0x66A317BD);//2002577475 : -1721964477);
//        FontUtil.drawString(String.format("%s\u00a77 %s", this.getLabel(), this.numberProperty.getValue()), this.x + 2.3f, this.y - 1.0f, -1);
        FontUtil.drawString(String.format("%s\u00a77 %s", this.getLabel(), this.numberProperty.getValue()), this.x + 2.0f, this.y + 4.0f, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            setSettingFromX(mouseX);
        }
    }

    private void setSettingFromX(int mouseX) {
        float percent = (mouseX - x) / (width + 7.4F);
        if(numberProperty.getValue() instanceof Double) {
            double result = (Double)numberProperty.getMinimum() + (difference * percent);
            numberProperty.setValue(Math.round(10.0 * result) / 10.0);
        } else if (numberProperty.getValue() instanceof Float) {
            float result = (Float)numberProperty.getMinimum() + (difference * percent);
            numberProperty.setValue(Math.round(10.0f * result) / 10.0f);
        } else if (numberProperty.getValue() instanceof Integer) {
            numberProperty.setValue(((Integer)numberProperty.getMinimum() + (int)(difference * percent)));
        }
    }

    @Override
    public int getHeight() {
        return 14;
    }

    private void dragSetting(int mouseX, int mouseY) {
        if(isHovering(mouseX, mouseY) && Mouse.isButtonDown(0)) {
            setSettingFromX(mouseX);
        }
    }

    private boolean isHovering(int mouseX, int mouseY) {
        for (Panel panel : ClickGui.getClickGui().getPanels()) {
            if (!panel.drag) continue;
            return false;
        }
        return (float)mouseX >= this.getX() && (float)mouseX <= this.getX() + (float)this.getWidth() && (float)mouseY >= this.getY() && (float)mouseY <= this.getY() + (float)this.height;
    }

    private float getValueWidth() {
        return ((Number)this.numberProperty.getMaximum()).floatValue() - ((Number)this.numberProperty.getMinimum()).floatValue() + ((Number)this.numberProperty.getValue()).floatValue();
    }

    private float middle() {
        return max.floatValue() - min.floatValue();
    }

    private float part() {
        return ((Number)numberProperty.getValue()).floatValue() - min.floatValue();
    }

    private float partialMultiplier() {
        return part() / middle();
    }
}

