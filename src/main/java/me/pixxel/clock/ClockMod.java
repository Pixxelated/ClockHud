package me.pixxel.clock;

import me.pixxel.clock.config.ClockConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockMod implements ClientModInitializer {

    public String MOD_ID = "clock-hud";
    public String NAME = "Clock Hud", VERSION = "v1.0", AUTHOR = "Pixxel", NAMEVER = NAME + " " + VERSION;
    public static final Logger LOGGER = LoggerFactory.getLogger("ClockHud");
    public static boolean SHOW_CLOCK;
    public static ClockConfig CONFIG;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing " + NAMEVER + " by " + AUTHOR);

        AutoConfig.register(ClockConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(ClockConfig.class).getConfig();

        KeyBinding binding_toggleClockOverlay = KeyBindingHelper.registerKeyBinding((new KeyBinding("key.clock.toggleClockOverlay", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F10, "key.clock.category")));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (binding_toggleClockOverlay.wasPressed() && !CONFIG.holdKeyToShowClock) {
                CONFIG.enabled = !CONFIG.enabled;
            }
            if (CONFIG.holdKeyToShowClock) {
                SHOW_CLOCK = binding_toggleClockOverlay.isPressed();
            } else {
                SHOW_CLOCK = CONFIG.enabled;
            }
        });
    }
}
