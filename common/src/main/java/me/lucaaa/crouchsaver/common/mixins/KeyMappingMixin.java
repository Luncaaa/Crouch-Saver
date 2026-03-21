package me.lucaaa.crouchsaver.common.mixins;

import me.lucaaa.crouchsaver.common.CrouchSaverMod;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public class KeyMappingMixin {
    @Unique private boolean crouchSaver$openedGUI;

    @Inject(method = "setDown", at = @At("HEAD"), cancellable = true)
    private void keepCrouching(boolean down, CallbackInfo ci) {
        if (!CrouchSaverMod.isCrouchEnabled()) return;

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) return;

        if (minecraft.options.keyShift.same((KeyMapping) (Object) this)) {

            if (!down) {
                // If crouching is going to be removed, only cancel the event if the window is not focused or the player is in an inv/chat.
                if (crouchSaver$isStickyApplicable()) {
                    crouchSaver$openedGUI = true;
                    ci.cancel();

                // If the player set "toggle crouch" to true in the controls screen, do not uncrouch when he closes the GUI
                } else if (minecraft.options.toggleCrouch().get() && crouchSaver$openedGUI) {
                    crouchSaver$openedGUI = false;
                    ci.cancel();
                }
            }
        }
    }

    @Unique
    private boolean crouchSaver$isStickyApplicable() {
        return !Minecraft.getInstance().isWindowActive() || Minecraft.getInstance().screen != null;
    }
}