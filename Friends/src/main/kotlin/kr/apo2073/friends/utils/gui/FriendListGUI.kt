package kr.apo2073.friends.utils.gui

import kr.apo2073.chatGroup.cmds.ChattingGroupCmd
import kr.apo2073.chatGroup.utils.setGroup
import kr.apo2073.friends.enums.FriendStat
import kr.apo2073.friends.utils.friends
import kr.apo2073.lib.Items.ItemBuilder
import kr.apo2073.lib.Plugins.txt
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemFlag
import kotlin.random.Random

class FriendListGUI: InventoryHolder, Listener {
    private var inv=Bukkit.createInventory(this, 9*6, txt("친구 목록"))
    
    fun get(player: Player):Inventory {
        val list=player.friends().getFriendList(FriendStat.TOTAL)
        for (i in 43..53) inv.setItem(i, ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
            .addItemFlag(ItemFlag.HIDE_ITEM_SPECIFICS).setItemName(txt(""))
            .build())
        return inv.apply { 
            list.forEach { name-> val frds=Bukkit.getPlayer(name) ?: return@forEach
                addItem(ItemBuilder(Material.PLAYER_HEAD)
                    .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                    .setOwner(name).setDisplayName("${
                    if (frds.isOnline) "§a"
                    else "§c"}${name}").build()) }
        }
    }
    
    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (e.inventory.getHolder(false) !is FriendListGUI) return
        val ci=e.clickedInventory ?: return
        if (ci.type.equals(InventoryType.PLAYER)) return
        val citem=e.currentItem ?: return
        val meta=citem.itemMeta
        val player=e.view as Player
        e.isCancelled=true
        if (citem.type==Material.PLAYER_HEAD) {
            if (meta.hasEnchants()) meta.removeEnchant(Enchantment.LUCK)
            else {
                ci.forEach { i-> if (i.itemMeta.hasEnchants() 
                    && i!=null && i.hasItemMeta()) i.removeEnchantment(Enchantment.LUCK) }
                meta.addEnchant(Enchantment.LUCK, 1, false)
                e.inventory.setItem(48, ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                    .setDisplayName("§c친구 삭제").build())
                e.inventory.setItem(49, ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                    .setDisplayName("§b일대일 채팅 열기").build())
                e.inventory.setItem(50, ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
                    .setDisplayName("§6취소").build())
            }
        }
        if (citem.type==Material.RED_STAINED_GLASS_PANE) {
            val frName=e.inventory.contents.firstOrNull { it != null && it.itemMeta?.hasEnchants() ?: return }
                ?.displayName()?.examinableName()?.removeRange(0,1) ?: return
            player.friends().removeFriend(Bukkit.getPlayer(frName) ?: return)
            e.inventory.contents=get(player).contents
        }
        if (citem.type==Material.LIGHT_BLUE_STAINED_GLASS_PANE) {
            player.closeInventory()
            if (Bukkit.getPluginManager().getPlugin("ChatGroup")==null) {
                player.sendMessage(MiniMessage.miniMessage()
                    .deserialize("<gradient:#CAC0F8:#A28FFC>[ CHAT ] </gradient>")
                    .append(txt("지원 되지 않는 기능 입니다")))
                return
            }
            val ra=Random(12)
            player.setGroup("친구채팅${ra}")
            val fr=Bukkit.getPlayer(e.inventory.contents.firstOrNull { it != null && it.itemMeta?.hasEnchants() ?: return }
                ?.displayName()?.examinableName()?.removeRange(0,1) ?: return) ?: return
            fr.sendMessage(ChattingGroupCmd().perfix
                .append(txt("친구 ${player.name}이(가) 일대일 채팅 초대를 보냈습니다"))
                .append(txt(" [ 초대받기 ]").clickEvent(ClickEvent.runCommand("/그룹 참여 친구채팅$ra")))
            )
            player.sendMessage(ChattingGroupCmd().perfix
                .append(txt("친구가 일대일 채팅에 들어올 때 까지 잠시만 기다려주세요")))
        }
        if (citem.type==Material.ORANGE_STAINED_GLASS_PANE) e.inventory.contents=get(player).contents
    }
    @EventHandler
    fun onClose(e: InventoryCloseEvent) {
        if (e.inventory.getHolder(false) is FriendListGUI) return
        (e.view as Player).openInventory(FriendMainGUI().get(e.view as Player))
    }
    
    override fun getInventory(): Inventory = inv
}