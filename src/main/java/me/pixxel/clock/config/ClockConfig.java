package me.pixxel.clock.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.clothconfig2.gui.entries.SelectionListEntry;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@Config(name = "clock")
public class ClockConfig implements ConfigData {

    public boolean enabled = true;

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public TimeSystem timeSystem = TimeSystem.HR12;
    public enum TimeSystem implements SelectionListEntry.Translatable {
        HR12,
        HR24;
        @Override
        public @NotNull String getKey() {
            return "text.autoconfig.clock.option.clockSystem." + name().toLowerCase(Locale.ROOT);
        }
    }

    public int leftOffset = 4;

    public int topOffset = 4;

    public boolean shadow = true;

    @ConfigEntry.ColorPicker
    public int color = 0xFFFFFF;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
    public int opacity = 255;

    public boolean bg = false;

    @ConfigEntry.ColorPicker
    public int bgColor = 0x000000;

    @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
    public int bgOpacity = 150;

    public boolean holdKeyToShowClock = false;
}
