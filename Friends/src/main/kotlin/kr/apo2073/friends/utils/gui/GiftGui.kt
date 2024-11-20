package kr.apo2073.friends.utils.gui

import kr.apo2073.friends.utils.GiftConfigManager
import kr.apo2073.lib.Plugins.txt
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack


class GiftGui:InventoryHolder, Listener {
    private var inv=Bukkit.createInventory(this, 9*6, txt("선물"))
    
    fun getSend(player: Player): Inventory {
        
        return inv
    }
    fun getGet(player: Player): Inventory {
        return loadInventory(player)
    }
    
    fun addItemToGift(item: ItemStack, player: Player) {
        val config=GiftConfigManager(player)
        inv=loadInventory(player)
        config.getStringList("items").forEach { 
            inv.addItem()
        }
    }

    fun loadInventory(player: Player): Inventory {
        val config=GiftConfigManager(player)
        val itemsMap = config.getConfiguration()
            .getConfigurationSection("items.${player.uniqueId}")?.getValues(false)
            ?: return inv
        itemsMap.forEach { (_, value) -> if (value is ItemStack) inv.addItem(value) }
        return inv
    }
    
    override fun getInventory(): Inventory = inv
}

class GistMainGUI:InventoryHolder, Listener {
    val inv=Bukkit.createInventory(this, 9*6, txt("선물"))
    override fun getInventory(): Inventory = inv
}