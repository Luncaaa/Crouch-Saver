package me.lucaaa.crouchsaver.common.mixins;

import me.lucaaa.crouchsaver.common.CrouchSaverMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Nullable
    public ClientLevel level;

    @Shadow
    @Final
    public Options options;

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void setScreen(Screen screen, CallbackInfo ci) {
        if (!CrouchSaverMod.isLadderEnabled()) return;

        if (player == null || !player.onClimbable() || level == null) return;

        // If a screen is being set, mark the keybind as pressed by the mod so KeyMappingMixin keeps the player down.
        if (screen != null) {
            if (!options.keyShift.isDown()) {
                CrouchSaverMod.modPressed = true;
                options.keyShift.setDown(true);
            }

        // Otherwise, if the screen is being closed and the crouch keybind was pressed by the mod, free it.
        } else if (CrouchSaverMod.modPressed) {
            CrouchSaverMod.modPressed = false;
            options.keyShift.setDown(false);
        }
    }
}