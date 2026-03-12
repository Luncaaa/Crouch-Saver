package me.lucaaa.crouchsaver.common.mixins;

import me.lucaaa.crouchsaver.common.CrouchSaverMod;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Options.class)
public class OptionsMixin {
    @Inject(method = "processOptions", at = @At("TAIL"))
    private void addCustomOption(Options.FieldAccess fieldAccess, CallbackInfo ci) {
        fieldAccess.process("crouchSaverCrouchEnabled", CrouchSaverMod.CROUCH_ENABLED);
        fieldAccess.process("crouchSaverLadderEnabled", CrouchSaverMod.LADDER_ENABLED);
    }
}