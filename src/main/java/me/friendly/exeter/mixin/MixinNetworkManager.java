package me.friendly.exeter.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.friendly.exeter.core.Exeter;
import me.friendly.exeter.events.PacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
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
@Mixin(value = NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    public void onPacketSend(Packet<?> packet, CallbackInfo info) {
        PacketEvent packetSendEvent = new PacketEvent(packet);
        Exeter.getInstance().getEventManager()
                .dispatch(packetSendEvent);

        if (packetSendEvent.isCanceled()) {
            info.cancel();
        }
    }

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onPacketReceive(ChannelHandlerContext chc, Packet<?> packet, CallbackInfo info) {
        PacketEvent packetReceiveEvent = new PacketEvent(packet);
        Exeter.getInstance().getEventManager()
                .dispatch(packetReceiveEvent);

        if (packetReceiveEvent.isCanceled()) {
            info.cancel();
        }
    }
}