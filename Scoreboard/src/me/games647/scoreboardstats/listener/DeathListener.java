package me.games647.scoreboardstats.listener;


import me.games647.scoreboardstats.api.Database;
import me.games647.scoreboardstats.api.Score;
import org.bukkit.craftbukkit.v1_5_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public final class DeathListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onDeath(final org.bukkit.event.entity.PlayerDeathEvent death) {

        final Player killed = death.getEntity();
        final Player killer = killed.getKiller();

        if (killer != null) {

            Score.update(
                    ((CraftPlayer) killer).getHandle().playerConnection
                    , true
                    , Database.increaseKills(killer.getName()));

            Score.update(
                    ((CraftPlayer) killed).getHandle().playerConnection
                    , false
                    , Database.increaseDeaths(killed.getName()));
        }
    }

    @EventHandler
    public void onJoin(final org.bukkit.event.player.PlayerJoinEvent join) {

        final me.games647.scoreboardstats.api.PlayerStats stats = Database.checkAccount(join.getPlayer().getName());

        Score.createScoreboard(
                ((CraftPlayer) join.getPlayer()).getHandle().playerConnection
                , stats.getKills()
                , stats.getDeaths());
    }
}