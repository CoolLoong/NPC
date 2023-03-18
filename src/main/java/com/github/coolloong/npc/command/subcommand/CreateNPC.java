package com.github.coolloong.npc.command.subcommand;

import com.github.coolloong.npc.NPCMain;
import com.github.coolloong.npc.npc.NPCPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.item.ItemStack;

import java.util.UUID;

public class CreateNPC extends Command {
    private final static UUID DEFAULT_SKIN_UUID = UUID.fromString("A5596EB5-171C-CB43-4F4B-3ECF667AF1B7");
    private final static String DEFAULT_SKIN_NAME = "null";

    public CreateNPC() {
        super("create");
        var nameArgument = ArgumentType.String("npc-name");
        var posArgument = ArgumentType.RelativeVec3("npc-pos");
        var skinUuidArgument = ArgumentType.UUID("npc-skin-uuid").setDefaultValue(DEFAULT_SKIN_UUID);
        var skinNameArgument = ArgumentType.String("npc-skin-name").setDefaultValue(DEFAULT_SKIN_NAME);
        nameArgument.setCallback(((sender, exception) -> {
            final String input = exception.getInput();
            sender.sendMessage("The number " + input + " is invalid!");
        }));
        addSyntax(((sender, context) -> {
            if (sender instanceof Player player) {
                var a1 = context.get(nameArgument);
                if (NPCMain.INSTANCE.NPC_MANAGER.has(a1)) {
                    sender.sendMessage(Component.text("This NPC already exists!", NamedTextColor.RED));
                    return;
                }
                var a2 = context.get(posArgument);
                var a3 = context.get(skinUuidArgument);
                if (player.getInstance() == null) {
                    return;
                }
                if (a3 == DEFAULT_SKIN_UUID) {
                    NPCPlayer.create(UUID.randomUUID(), a1, player.getInstance(), a2.fromSender(player).asPosition(), ItemStack.AIR, null);
                } else {
                    var skin = PlayerSkin.fromUuid(a3.toString());
                    if (skin == null) {
                        sender.sendMessage(Component.text("Skin not present!", NamedTextColor.RED));
                        return;
                    }
                    NPCPlayer.create(UUID.randomUUID(), a1, player.getInstance(), a2.fromSender(player).asPosition(), ItemStack.AIR, skin);
                }
            }
        }), nameArgument, posArgument, skinUuidArgument);

        addSyntax(((sender, context) -> {
            if (sender instanceof Player player) {
                var a1 = context.get(nameArgument);
                if (NPCMain.INSTANCE.NPC_MANAGER.has(a1)) {
                    sender.sendMessage(Component.text("This NPC already exists!", NamedTextColor.RED));
                    return;
                }
                var a2 = context.get(posArgument);
                var a3 = context.get(skinNameArgument);
                if (player.getInstance() == null) {
                    return;
                }
                if (a3.equals(DEFAULT_SKIN_NAME)) {
                    NPCPlayer.create(UUID.randomUUID(), a1, player.getInstance(), a2.fromSender(player).asPosition(), ItemStack.AIR, null);
                } else {
                    var skin = PlayerSkin.fromUsername(a3);
                    if (skin == null) {
                        sender.sendMessage(Component.text("Skin not present!", NamedTextColor.RED));
                        return;
                    }
                    NPCPlayer.create(UUID.randomUUID(), a1, player.getInstance(), a2.fromSender(player).asPosition(), ItemStack.AIR, skin);
                }
            }
        }), nameArgument, posArgument, skinNameArgument);
    }
}
