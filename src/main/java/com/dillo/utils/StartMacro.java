package com.dillo.utils;

import com.dillo.calls.ArmadilloStates;
import com.dillo.main.failsafes.macro.PlaySoundOnLongStop;
import com.dillo.main.files.localizedData.currentRoute;
import com.dillo.utils.previous.SendChat;
import com.dillo.utils.previous.random.ids;
import com.dillo.utils.previous.random.prefix;
import net.minecraft.util.BlockPos;

import static com.dillo.calls.CurrentState.ARMADILLO;
import static com.dillo.calls.KillSwitch.OFFLINE;
import static com.dillo.calls.KillSwitch.ONLINE;
import static com.dillo.main.macro.main.StateDillo.stateDilloNoGettingOn;
import static com.dillo.utils.BlockUtils.checkIfOnBlock;

public class StartMacro {

    public static boolean isPlayerTurnedOn;

    public static void startMacro() {
        PlaySoundOnLongStop.override = false;
        BlockPos blockUnder = checkIfOnBlock();

        if (blockUnder != null) {
            currentRoute.currentBlock = blockUnder;
            if (InInvItemsCheck.checkItems()) {
                if (ids.mc.thePlayer.isRiding()) {
                    stateDilloNoGettingOn();
                } else {
                    String str = ArmadilloStates.offlineState == OFFLINE
                            ? prefix.prefix + "Starting macro!"
                            : prefix.prefix + "Macro stopped!";
                    ArmadilloStates.offlineState = ArmadilloStates.offlineState == OFFLINE ? ONLINE : OFFLINE;
                    ArmadilloStates.currentState = ArmadilloStates.offlineState == ONLINE ? ARMADILLO : null;
                    isPlayerTurnedOn = ArmadilloStates.offlineState == ONLINE;

                    SendChat.chat(str);
                }
            } else {
                SendChat.chat(prefix.prefix + "Cant start macro! (Some items are not in inventory.)");
            }
        } else {
            SendChat.chat(
                    ArmadilloStates.offlineState == OFFLINE
                            ? prefix.prefix + "Please stand on a block on route!"
                            : prefix.prefix + "Macro stopped!"
            );
            ArmadilloStates.offlineState = OFFLINE;
            ArmadilloStates.currentState = null;
            isPlayerTurnedOn = false;
        }
    }
}
