package com.callitaday.monsterhunter.view;

public class FailView {
    /**
     * 예외 메시지 출력
     */
    public static void errorMessage(String message){
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║"+BattleView.center("⚠ "+message, 80)+"║"  );
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

}
