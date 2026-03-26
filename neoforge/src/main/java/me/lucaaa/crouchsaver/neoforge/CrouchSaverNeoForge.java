package me.lucaaa.crouchsaver.neoforge;

import me.lucaaa.crouchsaver.common.CrouchSaverMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = CrouchSaverMod.ID, dist = Dist.CLIENT)
public class CrouchSaverNeoForge extends CrouchSaverMod {
    public CrouchSaverNeoForge(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (container1, screen) -> new ControlsScreen(screen, Minecraft.getInstance().options));
    }
}