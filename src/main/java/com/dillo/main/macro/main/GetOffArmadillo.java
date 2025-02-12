package com.dillo.main.macro.main;

import static com.dillo.armadillomacro.regJump;

import com.dillo.calls.ArmadilloStates;
import com.dillo.calls.CurrentState;
import com.dillo.main.teleport.macro.TeleportToNextBlock;
import com.dillo.utils.previous.SendChat;
import com.dillo.utils.previous.random.ids;
import com.dillo.utils.throwRod;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GetOffArmadillo {

  private static final KeyBinding SNEAK = Minecraft.getMinecraft().gameSettings.keyBindSneak;
  private static double blockYPos = -34;
  private static boolean startOff = false;
  private static int ammountOfCheckTicks = 0;
  private static int currTicks = 0;
  private static boolean sneak = false;
  private final int MIN_DELAY_AMOUNT = 0;
  private final int MAX_DELAY_AMOUNT = 60;
  private final Random random = new Random();

  public static void getOffArmadillo(CurrentState newState, double blockY, int amountOfTicks, boolean turnOffSneak) {
    throwRod.throwRodInv();
    ArmadilloStates.currentState = null;
    sneak = turnOffSneak;

    regJump.reset();

    blockYPos = blockY;
    startOff = true;
    ammountOfCheckTicks = amountOfTicks;

    KeyBinding.setKeyBindState(SNEAK.getKeyCode(), true);
  }

  @SubscribeEvent
  public void onTick(TickEvent.ClientTickEvent event) {
    if (event.phase == TickEvent.Phase.END) {
      if (startOff) {
        if (currTicks <= ammountOfCheckTicks) {
          if (ArmadilloStates.isOnline()) {
            if (Math.abs(blockYPos - ids.mc.thePlayer.posY + 1) < 0.0001) {
              new Thread(() -> {
                swapWithRandomDelay();
                regJump.startStop(false);
                if (sneak) KeyBinding.setKeyBindState(SNEAK.getKeyCode(), false);
                startOff = false;
                currTicks = 0;
                ArmadilloStates.currentState = null;

                TeleportToNextBlock.teleportToNextBlockStage2();
              })
                .start();
            }
          } else {
            startOff = false;
            currTicks = 0;
            blockYPos = -34;
          }
        } else {
          SendChat.chat("Failed to get off the dillo.");
          KeyBinding.setKeyBindState(SNEAK.getKeyCode(), false);
          startOff = false;
          currTicks = 0;
          ArmadilloStates.currentState = null;
        }

        currTicks++;
      }
    }
  }

  private void swapWithRandomDelay() {
    try {
      int delay = MIN_DELAY_AMOUNT + random.nextInt(MAX_DELAY_AMOUNT - MIN_DELAY_AMOUNT + 1);
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
