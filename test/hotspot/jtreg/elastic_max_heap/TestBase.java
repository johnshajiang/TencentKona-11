/*
 * Copyright (C) 2023, Tencent. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
import java.lang.reflect.Field;
import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import static jdk.test.lib.Asserts.*;

public class TestBase {
    // start jcmd and check output string
    public static void resizeAndCheck(long pid, String new_size, String[] contains, String[] not_contains) throws Exception {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(new String[] { JDKToolFinder.getJDKTool("jcmd"), Long.toString(pid), "GC.elastic_max_heap", new_size});
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        System.out.println(output.getOutput());
        if (contains != null) {
            for (String s: contains) {
                output.shouldContain(s);
            }
        }
        if (not_contains != null) {
            for (String s: not_contains) {
                output.shouldNotContain(s);
            }
        }
    }


    public static synchronized long getPidOfProcess(Process p) {
        return p.pid();
    }
}
