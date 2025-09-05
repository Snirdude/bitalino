package com.afonsoraposo.bitalino;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.Result;

/** BitalinoPlugin */
public class BitalinoPlugin implements FlutterPlugin, ActivityAware, MethodChannel.MethodCallHandler {
    private @Nullable
    FlutterPluginBinding flutterPluginBinding;
    private @Nullable BITalino bitalino;
    private MethodChannel channel;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        this.flutterPluginBinding = binding;
        channel = new MethodChannel(binding.getBinaryMessenger(), "bitalino");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        this.flutterPluginBinding = null;
        if (channel != null) {
            channel.setMethodCallHandler(null);
            channel = null;
        }
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        startListening(binding.getActivity(), flutterPluginBinding.getBinaryMessenger());
    }

    @Override
    public void onDetachedFromActivity() {
        if (bitalino == null) {
            return;
        }

        bitalino.stopListening();
        bitalino = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        onAttachedToActivity(binding);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity();
    }

    private void startListening(Activity activity, BinaryMessenger messenger) {
        bitalino = new BITalino(activity, messenger);
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        // Handle method calls from Dart here.
    }
}
