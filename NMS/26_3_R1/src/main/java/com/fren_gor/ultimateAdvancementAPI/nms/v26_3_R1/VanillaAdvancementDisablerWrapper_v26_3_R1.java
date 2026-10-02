package com.fren_gor.ultimateAdvancementAPI.nms.v26_3_R1;

import com.fren_gor.ultimateAdvancementAPI.nms.wrappers.VanillaAdvancementDisablerWrapper;
import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.AdvancementTree;

import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class VanillaAdvancementDisablerWrapper_v26_3_R1 extends VanillaAdvancementDisablerWrapper {

    private static Logger LOGGER = null;
    private static Field firstPacket;

    static {
        try {
            firstPacket = Arrays.stream(PlayerAdvancements.class.getDeclaredFields()).filter(f -> f.getType() == boolean.class).findFirst().orElseThrow();
            firstPacket.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Field logger = Arrays.stream(AdvancementTree.class.getDeclaredFields()).filter(f -> f.getType() == org.slf4j.Logger.class).findFirst().orElseThrow();
            logger.setAccessible(true);
            org.slf4j.Logger slf4jLogger = (org.slf4j.Logger) logger.get(null);
            LOGGER = Arrays.stream(slf4jLogger.getClass().getDeclaredFields()).filter(f -> !Modifier.isStatic(f.getModifiers())).map(f -> {
                try {
                    f.setAccessible(true);
                    if (f.get(slf4jLogger) instanceof Logger log) {
                        return log;
                    }
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(Objects::nonNull).findFirst().orElseThrow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disableVanillaAdvancements(boolean vanillaAdvancements, boolean vanillaRecipeAdvancements) throws Exception {
        ServerAdvancementManager serverAdvancements = ((CraftServer) Bukkit.getServer()).getServer().getAdvancements();
        AdvancementTree tree = serverAdvancements.tree();
        if (serverAdvancements.advancements.isEmpty()) return;

        Set<Identifier> locations = new HashSet<>();
        ImmutableMap.Builder<Identifier, AdvancementHolder> builder = ImmutableMap.builder();
        for (var entry : serverAdvancements.advancements.entrySet()) {
            Identifier key = entry.getKey();
            boolean isRecipe = key.getPath().startsWith("recipes/");
            if (key.getNamespace().equals("minecraft") &&
                    ((vanillaAdvancements && !isRecipe) || (vanillaRecipeAdvancements && isRecipe))) {
                locations.add(key);
            } else {
                builder.put(key, entry.getValue());
            }
        }
        if (locations.isEmpty()) return;
        // Spigot 26.3 exposes a final mutable map; Paper forks may retain an
        // immutable map in a non-final field instead.
        try {
            serverAdvancements.advancements.keySet().removeAll(locations);
        } catch (UnsupportedOperationException e) {
            Field advancements = ServerAdvancementManager.class.getDeclaredField("advancements");
            advancements.setAccessible(true);
            advancements.set(serverAdvancements, new HashMap<>(builder.buildOrThrow()));
        }

        // 26.3 removed AdvancementTree.Listener. Compare the node sets to include
        // descendants removed recursively by AdvancementTree.remove().
        Set<Identifier> removed = new HashSet<>();
        for (AdvancementNode node : tree.nodes()) removed.add(node.holder().id());
        final Level oldLevel = disableLogger();
        try {
            tree.remove(locations);
        } finally {
            enableLogger(oldLevel);
        }
        for (AdvancementNode node : tree.nodes()) removed.remove(node.holder().id());

        final var removePacket = new ClientboundUpdateAdvancementsPacket(false,
                Collections.emptyList(), removed, Collections.emptyMap(), false);
        for (Player player : Bukkit.getOnlinePlayers()) {
            var mcPlayer = ((CraftPlayer) player).getHandle();
            var advs = mcPlayer.getAdvancements();
            advs.reload(serverAdvancements);
            firstPacket.setBoolean(advs, false);
            mcPlayer.connection.send(removePacket);
        }
    }

    private static Level disableLogger() {
        if (LOGGER == null) // Fail-safe if LOGGER could not be found by reflections
            return null;

        Level old = LOGGER.getLevel();

        // Method setLevel is not present in Logger interface
        if (LOGGER instanceof org.apache.logging.log4j.core.Logger coreLogger) {
            coreLogger.setLevel(Level.OFF);
        } else if (LOGGER instanceof SimpleLogger simple) {
            simple.setLevel(Level.OFF);
        }

        return old;
    }

    private static void enableLogger(Level toSet) {
        if (LOGGER == null || toSet == null) // Fail-safe if LOGGER could not be found by reflections
            return;

        // Method setLevel is not present in Logger interface
        if (LOGGER instanceof org.apache.logging.log4j.core.Logger coreLogger) {
            coreLogger.setLevel(toSet);
        } else if (LOGGER instanceof SimpleLogger simple) {
            simple.setLevel(toSet);
        }
    }

    private VanillaAdvancementDisablerWrapper_v26_3_R1() {
        throw new UnsupportedOperationException("Utility class.");
    }
}
