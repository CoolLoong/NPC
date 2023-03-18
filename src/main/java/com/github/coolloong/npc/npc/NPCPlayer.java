package com.github.coolloong.npc.npc;

import com.github.coolloong.npc.Constants;
import com.github.coolloong.npc.NPCMain;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.entity.fakeplayer.FakePlayer;
import net.minestom.server.entity.fakeplayer.FakePlayerOption;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class NPCPlayer extends FakePlayer {
    private static final PlayerSkin DEFAULT_SKIN = new PlayerSkin("ewogICJ0aW1lc3RhbXAiIDogMTY3OTEyMDQ1OTUxMSwKICAicHJvZmlsZUlkIiA6ICI3NzhkNzNiYzdhMDU0ZWE1OWQxNmZkZWM1ZTE3Nzg0OCIsCiAgInByb2ZpbGVOYW1lIiA6ICJQZWVHcmF2ZWwiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVjNGVlNWNlMjBhZWQ5ZTMzZTg2NmM2NmNhYTM3MTc4NjA2MjM0YjM3MjEwODRiZjAxZDEzMzIwZmIyZWIzZiIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9", "RRtDpJYXGvyTXWMs+34JfTfDHXqjDDOx6RxAfAuV9bDaFKwZPUWrK7eqC/GAkt+fjsuIdealHlzh33cxLk0s/7YZvn3wbjlrlM0MSG1jfX0TXazRcWLGtFtKVfBlx4e5Z/kZ4rBDTonmhV2AFa9UGRsGPT2ryZSFJleYgZiMGeFV/Mul6A3VVJnSVuKHqk5eKe0/n5sdJb+UwGNiINcvHeTJq1n4esUS2Wa6leQTt0CncuxqqBLdTTvs00Tpozs1TFz9B3tYXPE8U6A1FXEd3Z9StM0ZlADgiwBaVyEOnjoQDu6SXQwKk0dUYQ+QN1BF4aul3z+nWKVvgmtG6Lu0iIhqbMv6n//+xbuGzxF3MNcHWFjqHBCPQY3JnrsbJ8249YVPrQneXGqHQ+bEbR/uLzJpG9RQPja2SAqzksTrmoAMhq2l+2P+OQvA6AB7nZCSajJZvbiv/4PAgzrLHI0UNHpxSJSBoAtGibrkJ7XkZboFTyrSReXF11Ts0/0Y/WfH/VzU3KEEZXZHQhoq793ux4mU73ITuS8fjUWTQpDcZVDS27Uqq96o/jAAxNxSz3dCo0Ehj05UQTUTz4hnfiZrQsRQrqn3INAQjOpcByJjrCKDKIQu5vUqY+twi/Scjt7KmkWnOweKIPfjytEgzLd2dQerA4Nln21pF9BmqAkTFK0=");

    protected NPCPlayer(@NotNull UUID uuid, @NotNull String username, @NotNull Instance level, @NotNull Pos pos, @NotNull ItemStack item, @Nullable PlayerSkin skin) {
        super(uuid, username, new FakePlayerOption(), player -> {
            if (skin != null) {
                player.setSkin(skin);
            } else player.setSkin(DEFAULT_SKIN);
            player.setCanPickupItem(false);
            player.setGameMode(GameMode.CREATIVE);
            if (!Objects.equals(player.getInstance(), level)) {
                player.setInstance(level, pos);
            } else player.teleport(pos);
            player.setRespawnPoint(pos);
            player.setItemInHand(Hand.MAIN, item);
        });
    }

    public static void create(@NotNull UUID uuid, @NotNull String username, @NotNull Instance level, @NotNull Pos pos, @NotNull ItemStack item, @Nullable PlayerSkin skin) {
        NPCMain.INSTANCE.NPC_MANAGER.add(new NPCPlayer(uuid, username, level, pos, item, skin));
    }

    @Override
    public void update(long time) {
        super.update(time);
        if (this.isActive()) {
            var entities = this.getInstance().getNearbyEntities(this.getPosition(), Constants.CHECK_NEAREST_PLAYER_RANGE);
            var nearestPlayer = (Player) entities.stream()
                    .filter(e -> e instanceof Player && !(e instanceof FakePlayer))
                    .min((e1, e2) -> Double.compare(e1.getPosition().distanceSquared(this.getPosition()), e2.getPosition().distanceSquared(this.getPosition())))
                    .orElse(null);
            if (nearestPlayer != null) {
                var direction = this.getPosition().withLookAt(nearestPlayer.getPosition());
                this.getController().rotate(direction.yaw(), direction.pitch());
            }
        }
    }
}
