//package me.friendly.exeter.gui.screens.accountmanager;
//
//import me.friendly.exeter.gui.screens.accountmanager.GuiPasswordField;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.GuiButton;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.client.gui.GuiTextField;
//import net.minecraft.util.Session;
//import org.lwjgl.input.Keyboard;
//
//public class GuiDirectLogin
//extends GuiScreen {
//    private final GuiScreen parentScreen;
//    private GuiTextField usernameTextField;
//    private GuiPasswordField passwordTextField;
//    private String error;
//    private Minecraft minecraft = Minecraft.getMinecraft();
//
//    public GuiDirectLogin(GuiScreen guiScreen) {
//        this.parentScreen = guiScreen;
//    }
//
//    @Override
//    public void updateScreen() {
//        this.usernameTextField.updateCursorCounter();
//        this.passwordTextField.updateCursorCounter();
//    }
//
//    @Override
//    public void onGuiClosed() {
//        Keyboard.enableRepeatEvents((boolean)false);
//    }
//
//    @Override
//    protected void actionPerformed(GuiButton guiButton) {
//        if (!guiButton.enabled) {
//            return;
//        }
//        if (guiButton.id == 1) {
//            this.minecraft.displayGuiScreen(this.parentScreen);
//        } else if (guiButton.id == 0) {
//            if (this.passwordTextField.getText().length() > 0) {
//                String usernameTextFieldText = this.usernameTextField.getText();
//                String passwordTextFieldText = this.passwordTextField.getText();
//                try {
//                    String result = Minecraft.getMinecraft().processLogin(usernameTextFieldText, passwordTextFieldText).trim();
//                    if (result == null || !result.contains(":")) {
//                        this.error = result;
//                        return;
//                    }
//                    String[] values = result.split(":");
//                    if (values.length > 1) {
//                        this.minecraft.setSession(new Session(values[2], values[4], values[3], "mojang"));
//                    }
//                    this.minecraft.displayGuiScreen(this.parentScreen);
//                }
//                catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            } else {
//                this.minecraft.setSession(new Session(this.usernameTextField.getText(), "", "", "mojang"));
//            }
//            this.minecraft.displayGuiScreen(this.parentScreen);
//        }
//    }
//
//    @Override
//    protected void keyTyped(char typedChar, int keyCode) {
//        this.usernameTextField.textboxKeyTyped(typedChar, keyCode);
//        this.passwordTextField.textboxKeyTyped(typedChar, keyCode);
//        if (typedChar == '\t') {
//            if (this.usernameTextField.isFocused()) {
//                this.usernameTextField.isFocused = false;
//                this.passwordTextField.isFocused = true;
//            } else {
//                this.usernameTextField.isFocused = true;
//                this.passwordTextField.isFocused = false;
//            }
//        }
//        if (typedChar == '\r') {
//            this.actionPerformed((GuiButton)this.buttonList.get(0));
//        }
//    }
//
//    @Override
//    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
//        try {
//            super.mouseClicked(mouseX, mouseY, mouseButton);
//        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        this.usernameTextField.mouseClicked(mouseX, mouseY, mouseButton);
//        this.passwordTextField.mouseClicked(mouseX, mouseY, mouseButton);
//    }
//
//    @Override
//    public void initGui() {
//        Keyboard.enableRepeatEvents((boolean)true);
//        this.buttonList.clear();
//        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Done"));
//        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
//        this.usernameTextField = new GuiTextField(6, this.fontRendererObj, this.width / 2 - 100, 76, 200, 20);
//        this.passwordTextField = new GuiPasswordField(this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
//        this.usernameTextField.setMaxStringLength(512);
//    }
//
//    @Override
//    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        this.drawDefaultBackground();
//        this.drawCenteredString(this.fontRendererObj, "Login", this.width / 2, 12, 0xFFFFFF);
//        this.drawString(this.fontRendererObj, "Username", this.width / 2 - 100, 63, 0xA0A0A0);
//        this.drawString(this.fontRendererObj, "Password", this.width / 2 - 100, 104, 0xA0A0A0);
//        this.usernameTextField.drawTextBox();
//        this.passwordTextField.drawTextBox();
//        if (this.error != null) {
//            this.drawCenteredString(this.fontRendererObj, "\u00a7cLogin Failed: " + this.error, this.width / 2, this.height / 4 + 72 + 12, -1);
//        }
//        super.drawScreen(mouseX, mouseY, partialTicks);
//    }
//}
//
