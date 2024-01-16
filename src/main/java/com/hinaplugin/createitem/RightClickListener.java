package com.hinaplugin.createitem;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/*
*  Listenerはイベント（プレイヤーがジャンプした，Mobがスポーンしたなど）を受け取ります
*  つまりコマンドによって動くコードではなくアクションによって動くコードです
*  Listenerは様々な種類があるが必ずorg.bukkit.eventと右に表示されているものを選択
* */
public class RightClickListener implements Listener{

    //PlayerInteractEvent: 左クリックまたは右クリックを検出したら動くイベント
    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        //イベントを起こしたプレイヤーを取得
        Player player = event.getPlayer();
        //クリック情報を取得
        Action action = event.getAction();
        //クリックされたブロックを取得
        Block block = event.getClickedBlock();

        //クリックされたのが空中かどうかの判定
        if (block == null){
            //空中であれば処理終了
            return;
        }

        //クリックが右クリックかの判定
        if (action != Action.RIGHT_CLICK_BLOCK){
            //ブロックを右クリックしていない場合は処理終了
            return;
        }

        //右クリックしたときにメインハンド（右手）に持っているアイテムを取得
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        //右手にアイテムを持っているかの判定
        if (itemStack.getType().isAir()){
            //アイテムを持っていない（空気を持っている）ならば処理終了
            return;
        }

        //持っているアイテムがダイヤモンドのシャベルか判定
        if (itemStack.getType() != Material.DIAMOND_SHOVEL){
            //ダイヤモンドのシャベルでなければ処理終了
            return;
        }

        //鍵のようなものを生成
        NamespacedKey namespacedKey = new NamespacedKey(CreateItem.getPlugin(), "create");
        //詳細情報のカスタムデータを取得
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        //鍵が一致しているかを確認
        if ("ci".equalsIgnoreCase(persistentDataContainer.get(namespacedKey, PersistentDataType.STRING))){
            //一致していたら処理
            block.setType(Material.DIAMOND_BLOCK);
        }
    }
}
