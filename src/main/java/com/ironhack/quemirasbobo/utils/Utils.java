package com.ironhack.quemirasbobo.utils;

public class Utils {

    public static void pause(int milis){
        milis = Math.min(milis,5000);
        try{
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted");
        }
    }

    public static void clearConsole() {
        try{
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")){
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            }else{
                System.out.print("\033[H\033[2J");
            }
        }catch (final Exception e){
            //  Handle any exceptions.
        }
    }

}
