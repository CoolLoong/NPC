package com.github.coolloong.npc.command.subcommand;

import com.github.coolloong.npc.NPCMain;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class RemoveNPC extends Command {
    public RemoveNPC() {
        super("remove");
        var nameArgument = ArgumentType.String("npc-name");
        addSyntax(((sender, context) -> {
            var name = context.get(nameArgument);
            if (NPCMain.INSTANCE.NPC_MANAGER.has(name)) {
                NPCMain.INSTANCE.NPC_MANAGER.remove(name);
            } else {
                sender.sendMessage(Component.text("This NPC does not exist!", NamedTextColor.RED));
            }
        }), nameArgument);
    }
}
