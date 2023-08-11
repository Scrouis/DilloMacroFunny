package com.dillo.pathfinding.Brigeros;

import com.dillo.calls.ArmadilloStates;
import com.dillo.calls.CurrentState;
import com.dillo.utils.previous.packets.getBlockEnum;
import com.dillo.utils.previous.packets.sendStart;
import com.dillo.utils.previous.packets.sendStop;
import com.dillo.utils.previous.random.ids;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class DestroyBlock {

    private static CurrentState newDilloState = null;
    private static List<BlockPos> blocks = null;
    private static BlockPos blockPosition = null;
    private static boolean state = false;

    private static boolean canSend = false;

    public static void destroyBlock(List<BlockPos> blocksToBreak, CurrentState newState, boolean startStop) {
        blocks = blocksToBreak;
        newDilloState = newState;

        blockPosition = blocksToBreak.get(0);
        blocks.remove(0);

        state = startStop;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (state && blockPosition != null) {
                if (canSend) {
                    sendStop.sendStopPacket(blockPosition, getBlockEnum.getEnum(blockPosition));
                    canSend = false;

                    if (ids.mc.theWorld.getBlockState(blockPosition).getBlock() == Blocks.air) {
                        if (blocks.size() > 0) {
                            blockPosition = blocks.get(0);
                            //SendChat.chat(String.valueOf(blockPosition) + "!!!");
                            blocks.remove(0);
                        } else {
                            state = false;
                            ArmadilloStates.currentState = newDilloState;
                        }
                    }
                }

                //SendChat.chat(String.valueOf(blocks.size()) + "~`");

                if (ids.mc.theWorld.getBlockState(blockPosition).getBlock() != Blocks.air) {
                    sendStart.sendStartPacket(blockPosition, getBlockEnum.getEnum(blockPosition));
                    canSend = true;
                } else {
                    if (blocks.size() > 0) {
                        blockPosition = blocks.get(0);
                        //SendChat.chat(String.valueOf(blockPosition) + "???");
                        blocks.remove(0);
                    } else {
                        state = false;
                        ArmadilloStates.currentState = newDilloState;
                    }
                }
            }
        }
    }
}
