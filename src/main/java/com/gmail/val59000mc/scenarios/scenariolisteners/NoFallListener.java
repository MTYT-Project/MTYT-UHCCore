package com.gmail.val59000mc.scenarios.scenariolisteners;

import com.gmail.val59000mc.game.GameManager;
import com.gmail.val59000mc.game.GameState;
import com.gmail.val59000mc.scenarios.ScenarioListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallListener extends ScenarioListener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                if (GameManager.getGameManager().getGameState().equals(GameState.PLAYING) && !GameManager.getGameManager().getPvp()) {
                    e.setCancelled(true);
                }
            }

        }
    }
}