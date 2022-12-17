package com.ironhack.quemirasbobo.utils;

import com.ironhack.quemirasbobo.dto.PlatformDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Utils {

    public void pause(int milis){
        milis = Math.min(milis,5000);
        try{
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            System.out.println("Sleep interrupted");
        }
    }

    public void promptEnterKey() {
        System.out.println("\nPress ENTER to continue...");
        try{
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void clearConsole() {
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

    public List<PlatformDto> deleteDuplicate(List<PlatformDto> platforms) {
        List<PlatformDto> cleanList = new ArrayList<>();
        boolean isIn;
        for (PlatformDto platform : platforms) {
            isIn=false;
            for (PlatformDto finalPlatform : cleanList) {
                if (platform.getName().equals(finalPlatform.getName())) {
                    isIn = true;
                    break;
                }
            }
            if(!isIn){
                cleanList.add(platform);
            }
        }

        return cleanList;
    }
}
