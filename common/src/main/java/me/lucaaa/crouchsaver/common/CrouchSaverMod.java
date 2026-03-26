package me.lucaaa.crouchsaver.common;

import net.minecraft.ChatFormatting;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CrouchSaverMod {
    public static final String ID = "crouchsaver";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);

    public static boolean modPressed = false;

    public static final OptionInstance<Boolean> CROUCH_ENABLED = OptionInstance.createBoolean(
            "crouchsaver.options.crouch",
            OptionInstance.cachedConstantTooltip(Component.translatable("crouchsaver.options.crouch.tooltip")),
            (caption, value) -> value ?
                    Component.translatable("options.on").withStyle(ChatFormatting.GREEN) :
                    Component.translatable("options.off").withStyle(ChatFormatting.RED),
            true,
            newValue -> {}
    );

    public static final OptionInstance<Boolean> LADDER_ENABLED = OptionInstance.createBoolean(
            "crouchsaver.options.ladder",
            OptionInstance.cachedConstantTooltip(Component.translatable("crouchsaver.options.ladder.tooltip")),
            (caption, value) -> value ?
                    Component.translatable("options.on").withStyle(ChatFormatting.GREEN) :
                    Component.translatable("options.off").withStyle(ChatFormatting.RED),
            true,
            newValue -> {}
    );

    public static boolean isCrouchEnabled() {
        return CROUCH_ENABLED.get();
    }

    public static boolean isLadderEnabled() {
        return LADDER_ENABLED.get();
    }
}