package me.lucaaa.crouchsaver.forge;

import me.lucaaa.crouchsaver.common.CrouchSaverMod;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CrouchSaverMod.ID)
public class CrouchSaverForge extends CrouchSaverMod {
    public CrouchSaverForge(FMLJavaModLoadingContext context) {
        MinecraftForge.registerConfigScreen(((minecraft, screen) -> new ControlsScreen(screen, minecraft.options)));
    }
}