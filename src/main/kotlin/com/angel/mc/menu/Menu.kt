package com.angel.mc.menu

import com.angel.mc.KeySet.COLOR.ULTRAMARINE_BLUE
import com.angel.mc.dao.PlayerMapper
import com.angel.mc.dao.PlayerTable
import com.mybatisflex.core.query.QueryWrapper
import com.mybatisflex.kotlin.extensions.db.mapper
import com.mybatisflex.kotlin.extensions.kproperty.eq
import dev.triumphteam.gui.builder.item.ItemBuilder
import dev.triumphteam.gui.guis.Gui
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.minecraft.server.level.PlayerMap
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class Menu(
    private val player: Player,
    private val row: Int=6,
    private val title: TextComponent = Component.text("Menu")
) {
    var gui: Gui = Gui.gui()
        .title(title)
        .rows(row)
        .disableAllInteractions()
        .create()

    init {
        gui.setDefaultClickAction {  }
        generateIcon()
    }
    fun generateIcon() {
        val playerInfo = mapper<PlayerMapper>()
            .selectOneWithRelationsByQuery(
                QueryWrapper().where(PlayerTable::uuid eq player.uniqueId.toString())
            )
        val infoIcon = ItemBuilder.from(Material.DANDELION)
            .name(Component.text("基本信息").color(TextColor.fromHexString(ULTRAMARINE_BLUE)))
            .lore(
            listOf(Component.text("金钱：${playerInfo?.money?.money}"))
        ).asGuiItem{  }
        gui.addItem(infoIcon)
    }

    fun open() {
        gui.open(player)
    }
}

fun Player.openMenu() {
    val menu = Menu(this)
    menu.open()
}
