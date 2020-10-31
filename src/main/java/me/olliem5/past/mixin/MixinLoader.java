package me.olliem5.past.mixin;

import me.olliem5.past.Past;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class MixinLoader implements IFMLLoadingPlugin {

    public MixinLoader() {
        System.out.println("[" + Past.nameversion + "]" + " " + "Injecting Mixins!");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.past.json");
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
    }

    @Override
    public String[] getASMTransformerClass() { return new String[0]; }


    @Override
    public String getModContainerClass() { return null; }

    @Override
    public String getSetupClass() { return null; }

    @Override
    public String getAccessTransformerClass() { return null; }

    @Override
    public void injectData(Map<String, Object> data) {}
}
