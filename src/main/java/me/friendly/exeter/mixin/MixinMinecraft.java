package me.friendly.exeter.mixin;

import me.friendly.exeter.core.Exeter;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * This class is not present in the original
 * Exeter 1.8 client. It was added as part
 * of the 1.12.2 forge port
 *
 * @author Gopro336
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft
{
    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V",
                    ordinal = 2,
                    shift = At.Shift.BEFORE))
    private void initHook2(CallbackInfo ci)
    {
        new Exeter();
    }
}
