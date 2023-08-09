package com.dillo.gui;

import com.dillo.gui.GUIUtils.Element;
import com.dillo.gui.GUIUtils.totalveins.TotalVeinsMain;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

import static com.dillo.armadillomacro.allOverlays;

public class Overlay {

    public static long curTime = System.currentTimeMillis();
    public static boolean isStartRenderPoints = true;
    public static long startTime = System.currentTimeMillis();
    private static long lastLobbyCheck = System.currentTimeMillis();
    private static boolean isFirst = true;

    private static void draw(String text, int x, int y) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        fontRenderer.drawStringWithShadow(text, x, y, 0xFFFFFF);
    }

    public static void drawWithColor(String text, int x, int y, Color color) {
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRendererObj;
        fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
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

    private static void drawTextInBox(String text1, String text2, int x, int y, int width, int height) {
        drawBoxAround(x, y, width, height);
        draw(text1, x + width / 2, y - height / 2 - 10);
        draw(text2, x + width / 2, y - 25 - height / 2);
    }

    @SubscribeEvent
    public void renderGameOverlay(RenderGameOverlayEvent event) {
        if (event.type != RenderGameOverlayEvent.ElementType.TEXT) {
            return;
        }

        for (Element element : allOverlays) {
            element.guiDraw();
        }

    /*if (timeVein) {
      long currTime = CurTime.curTime();

      if (currTime > 0) {
        drawWithColor("Current Time/Vein: " + currTime + "ms", 10, 20, Color.GREEN);
      } else {
        if (ArmadilloStates.isOnline()) {
          drawWithColor("Current Time/Vein: NONE", 10, 20, Color.GREEN);
        }
      }
    }*/

    /*
    if (started) {
      if (ItemsPickedUp.timePoint + 10000 >= System.currentTimeMillis()) {
        if (GetTotalEarned.totalEarned().totalEarned > 0) {
          GetTotalEarned.TotalEarning earnings = GetTotalEarned.totalEarned();

          String text = "Total Earned: " + earnings.totalEarningString + "$";
          String textPerHour = "Per hour: " + earnings.perHour + "$/hr";
          drawTextInBox(
            text,
            textPerHour,
            config.profitTrackerX,
            config.profitTrackerY,
            30 * config.profitTrackerSize / 2,
            20 * config.profitTrackerSize / 2
          );
        } else {
          started = false;
          GetTotalEarned.clearTotalEarned();
        }
      } else {
        started = false;
        GetTotalEarned.clearTotalEarned();
      }
    }
     */

    /*if (onRouteCheck) {
      if (IsInBlockRange.isInCheckRange()) {
        if (curTime + 10000 > System.currentTimeMillis()) {
          GlStateManager.pushMatrix();
          GlStateManager.scale(4, 4, 4);
          FontRenderer fontRenderer = ids.mc.fontRendererObj;

          fontRenderer.drawStringWithShadow("Use Path Check!", 55, 35, Color.GREEN.getRGB());
          GlStateManager.popMatrix();
        }
      } else {
        curTime = System.currentTimeMillis();
      }
    }*/

    /*if (alrCheckedLobby && !ids.mc.isSingleplayer()) {
      if (isChecked()) {
        if (lastLobbyCheck + 5000 > System.currentTimeMillis()) {
          GlStateManager.pushMatrix();
          GlStateManager.scale(3, 3, 3);
          FontRenderer fontRenderer = ids.mc.fontRendererObj;

          if (alrCheckedLobbySound) {
            playSound(0.5f, 0.5f, "random.orb");
          }

          if (alrCheckedLobbyWarpOut && isFirst) {
            ids.mc.thePlayer.sendChatMessage("/is");
          }

          isFirst = false;
          fontRenderer.drawStringWithShadow("Alr checked lobby!", 10, 50, Color.red.getRGB());
          GlStateManager.popMatrix();
        }
      } else {
        isFirst = true;
        lastLobbyCheck = System.currentTimeMillis();
      }
    }*/

    /*if (failCheckRouteDisplay && isStartRenderPoints) {
      if (startTime + 10000 > System.currentTimeMillis()) {
        int x = 20;
        int y = 40;

        draw("Fail Points: ", x, y);

        if (GetFailPointsList.failListPoints.size() < 6) {
          for (int block : GetFailPointsList.failListPoints) {
            y += 8;
            draw("Point " + ((int) block + 1), x, y);
          }
        } else {
          isStartRenderPoints = false;
          SendChat.chat(prefix.prefix + "To View All the points that might be obstructed, run /obstructedPoints");
        }

        if (GetFailPointsList.failListPoints.size() == 0) {
          SendChat.chat(prefix.prefix + "No Obstruction Points found!");
          isStartRenderPoints = false;
        }
      } else {
        startTime = System.currentTimeMillis();
      }
    }*/

        int totalVeins = new TotalVeinsMain().totalGemsMined();
        if (totalVeins > 0) {
            draw("Total Veins Mined -> " + totalVeins, 50, 50);
        }
    }
}
