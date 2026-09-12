package com.callitaday.monsterhunter.util;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {

    /**
     * 현재 재생 중인 BGM
     *
     * BGM은 한 번에 하나만 재생하도록 관리한다.
     */
    private static Clip bgmClip;


    /**
     * 메인 화면 BGM
     */
    public static void playMainBgm() {

        playBgm("bgm/Main.wav");
    }


    /**
     * 캐릭터 정보 화면 BGM
     */
    public static void playCharacterInfoBgm() {

        playBgm("bgm/ChInfo.wav");
    }


    /**
     * 상점 BGM
     */
    public static void playShopBgm() {

        playBgm("bgm/Shop.wav");
    }


    /**
     * 스테이지별 BGM
     *
     * stage = 1
     * → Stage01.wav
     *
     * stage = 5
     * → Stage05.wav
     */
    public static void playStageBgm(int stage) {

        // 1~5 스테이지가 아닌 경우
        if (stage < 1 || stage > 5) {

            System.out.println(
                "존재하지 않는 스테이지 BGM입니다."
            );

            return;
        }

        String filePath =
            String.format("bgm/Stage%02d.wav", stage);

        playBgm(filePath);
    }


    /**
     * 공격 효과음
     */
    public static void playAttack() {

        playEffect("bgm/Atk.wav");
    }


    /**
     * 방어 효과음
     */
    public static void playDefend() {

        playEffect("bgm/Def.wav");
    }


    /**
     * 포션 효과음
     */
    public static void playPotion() {

        playEffect("bgm/Potion.wav");
    }


    /**
     * 실제 BGM 재생
     */
    private static synchronized void playBgm(String filePath) {

        /*
         * 새로운 BGM을 실행하기 전에 기존 BGM을 종료한다.
         */
        stopBgm();


        /*
         * BGM 전용 Thread 생성
         */
        Thread bgmThread = new Thread(() -> {

            try {
            	File file = new File(filePath);

                /*
                 * 파일이 존재하지 않을 경우
                 */
                if (!file.exists()) {

                    System.out.println("BGM 파일을 찾을 수 없습니다.");
                    System.out.println(file.getAbsolutePath());

                    return;
                }


                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);


                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);

                /*
                 * 현재 BGM으로 저장
                 */
                bgmClip = clip;

                /*
                 * 무한 반복 설정
                 */
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                /*
                 * BGM 시작
                 */
                clip.start();


            } catch (Exception e) {

                System.out.println("재생 중 오류가 발생했습니다.");
                System.out.println("파일 : " + filePath);
                e.printStackTrace();
            }

        });


        /*
         * 게임이 종료될 때 BGM Thread 때문에 프로그램이 계속 살아있는 것을 방지
         */
        bgmThread.setDaemon(true);

        /*
         * Thread 시작
         */
        bgmThread.start();
    }


    /**
     * 현재 BGM 종료
     */
    public static synchronized void stopBgm() {

        if (bgmClip != null) {

            /*
             * 재생 중이면 stop
             */
            if (bgmClip.isRunning()) {
                bgmClip.stop();
            }


            /*
             * Clip 자원 반환
             */
            bgmClip.close();
            bgmClip = null;
        }
    }


    /**
     * 효과음 재생
     * BGM과 별도의 Thread에서 실행한다.
     * 
     * Stage01.wav
     * +
     * Atk.wav
     *
     * 동시에 재생 가능
     */
    private static void playEffect(String filePath) {

        Thread effectThread = new Thread(() -> {

                try {

                    File file = new File(filePath);

                    if (!file.exists()) {

                        System.out.println("효과음 파일을 찾을 수 없습니다.");
                        System.out.println(file.getAbsolutePath());

                        return;
                    }


                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);


                    Clip effectClip = AudioSystem.getClip();


                    effectClip.open(
                        audioStream
                    );


                    /*
                     * 효과음 시작
                     */
                    effectClip.start();


                    /*
                     * 효과음이 끝날 때까지만 이 Thread를 기다린다.
                     *
                     * Main Thread와 BGM Thread에는 영향 없음
                     */
                    Thread.sleep(effectClip.getMicrosecondLength()/ 1000);

                    effectClip.close();

                    audioStream.close();


                } catch (InterruptedException e) {
                    
                	Thread.currentThread().interrupt();

                } catch (Exception e) {

                    System.out.println("효과음 재생 중 오류가 발생했습니다.");
                    System.out.println("파일 : " + filePath);

                    e.printStackTrace();
                }

            });


        effectThread.setDaemon(true);

        effectThread.start();
    }
}