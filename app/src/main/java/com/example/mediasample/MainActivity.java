package com.example.mediasample;

import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.URI;

public class MainActivity extends AppCompatActivity {

    //メディアプレーヤーフィールド
    private MediaPlayer _player;

    //再生、一時停止ボタンフィールド
    private Button _btPlay;

    //戻るボタンフィールド
    private Button _btBack;

    private Button _btForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //フィールドの各ボタンを取得
        _btPlay=findViewById(R.id.btPlay);
        _btBack=findViewById(R.id.btBack);
        _btForward=findViewById(R.id.btForward);

        //フィールドのメディアプレーヤーオブジェクトを生成
        _player = new MediaPlayer();
        //音声ファイルのURI文字列を作成
        String mediaFileUriStr ="android.resource://"+getPackageName()+"/"+R.raw.summer_mountain;
        //音声ファイルのURI文字列をもとにURIオブジェクトを生成。
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);
        try{
            //メディアプレーヤーに音声ファイルを指定。
            _player.setDataSource(MainActivity.this, mediaFileUri);
            //非同期でのメディア再生準備が完了した才のリスナを設定。
            _player.setOnPreparedListener(new PlayerPreparedListener());
            //メディア再生が終了した際のリスナを設定
            _player.setOnCompletionListener(new PlayerCompletionListener());
            //非同期でメディア再生
            _player.prepareAsync();

        }catch (IOException e){

        }
    }

    private class PlayerPreparedListener implements MediaPlayer.OnPreparedListener{
        @Override
        public void onPrepared(MediaPlayer mp){
            //各ボタンをタップ可能に設定
            _btPlay.setEnabled(true);
            _btBack.setEnabled(true);
            _btForward.setEnabled(true);
        }
    }
    private class PlayerCompletionListener implements MediaPlayer.OnCompletionListener{
        @Override
        public void onCompletion(MediaPlayer mp){
            //再生ボタンのラベルを「再生」に設定
            _btPlay.setText("再生");
        }
    }

    public void onPayButtonClick(View view){
        //プレーヤーが再生中だったら
        if(_player.isPlaying()){
            //プレーヤーを一時停止
            _player.pause();
            //再生ボタンのラベルを再生に設定。
            _btPlay.setText("再生");
        }else{
            //プレーヤーを再生
            _player.start();
            //再生ボタンのラベルを一時停止に設定。
            _btPlay.setText("一時停止");
        }
    }

    @Override
    protected void onDestroy(){
        //親クラスのメソッド呼び出し
        super.onDestroy();
        //プレーヤーが再生中なら…
        if(_player.isPlaying()){
            //プレーヤーを停止
            _player.stop();
        }
        //プレーヤーを解放。
        _player.release();
        //プレーヤー用フィールドをnullに。
        _player = null;

    }




}
