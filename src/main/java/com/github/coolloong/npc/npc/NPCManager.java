package com.github.coolloong.npc.npc;

import net.minestom.server.entity.fakeplayer.FakePlayer;
import net.minestom.server.tag.Tag;

import java.util.concurrent.ConcurrentHashMap;

public class NPCManager {
    private final ConcurrentHashMap<String, FakePlayer> cacheNpc = new ConcurrentHashMap<>();

    public void add(FakePlayer npc) {
        cacheNpc.putIfAbsent(npc.getUsername(), npc);
    }

    public void remove(String name) {
        cacheNpc.remove(name).getPlayerConnection().disconnect();
    }

    public boolean has(String name) {
        return cacheNpc.containsKey(name);
    }
}
