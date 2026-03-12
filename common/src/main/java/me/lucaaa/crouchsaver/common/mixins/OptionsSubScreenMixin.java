package me.lucaaa.crouchsaver.common.mixins;

import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(OptionsSubScreen.class)
public class OptionsSubScreenMixin {
    @Shadow
    @Nullable
    protected OptionsList list;
}