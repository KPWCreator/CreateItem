package com.hinaplugin.createitem;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class CreateItem extends JavaPlugin {
    public static CreateItem plugin;
    //config情報を保持するための宣言
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //pluginにこのプラグイン情報を入れる
        plugin = this;
        //コンソールにメッセージを出力
        getLogger().info("メッセージ");
        //コンソールに警告を出力
        getLogger().warning("警告");
        //コンソールにエラーを出力
        getLogger().severe("エラー");

        //config.ymlの取得
        File configFile = new File(getDataFolder(), "config.yml");
        //config.ymlがあるか判定
        if (!configFile.exists()){
            //config.ymlがなければ作成
            saveDefaultConfig();
        }

        //configの変数にconfigの情報を格納
        config = getConfig();

        //plugin.ymlに設定したコマンドを取得
        PluginCommand command = getCommand("create");
        //コマンドが存在しているかを確認
        if (command != null){
            //コマンドを受信した場合処理をするclassの指定
            command.setExecutor(new Command());
        }

        //RightClickListenerを登録
        getServer().getPluginManager().registerEvents(new RightClickListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //別のclassからpluginを取得できるようにする
    public static CreateItem getPlugin(){ return plugin; }

    //別のclassからconfigを取得できるようにする
    public static FileConfiguration getConfiguration(){ return config; }

    /*
    *  staticとはnewで新しいclassを作ったとしても同じ情報を使用するという意味
    *  staticがないとnewで新しいclassを作るたびに新しいインスタンスが作成されるため新しく作成する必要がない場合は
    *  staticを付けた方がメモリが軽くなる
    * */

}
