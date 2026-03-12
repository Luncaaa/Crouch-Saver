package me.lucaaa.crouchsaver.common.mixins;

import me.lucaaa.crouchsaver.common.CrouchSaverMod;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public class ControlsScreenMixin extends OptionsSubScreenMixin {
    /* This method adds it "filling the gaps". Since a fully new section is needed, inject into addOptions instead.

    @ModifyReturnValue(method = "options", at = @At("RETURN"))
    private static OptionInstance<?>[] addCustomOption(OptionInstance<?>[] original) {
        OptionInstance<?>[] extended = new OptionInstance[original.length + 1];

        // Copy the original return value into the new list and then add the button
        System.arraycopy(original, 0, extended, 0, original.length);
        extended[original.length] = CrouchSaverMod.CROUCH_ENABLED;

        return extended;
    }*/

    @Inject(method = "addOptions", at = @At("TAIL"))
    protected void addOptions(CallbackInfo ci) {
        if (this.list == null) return;

        this.list.addSmall(CrouchSaverMod.CROUCH_ENABLED, CrouchSaverMod.LADDER_ENABLED);
    }
}