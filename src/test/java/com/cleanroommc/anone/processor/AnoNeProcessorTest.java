/*
 * Copyright (c) 2022-2026 CleanroomMC contributors
 * SPDX-License-Identifier: MIT
 */

package com.cleanroommc.anone.processor;

import org.junit.jupiter.api.Test;

import static com.google.testing.compile.Compiler.javac;
import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.JavaFileObjects.forSourceString;

public class AnoNeProcessorTest {

    @Test
    void invokeOnly() {
        var invokeOnly = forSourceString("com.cleanroommc.anone.processor.InvokeOnlyClass",
                """
                package com.cleanroommc.anone.processor;
                
                import com.cleanroommc.anone.canon.InvokeOnly;
                
                public class InvokeOnlyClass {
                    @InvokeOnly
                    public void method() { }
                }
                """);
        var invalidOverride = forSourceString("com.cleanroommc.anone.processor.InvalidOverride",
                """
                package com.cleanroommc.anone.processor;
                
                public class InvalidOverride extends InvokeOnlyClass {
                    @Override
                    public void method() { }
                }
                """);

        var compilation = javac().withProcessors(new AnoNeProcessor()).compile(invokeOnly, invalidOverride);

        assertThat(compilation).failed();
    }

    @Test
    void overrideOnly() {
        var overrideOnly = forSourceString("com.cleanroommc.anone.processor.OverrideOnlyClass",
                """
                package com.cleanroommc.anone.processor;
                
                import com.cleanroommc.anone.canon.OverrideOnly;
                
                public class OverrideOnlyClass {
                    @OverrideOnly
                    public void method() { }
                    
                    public void method1() {
                        this.method();
                    }
                }
                """);

        var compilation = javac().withProcessors(new AnoNeProcessor()).compile(overrideOnly);

        assertThat(compilation).failed();
    }

    @Test
    void mustNotCloseReturnValue() {
        var assets = forSourceString("com.cleanroommc.anone.processor.Assets",
                """
                package com.cleanroommc.anone.processor;
                
                import com.cleanroommc.anone.lifecycle.MustNotClose;
                
                import java.io.InputStream;
                
                public class Assets {
                    private InputStream shared;
                    
                    @MustNotClose
                    public InputStream stream() {
                        return this.shared;
                    }
                    
                    public void release() throws Exception {
                        this.stream().close();
                    }
                }
                """);

        var compilation = javac().withProcessors(new AnoNeProcessor()).compile(assets);

        assertThat(compilation).hadErrorContaining("@MustNotClose");
    }

    @Test
    void mustNotCloseParameterInTryWithResources() {
        var borrower = forSourceString("com.cleanroommc.anone.processor.Borrower",
                """
                package com.cleanroommc.anone.processor;
                
                import com.cleanroommc.anone.lifecycle.MustNotClose;
                
                import java.io.InputStream;
                
                public class Borrower {
                    public void read(@MustNotClose InputStream in) throws Exception {
                        try (InputStream resource = in) {
                            resource.read();
                        }
                    }
                }
                """);

        var compilation = javac().withProcessors(new AnoNeProcessor()).compile(borrower);

        assertThat(compilation).hadErrorContaining("@MustNotClose");
    }

    @Test
    void mustNotCloseAllowsPlainUsage() {
        var borrower = forSourceString("com.cleanroommc.anone.processor.PlainBorrower",
                """
                package com.cleanroommc.anone.processor;
                
                import com.cleanroommc.anone.lifecycle.MustNotClose;
                
                import java.io.InputStream;
                
                public class PlainBorrower {
                    public int read(@MustNotClose InputStream in) throws Exception {
                        return in.read();
                    }
                }
                """);

        var compilation = javac().withProcessors(new AnoNeProcessor()).compile(borrower);

        assertThat(compilation).succeeded();
    }

}
