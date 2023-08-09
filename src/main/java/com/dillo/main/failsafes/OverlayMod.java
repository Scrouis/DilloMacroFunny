package com.dillo.main.failsafes;

import com.dillo.calls.ArmadilloStates;
import com.dillo.calls.CurrentState;
import com.dillo.calls.KillSwitch;
import com.dillo.config.config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OverlayMod {

    private static void draw(String text, int x, int y) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        fontRenderer.drawStringWithShadow(text, x, y, 0xFFFFFF);
    }

    private static void drawBoxAround(int x, int y, int width, int height) {
        StringBuilder topText = new StringBuilder();

        for (int i = 0; i < width; i++) {
            topText.append("_");
        }

        draw(topText.toString(), x, y - height * 3 - 5);

        int currDepth = y;

        for (int i = 0; i < height; i++) {
            draw("|", x, currDepth);
            currDepth -= 3;
        }

        draw(topText.toString(), x, y);

        int currDepth1 = y;
        for (int i = 0; i < height; i++) {
            draw("|", x + width * 6, currDepth1);
            currDepth1 -= 3;
        }
    }

    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }

        //drawBoxAround(config.profitTrackerX, config.profitTrackerY, 30 * config.profitTrackerSize / 2, 20 * config.profitTrackerSize / 2);

        if (config.TPSOverlay) {
            double serverTPS = ServerTPSFailsafe.getCurrentTPS();
            String overlayText = "TPS: " + serverTPS;
            draw(overlayText, 10, 10);
        }

        if (config.currentActionOverlay) {
            CurrentState currState = ArmadilloStates.currentState;
            KillSwitch currOnOffState = ArmadilloStates.offlineState;

            if (currState != null) {
                switch (currState) {
                    case ARMADILLO:
                        draw("Getting On Armadillo", config.overlayX, config.overlayY);
                        return;
                    case SPINDRIVE:
                        draw("Spiny Spiny", config.overlayX, config.overlayY);
                        return;
                    case TPSTAGE2:
                    case TPSTAGE3:
                        draw("Teleporting!", config.overlayX, config.overlayY);
                        return;
                    case STARTWALKINGPATH:
                    case RESUMEWALKING:
                        draw("Walking On Path...", config.overlayX, config.overlayY);
                        return;
                    case RESTARTPATHFINDER:
                        draw("Restarting Pathfinding...", config.overlayX, config.overlayY);
                        return;
                    case NEXTBLOCKSTAGE2:
                        draw("Still Teleporting :/", config.overlayX, config.overlayY);
                        return;
                    case STARTMACRO:
                        draw("Starting Macro!", config.overlayX, config.overlayY);
                        return;
                    case PLAYER_DETECTION:
                        draw("Detected Player!", config.overlayX, config.overlayY);
                        return;
                    case REFUELING:
                        draw("Refueling!", config.overlayX, config.overlayY);
                        return;
                }
            }

            if (currState == null && currOnOffState == KillSwitch.OFFLINE) {
                draw("Idle", config.overlayX, config.overlayY);
                return;
            } else if (currState == null && currOnOffState == KillSwitch.ONLINE) {
                draw("Idle or doing smh idk", config.overlayX, config.overlayY);
                return;
            }
        }
    }
}
