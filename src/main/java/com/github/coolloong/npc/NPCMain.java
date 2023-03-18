package com.github.coolloong.npc;

import com.github.coolloong.npc.command.NPCCommand;
import com.github.coolloong.npc.npc.NPCManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.extensions.Extension;

public class NPCMain extends Extension {
    public static NPCMain INSTANCE;
    public NPCManager NPC_MANAGER;

    @Override
    public void preInitialize() {
        this.getLogger().info("NPCPlayer Extension loading...");
        INSTANCE = this;
        NPC_MANAGER = new NPCManager();
    }

    @Override
    public void initialize() {
        CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new NPCCommand());
    }

    @Override
    public void terminate() {
    }
}
