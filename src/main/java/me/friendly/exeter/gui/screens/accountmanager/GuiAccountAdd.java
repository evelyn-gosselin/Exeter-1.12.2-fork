//package me.friendly.exeter.gui.screens.accountmanager;///*
//
//import java.io.IOException;
//
//import com.mojang.realmsclient.gui.ChatFormatting;
//import me.friendly.exeter.core.Exeter;
//import me.friendly.exeter.gui.screens.accountmanager.Account;
//import me.friendly.exeter.gui.screens.accountmanager.GuiAccountScreen;
//import me.friendly.exeter.gui.screens.accountmanager.GuiPasswordField;
//import net.minecraft.client.gui.GuiButton;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.client.gui.GuiTextField;
//import org.lwjgl.input.Keyboard;
//
//public class GuiAccountAdd
//extends GuiScreen {
//    private GuiTextField usernameBox;
//    private GuiPasswordField passwordBox;
//    private String errorMessage = "";
//    private int errorTime = 0;
//
//    @Override
//    public void initGui() {
//        Keyboard.enableRepeatEvents((boolean)true);
//        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, "Add"));
//        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96 + 36, "Back"));
//        this.usernameBox = new GuiTextField(6, this.fontRendererObj, this.width / 2 - 100, 76, 200, 20);
//        this.passwordBox = new GuiPasswordField(this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
//        this.usernameBox.setMaxStringLength(120);
//        this.passwordBox.setMaxStringLength(100);
//    }
//
//    @Override
//    public void onGuiClosed() {
//        Keyboard.enableRepeatEvents((boolean)false);
//    }
//
//    @Override
//    public void updateScreen() {
//        this.usernameBox.updateCursorCounter();
//        this.passwordBox.updateCursorCounter();
//    }
//
//    @Override
//    public void mouseClicked(int x, int y, int b) {
//        this.usernameBox.mouseClicked(x, y, b);
//        this.passwordBox.mouseClicked(x, y, b);
//        try {
//            super.mouseClicked(x, y, b);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void actionPerformed(GuiButton button) {
//        if (button.id == 1) {
//            if (!this.usernameBox.getText().trim().isEmpty()) {
//                if (this.passwordBox.getText().trim().isEmpty()) {
//                    Account account = new Account(this.usernameBox.getText().trim());
//                    if (!Exeter.getInstance().getAccountManager().getRegistry().contains(account)) {
//                        Exeter.getInstance().getAccountManager().register(account);
//                    }
//                } else {
//                    Account account = new Account(this.usernameBox.getText().trim(), this.passwordBox.getText().trim());
//                    if (!Exeter.getInstance().getAccountManager().getRegistry().contains(account)) {
//                        Exeter.getInstance().getAccountManager().register(account);
//                    }
//                }
//            }
//            this.mc.displayGuiScreen(new GuiAccountScreen());
//        } else if (button.id == 2) {
//            this.mc.displayGuiScreen(new GuiAccountScreen());
//        }
//    }
//
//    @Override
//    protected void keyTyped(char typedChar, int keyCode) throws IOException {
//        this.usernameBox.textboxKeyTyped(typedChar, keyCode);
//        this.passwordBox.textboxKeyTyped(typedChar, keyCode);
//        if (typedChar == '\t') {
//            if (this.usernameBox.isFocused()) {
//                this.usernameBox.setFocused(false);
//                this.passwordBox.setFocused(true);
//            } else if (this.passwordBox.isFocused()) {
//                this.usernameBox.setFocused(true);
//                this.passwordBox.setFocused(false);
//            }
//        }
//        if (typedChar == '\r') {
//            this.actionPerformed((GuiButton)this.buttonList.get(0));
//        }
//    }
//
//    @Override
//    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        this.drawDefaultBackground();
//        this.mc.fontRenderer.drawStringWithShadow(String.format("%s* \u00a77Username", new Object[]{this.usernameBox.getText().length() > 1 ? ChatFormatting.GREEN : ChatFormatting.RED}), this.width / 2 - 109, 63.0f, 0xA0A0A0);
//        this.mc.fontRenderer.drawStringWithShadow("Password", this.width / 2 - 100, 103.0f, 0xA0A0A0);
//        this.mc.fontRenderer.drawStringWithShadow(this.errorMessage, this.width / 2 - this.fontRendererObj.getStringWidth(this.errorMessage), 13.0f, 0xA0A0A0);
//        if (this.errorMessage.length() > 1) {
//            ++this.errorTime;
//            if (this.errorTime > 1700) {
//                this.errorMessage = "";
//                this.errorTime = 0;
//            }
//        }
//        try {
//            this.usernameBox.drawTextBox();
//            this.passwordBox.drawTextBox();
//        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        super.drawScreen(mouseX, mouseY, partialTicks);
//    }
//}
//
