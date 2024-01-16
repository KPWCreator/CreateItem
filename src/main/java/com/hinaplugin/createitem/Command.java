package com.hinaplugin.createitem;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //コマンド送信者がプレイヤーかどうかチェック
        if (commandSender instanceof Player){
            //コマンド送信者の情報をプレイヤーへ変換
            Player player = (Player) commandSender;
            //因数（引数）が1つかチェック
            if (strings.length == 1){
                //因数によってもらえるアイテムを判定
                switch (strings[0]){
                    //コマンドが/create item1だった時の処理
                    case "item1" -> {
                        //ItemStack: アイテムの全情報が入ったデータの作成（今回はダイヤモンドソードを1個作成）（料理の入ったお盆の感じ）
                        ItemStack itemStack = new ItemStack(Material.DIAMOND_SWORD, 1);
                        //ItemMeta: アイテムの詳細情報を取得（お盆に乗せられたそれぞれの料理みたいな感じ）
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        //ダイヤモンドの剣につよつよソードと名前を付ける
                        itemMeta.displayName(Component.text().content("つよつよソード").build());
                        //エンチャントの付与（修繕レベル10）
                        itemMeta.addEnchant(Enchantment.MENDING, 10, true);
                        //エンチャントの付与（ダメージ増加レベル255）
                        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 255, true);
                        //説明文を格納するリストの作成
                        List<Component> list = new ArrayList<>();
                        //ダイヤモンドの剣に説明文を保存
                        itemMeta.lore(list);
                        //変更した詳細情報を保存
                        itemStack.setItemMeta(itemMeta);
                        //作成したアイテムをプレイヤーのインベントリーに追加
                        player.getInventory().addItem(itemStack);
                        //コマンドが完了したことを通知するメッセージをプレイヤーへ送信
                        player.sendMessage("つよつよソードを受け取りました．");
                    }
                    //コマンドが/create item2だった時の処理
                    case "item2" -> {
                        //ItemStack: アイテムの全情報が入ったデータの作成
                        ItemStack itemStack = new ItemStack(Material.DIAMOND_SHOVEL, 1);
                        //ItemMeta: アイテムの詳細情報を取得
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        //ダイヤモンドのシャベルに祝福のシャベルと名前をつける
                        itemMeta.displayName(Component.text().content("祝福のシャベル").build());
                        //シャベルを不可解（耐久値が減らない）に設定
                        itemMeta.setUnbreakable(true);
                        //アイテムにカスタムデータを格納する入れ物を取得（NBTのようなもの）
                        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
                        //NamespacedKey: 鍵のようなものを生成
                        NamespacedKey namespacedKey = new NamespacedKey(CreateItem.getPlugin(), "create");
                        //カスタムデータに鍵の情報を格納する
                        persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, "ci");
                        //変更した詳細情報を保存
                        itemStack.setItemMeta(itemMeta);
                        //作成したアイテムをプレイヤーのインベントリーに追加
                        player.getInventory().addItem(itemStack);
                        //コマンドが完了したことを通知するメッセージをプレイヤーへ送信
                        player.sendMessage("祝福のシャベルを受け取りました．");
                    }
                    //コマンドが/create item3だった時の処理
                    case "item3" -> {
                        //ItemStack: アイテムの全情報が入ったデータの作成
                        ItemStack itemStack = new ItemStack(Material.NETHERITE_INGOT, 1);
                        //ItemMeta: アイテムの詳細情報を取得
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        //configからdisplayの情報を取得("エラー"はconfigから名前を取得できなかった場合[例: configにdisplayの設定項目がないなど]に使用される)
                        String display = CreateItem.getConfiguration().getString("display", "エラー");
                        //displayで取得した名前をネザライトインゴットにつける
                        itemMeta.displayName(Component.text().content(display).build());
                        //説明文を格納するリストの作成
                        List<Component> list = new ArrayList<>();
                        //configからloreの1を取得
                        String lore1 = CreateItem.getConfiguration().getString("lore.1", "エラー");
                        //lore1をComponentにしてlistへ入れる
                        list.add(Component.text().content(lore1).build());
                        //configからloreの2を取得
                        String lore2 = CreateItem.getConfiguration().getString("lore.2", "エラー");
                        //lore2をComponentにしてlistへ入れる
                        list.add(Component.text().content(lore2).build());
                        //ネザライトインゴットに説明文を保存
                        itemMeta.lore(list);
                        //configから取得するlistを保存する
                        List<String> list2 = CreateItem.getConfiguration().getStringList("enchantments");
                        //list2にデータがあるかを確認
                        if (!list2.isEmpty()){
                            //データがあれば実行
                            //複数個のデータに対して同じことを行う
                            for (String string : list2){
                                //データが-によって区切られているため-を削除してデータを分ける
                                String[] strings2 = string.split("-");
                                //今回取得したデータ（エンチャント名-レベル）のエンチャント名の方を取り出す
                                String enchantName = strings2[0];
                                //エンチャント名をエンチャントのデータに変換
                                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantName.toLowerCase()));
                                //enchantmentが存在するか確認
                                if (enchantment != null){
                                    //エンチャントの付与
                                    itemMeta.addEnchant(enchantment, Integer.parseInt(strings2[1]), true);
                                }
                            }
                        }
                        //変更した詳細情報を保存
                        itemStack.setItemMeta(itemMeta);
                        //作成したアイテムをプレイヤーのインベントリーに追加
                        player.getInventory().addItem(itemStack);
                        //コマンドを完了したことを通知するメッセージをプレイヤーへ送信
                        player.sendMessage(display + "を受け取りました．");
                    }
                    //コマンドがどれでもなかった時の処理
                    default -> player.sendMessage("サブコマンドはitem1, item2, item3のみです．");
                }
            }
        }else {
            //送信者がコマンドブロックかコンソールだったら返信
            commandSender.sendMessage("このコマンドはプレイヤーのみ実行できます．");
        }
        return true;
    }
}
