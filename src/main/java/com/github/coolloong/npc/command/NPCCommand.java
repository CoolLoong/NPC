package com.github.coolloong.npc.command;

import com.github.coolloong.npc.command.subcommand.CreateNPC;
import com.github.coolloong.npc.command.subcommand.RemoveNPC;
import net.minestom.server.command.builder.Command;

public class NPCCommand extends Command {
    public NPCCommand() {
        super("npc");
        addSubcommand(new CreateNPC());
        addSubcommand(new RemoveNPC());
    }
}
