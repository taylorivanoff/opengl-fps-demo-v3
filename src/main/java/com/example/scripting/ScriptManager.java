package com.example.scripting;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class ScriptManager {
    private Globals globals;

    public ScriptManager() {
        globals = JsePlatform.standardGlobals();
    }

    public void executeScript(String scriptPath) {
        try {
            globals.loadfile(scriptPath).call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}