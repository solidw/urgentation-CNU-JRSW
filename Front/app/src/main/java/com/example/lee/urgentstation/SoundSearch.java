package com.example.lee.urgentstation;

public class SoundSearch {
    private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
    private static final char HANGUL_LAST_UNICODE = 55203; // 힣
    private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수 //자음
    private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

    private static boolean isInitialSound(char searchar){
                for(char c:INITIAL_SOUND){
            if(c == searchar){
                return true;
            }
        }
        return false;
    }

    private static char getInitialSound(char c)
    {   int hanBegin = (c - HANGUL_BEGIN_UNICODE);
        int index = hanBegin / HANGUL_BASE_UNIT;
        return INITIAL_SOUND[index];
    }
    private static boolean isHangul(char c)
    { return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE; }


    public SoundSearch() { }

    public static boolean matchString(String value, String search){
        int t = 0;
        int seof = value.length() - search.length();
        int slen = search.length();
        if(seof < 0) return false; //검색어가 더 길면 false를 리턴한다.
        for(int i = 0;i <= seof;i++){
            t = 0;
            while(t < slen){
                if(isInitialSound(search.charAt(t))==true && isHangul(value.charAt(i+t))) { //만약 현재 char이 초성이고 value가 한글이면
                    if (getInitialSound(value.charAt(i + t)) == search.charAt(t)) //각각의 초성끼리 같은지 비교한다
                        t++;
                    else
                        break;
                }
                else{
                    if(value.charAt(i+t)==search.charAt(t)) //그냥 같은지 비교한다.
                         t++;
                    else
                        break;


                }
            }
            if(t == slen)
                return true; //모두 일치한 결과를 찾으면 true를 리턴한다.
             }
             return false; //일치하는 것을 찾지 못했으면 false를 리턴한다.
        }
}

