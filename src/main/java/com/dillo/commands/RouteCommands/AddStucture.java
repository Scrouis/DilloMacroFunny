package com.dillo.commands.RouteCommands;

import com.dillo.main.files.localizedData.currentRoute;
import com.dillo.main.files.readwrite.ReWriteFile;
import com.dillo.utils.previous.random.ids;
import gg.essential.api.commands.Command;
import gg.essential.api.commands.DefaultHandler;
import net.minecraft.util.BlockPos;

public class AddStucture extends Command {

    public AddStucture() {
        super("addStructure");
    }

    @DefaultHandler
    public void handle() {
        BlockPos block = new BlockPos(ids.mc.thePlayer.posX, ids.mc.thePlayer.posY, ids.mc.thePlayer.posZ);
        currentRoute.strucList.add(block);
        //SendChat.chat(currentRoute.strucList.size() + "SO");
        ReWriteFile.reWriteFile(currentRoute.currentRouteFile);
    }
}
