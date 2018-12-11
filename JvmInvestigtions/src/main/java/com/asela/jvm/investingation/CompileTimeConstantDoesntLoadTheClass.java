package com.asela.jvm.investingation;

/** -verbose:class */

class ShouldNotBeLoaded {

    /*
     * Without final 
    [Loaded java.security.BasicPermissionCollection from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
    [Loaded com.asela.jvm.investingation.CompileTimeConstantDoesntLoadTheClass from file:/C:/Users/asela.illayapparachc/git/experiments/JvmInvestigtions/target/classes/]
    [Loaded sun.launcher.LauncherHelper$FXHelper from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
    [Loaded java.lang.Class$MethodArray from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
    [Loaded com.asela.jvm.investingation.ShouldNotBeLoaded from file:/C:/Users/asela.illayapparachc/git/experiments/JvmInvestigtions/target/classes/]
    com.asela.jvm.investingation.ShouldNotBeLoaded static initialization.......
    Hello World!2048
    [Loaded java.lang.Shutdown from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
    [Loaded java.lang.Shutdown$Lock from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
     */

    /*
     * With final
    [Loaded com.asela.jvm.investingation.CompileTimeConstantDoesntLoadTheClass from file:/C:/Users/asela.illayapparachc/git/experiments/JvmInvestigtions/target/classes/]
    [Loaded sun.launcher.LauncherHelper$FXHelper from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
    [Loaded java.lang.Class$MethodArray from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
    Hello World!2048
    [Loaded java.lang.Shutdown from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
    [Loaded java.lang.Shutdown$Lock from C:\Program Files (x86)\Java\jre1.8.0_25\lib\rt.jar]
     */
    public static final int CONSTANT = 2 << 10;

    static {
        System.out.println(ShouldNotBeLoaded.class.getName() + " static initialization.......");
    }
    {

        System.out.println(ShouldNotBeLoaded.class.getName() + " initialization.......");
    }
}

public class CompileTimeConstantDoesntLoadTheClass {
    public static void main(String[] args) {
        System.out.println("Hello World!" + ShouldNotBeLoaded.CONSTANT);
    }
}
