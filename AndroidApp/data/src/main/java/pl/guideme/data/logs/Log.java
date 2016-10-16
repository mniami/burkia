/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package pl.guideme.data.logs;

import pl.guideme.data.util.Supplier;

public class Log {
    private final String tag;

    public Log(String tag){
        this.tag = tag;
    }
    public static Log withName(String tag){
        return new Log(tag);
    }

    public void fine(Supplier<String> msg, Object... args) {
        android.util.Log.d(tag, String.format(msg.get(), args));
    }

    public void info(Supplier<String> msg, Object... args) {
        android.util.Log.i(tag, String.format(msg.get(), args));
    }

    public void error(Throwable t, Supplier<String> msg, Object... args) {
        android.util.Log.e(tag, String.format(msg.get(), args), t);
    }
    public void error(Throwable t){
        android.util.Log.e(tag, t.toString());
    }

}
