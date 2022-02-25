package me.pixxel.clock.mixins;

import me.pixxel.clock.ClockMod;
import me.pixxel.clock.config.ClockConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(at = @At("TAIL"), method = "render")
    public void renderClock(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        MinecraftClient mc = MinecraftClient.getInstance();
        ClockConfig config = ClockMod.CONFIG;

        if (!mc.options.debugEnabled && config.enabled && ClockMod.SHOW_CLOCK) {

            DateTimeFormatter dtf12 = DateTimeFormatter.ofPattern("hh:mm a");
            DateTimeFormatter dtf24 = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime now = LocalDateTime.now();
            String time = null;
            if(config.timeSystem == ClockConfig.TimeSystem.HR12) {
                time = dtf12.format(now);
            } else {
                time = dtf24.format(now);
            }

            int posX = config.leftOffset;
            int posY = config.topOffset;

            TextRenderer renderer = mc.textRenderer;
            int textColor = config.color | ((config.opacity & 0xFF) << 24);
            int bgColor = config.bgColor | ((config.bgOpacity & 0xFF) << 24);

            //With shadow, with bg
            if (config.shadow && config.bg) {
                if(config.timeSystem == ClockConfig.TimeSystem.HR12) {
                    DrawableHelper.fill(matrices, posX, posY, posX + 46, posY + 14, bgColor);
                    renderer.drawWithShadow(matrices, time, posX + 23 - renderer.getWidth(time) / 2, posY + 3, textColor);
                } else {
                    DrawableHelper.fill(matrices, posX, posY, posX + 32, posY + 14, bgColor);
                    renderer.drawWithShadow(matrices, time, posX + 16 - renderer.getWidth(time) / 2, posY + 3, textColor);
                }
            //Without shadow, without bg
            } else if (!config.shadow && !config.bg) {
                renderer.draw(matrices, time, posX, posY, textColor);
            //Without shadow, with bg
            } else if (!config.shadow) {
                if(config.timeSystem == ClockConfig.TimeSystem.HR12) {
                    DrawableHelper.fill(matrices, posX, posY, posX + 45, posY + 13, bgColor);
                    renderer.draw(matrices, time, posX + 23 - renderer.getWidth(time) / 2, posY + 3, textColor);
                } else {
                    DrawableHelper.fill(matrices, posX, posY, posX + 31, posY + 13, bgColor);
                    renderer.draw(matrices, time, posX + 16 - renderer.getWidth(time) / 2, posY + 3, textColor);
                }
            //With shadow, without bg
            } else {
                renderer.drawWithShadow(matrices, time, posX, posY, textColor);
            }
        }
    }

}
