package dev.mv.mobarmy;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;

public class Timer implements CommandExecutor {
    private long time;
    private BukkitTask loopTask, finishTask;
    private static boolean running;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // timer already exists as a part of mc
        if (args.length != 1) {
            sender.sendMessage("Usage: /" + command + " <time in format [xxh][xxm][xxs]>");
        }

        String arg = args[0];

        if (arg.equals("stop")) {
            loopTask.cancel();
            finishTask.cancel();
            sendActionbar("Timer stopped");
            running = false;
            return false;
        }

        running = true;

        long time = parseTime(arg);
        this.time = time;

        loopTask = Bukkit.getScheduler().runTaskTimer(MobArmyMod.instance(), () -> {
            Timer.this.time--;
            updateActionbar();
        }, 20, 20); // 20 ticks = 1 second //bro and 1 + 1 = 2 you knew?

        finishTask = Bukkit.getScheduler().runTaskLater(MobArmyMod.instance(), () -> {
            loopTask.cancel();
            running = false;
            // after the timer is done
            //for the loop, we can make a different one and stop it from here
            timerFinished();
            Teams.timerFinished();
        }, time * 20);

        return false;
    }

    private String getTimeFormatted() {
        long hours = time / 3600;
        long minutes = (time % 3600) / 60;
        long seconds = time % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void updateActionbar() {
        String timeString = getTimeFormatted();
        sendActionbar(timeString);
    }

    private void timerFinished() {
        sendActionbar("Timer finished");
    }

    private void sendActionbar(String msg) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            int team = Teams.getTeam(player);
            if (team == -1) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(msg));
            } else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.chat("&2Team " + team + " &7- &6" + msg)));
            }
        });
    }

    private long parseTime(String input) {
        String hoursStr = "0";
        String minutesStr = "0";
        String secondsStr = "0";

        StringBuilder builder = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c == 'h') {
                hoursStr = builder.toString();
                builder.setLength(0);
            }
            else if (c == 'm') {
                minutesStr = builder.toString();
                builder.setLength(0);
            }
            else if (c == 's') {
                secondsStr = builder.toString();
                builder.setLength(0);
            } else {
                builder.append(c);
            }
        }

        int hours = Integer.parseInt(hoursStr);
        int minutes = Integer.parseInt(minutesStr);
        int seconds = Integer.parseInt(secondsStr);

        return seconds + minutes * 60L + hours * 3600L;
    }

    public static boolean running() {
        return running;
    }
}
